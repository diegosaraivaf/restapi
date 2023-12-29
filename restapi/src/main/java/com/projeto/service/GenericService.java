package com.projeto.service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.projeto.repository.RepositoryGenericoImpl;

@Service
public class GenericService {
	
	@Autowired
	private RepositoryGenericoImpl repository;

	
    public <T> T saveOrUpdate(Class<T> clazz, Map<String, Object> attributes) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        T entity = instantiateAndPopulate(clazz, attributes);
        return entity;
    }
    
    /**
     *-buca a entidade por id 
     *-se existir : 
     *-    busca a entidade por id e seta os atributos que vem do json na entitdade 
     *-se nao existir 
     *     cria uma nova entidade e seta os campos passados 
     *-converte o valor string que vem do json e converte para um valor expecifico ex Date,Long,String,Enum
     *-caso o atributo seja uma outra entidade(verifica se tem a anotacao @Entity) ele repete o fluxo principal para essa entity
     *-tudo ocorre dentro de uma transacao,caso ocorra um erro, anteracoes feitas sao desfeitas
     *-relacionamento oneToMany: 
     *	  se a lista de referencias nao for passada nao e alteradoa nada(neste ponto isso 'encaixou com jpa' pois nao e execultada nenhum processamento(sql) se nao mecher no proxy da dependencia )
     *-   se for passado apenas um item na lista, os item anteriores serao sobreescritos(removidos) e apenas o item passado ficara como dependencia
     *-   se o lista for passada com informacoes, as informacoes desses itens serao atualizados
     *-   assumi que qualquer lista e uma lista de entity
     *-referencias a outras entidades que sejam passados iram tbm entraram logica(de bucar por id e atualizar somente os campos passados) de forma recursiva
     *-  
     */

    @Transactional
    public <T> T instantiateAndPopulate(Class<T> clazz, Map<String, Object> attributes) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        
    	T entity = null;
    	//busca entidade por id, se nao ouver id vai ser criada um nova entidade 
        if(attributes.get("id") == null ) {
        	entity = clazz.newInstance(); // Handle exceptions        	
        }else {
        	Object objeto = repository.porId(clazz, Long.valueOf(attributes.get("id").toString()));
            entity = (T)objeto;
        }

        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            Field field = clazz.getDeclaredField(entry.getKey()); // Handle exceptions
            field.setAccessible(true);
            
            //SE REFERENCIA
            if (isEntity(field.getType())) {
                Object nestedEntity = instantiateAndPopulate(field.getType(), (Map<String, Object>) entry.getValue());
                field.set(entity, nestedEntity);
            } 
            //SE LISTA
            else if (List.class.equals(field.getType())) {
                List existingList = (List) field.get(entity); // Get the existing list from the entity
                if (existingList == null) {
                    existingList = new ArrayList<>();
                }

                List newList = (List) entry.getValue();
                Class<?> listClazz = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                
                // Update the existing collection
                existingList.clear();
                for (Object item : newList) {
                    Object nestedEntity = instantiateAndPopulate(listClazz, (Map<String, Object>) item);
                    existingList.add(nestedEntity);
                }
                field.set(entity, existingList);
            }
            //SE VALOR 
            else {
            	 Object convertedValue = convertValue(field.getType(), entry.getValue());
                 
            	 //tive que utilizar isso pq jpa nao reconhece a alteracoa ao alterar diretamente o atributo
                 Method setter = entity.getClass().getMethod("set" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1), field.getType());
                 setter.invoke(entity, convertedValue);
            }
        }
        entity  =(T) repository.salvar(entity);
        return entity;
    }
    
    /**verifica se a classe e uma @Entity */
    private boolean isEntity(Class<?> type) {
        return type.isAnnotationPresent(javax.persistence.Entity.class); 
    }
    
    private Object convertValue(Class<?> type, Object value) {
    	if (Long.class.equals(type)) {
    		return new Long(value.toString());
    	}
    	else if (BigDecimal.class.equals(type)) {
            return new BigDecimal(value.toString());
        }
        else if(Date.class.equals(type)) {
        	try {
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        	Date date = formatter.parse(value.toString());
        	return date;
			} 
        	catch (ParseException e) {
				e.printStackTrace();
			}
        	return null;
        }
        else if(type.isEnum()) {
        	Class<? extends Enum> enumType = (Class<? extends Enum>) type;
            return Enum.valueOf(enumType, value.toString());
        }
        else if (LocalDate.class.equals(type)) {
        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    		return LocalDate.parse(value.toString(), formatter);
    	}

        // Add more type checks and conversions as needed
        // For example, handling Integer, Date, etc.
        
        return value; 
    }

}
