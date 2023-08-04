package com.projeto.repository;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.projeto.entity.Lancamento;

@Repository
public class RepositoryGenericoImpl <T,PK>{
	
	@PersistenceContext
	private EntityManager manager;
	
	public T porId(Long id) {
		return (T) manager.find(getTypeClass(), id);
	}
	
	private Class<?> getTypeClass() {
        Class<?> clazz = (Class<?>) ((ParameterizedType) this.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        return clazz;
    }
	
	/**
	 * 
	 * Implementar aqui um filtrar onde eu possa passar os campos que deve ser filtrados, as relacoes que devem ser carregadas e se quero paginacao
	 */
	public List<Lancamento> filtrar(T entidade,String[] relacoes, int pagina, int limite) {
		StringBuilder jpql = new StringBuilder();
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		int total = buscarQuantidadeTotalLancamentos();
		int quantidadePaginas = total / limite;
		int primeiroRegistroPagina = pagina * limite ;
		int ultimoRegistroPagina = primeiroRegistroPagina + limite ;
		
		jpql.append("select distinct e from "+getTypeClass().getName()+" e "
				+ "join fetch l.contribuinte c "
				+ "left join fetch l.parcelas p "
				+ "where 0=0 ");
		
//		if(id != null) {
//			jpql.append("and l.id = :id ");
//			parametros.put("id", id);
//		}
//		if(tipoLancamento != null ) {
//			jpql.append("and l.tipoLancamento = :tipoLancamento ");
//			parametros.put("tipoLancamento", tipoLancamento);
//		}
//		if(contribuinteNome != null && contribuinteNome.trim().length() > 0) {
//			jpql.append("and l.contribuinte.nome like :contribuinteNome ");
//			parametros.put("contribuinteNome", "%"+contribuinteNome+"%");
//		}
//		if(contribuinteDocumento != null && contribuinteDocumento.trim().length() > 0) {
//			jpql.append("and l.contribuinte.documento like :contribuinteDocumento ");
//			parametros.put("contribuinteDocumento", "%"+contribuinteDocumento+"%");
//		}
		
		jpql.append("order by l.id ");
		
		TypedQuery<Lancamento> query =  manager.createQuery(jpql.toString(),Lancamento.class);
		parametros.forEach((key,value) -> query.setParameter(key, value));
		
		return query.getResultList();
	}
	
	private int buscarQuantidadeTotalLancamentos() {
		StringBuilder jpql = new StringBuilder();
		jpql.append("select count(l.id) from Lancamento l");
	
		TypedQuery<Long> query =  manager.createQuery(jpql.toString(),Long.class);

		
		return Integer.parseInt(query.getSingleResult().toString());
	}

}
