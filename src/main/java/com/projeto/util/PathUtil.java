package com.projeto.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projeto.entity.Lancamento;
import com.projeto.entity.Nfse;
import com.projeto.repository.RepositoryGenericoImpl;
/**
 * 
 * @author Diego Ferreira
 *
 */
@Component
public class PathUtil {
	
	@Autowired
	RepositoryGenericoImpl<Nfse, Long> repositoryGeneric;

	public <T> T parseToObject(Map<String, Object> atributos,Class<T> classe) throws InstantiationException, IllegalAccessException {
		//busca pessoa por id se nao achar retorna codigo 403
//		Class<T> objetoDestino = classe.getClass().newInstance();
		T objetoAtual = (T) repositoryGeneric.porId(1L);
		
		//seta os valores dos atributaos passados,e passado uma lista porques existe a possibilidade do cliente querer setar null em um atributo
		ObjectMapper objectMapper = new ObjectMapper();
		//object mapper foi utilizadeo porque existem varias conversoes que seriam necessario fazer manualmente como de int para bigdevimal ....
		T objetoNovo = objectMapper.convertValue(atributos, classe);
		
		atributos.forEach((nomePropriedade,valorPropriedade) ->{
			//tornar esse trecho generico 
			Field field = ReflectionUtils.findField(classe, nomePropriedade);
			//field.getDeclaredAnnotations()
			
			//caso seja um relacao atualizar o objeto da relacao com o atributo passado 
			if(field.isAnnotationPresent(javax.persistence.OneToMany.class)) {
				try {
					Map<String,Object> map = new HashMap<String, Object>();
//					(ArrayList<LinkedHashMap)valorPropriedade
					ArrayList<LinkedHashMap> map2 =  ((ArrayList<LinkedHashMap>)valorPropriedade);
//					map2.stream().forEach(m -> 
//						m.entrySet().stream().forEach(m2 -> 
//							map.put(m2.getKey().toString(), m2.get(m2.keySet().toString()))
//						)
//							
//					);
					
					
					parseToObject(map, field.getClass());
				} catch (InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			field.setAccessible(true);
			
			Object novoValor =ReflectionUtils.getField(field, objetoNovo);
			
			ReflectionUtils.setField(field, objetoAtual, novoValor);
		});
		
		
		//adicionar regra se o campo tiver anotacao @ManyToMany ManyToOne OneToMany OneToOne 
		//repetir o processo de atualizacao de objeto 
		
		return objetoAtual;
	}

}
