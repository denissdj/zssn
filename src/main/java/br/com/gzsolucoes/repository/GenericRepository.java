package br.com.gzsolucoes.repository;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;

@SuppressWarnings({ "unchecked" })
public abstract class GenericRepository <E,PK> {

	@Inject
    private EntityManager manager;
	
	@Inject
	private Logger log;

	private Class<?> getTypeClass() {
		return (Class<?>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public void salvar(final E entidade) {
		if (entidade != null) {
			this.getManager().persist(this.getManager().merge(entidade));
			this.getManager().flush();
		}
	}
	
	public E obterPorId(PK id) {
		return (E) this.getManager().find(this.getTypeClass(), id);
	}

	public List<E> listar() {
	    CriteriaBuilder builder = getManager().getCriteriaBuilder();
	    CriteriaQuery<E> criteriaQuery = (CriteriaQuery<E>) builder.createQuery(this.getTypeClass());
	    
	    Root<E> item = (Root<E>) criteriaQuery.from(this.getTypeClass());
	    criteriaQuery.select(item).orderBy(builder.asc(item.get("id")));
	
	    TypedQuery<E> query = getManager().createQuery(criteriaQuery);
	    return query.getResultList();
	}
	
	public void removerTodos(final List<E> entidades) {
		if (entidades != null) {
			for (final E entidade : entidades) {
				this.remover(entidade);
			}
		}
	}

	public void remover(final E entidade) {
		if (entidade != null) {
			log.info("Removendo a entidade: " + entidade.getClass().getName());
			this.getManager().remove(this.getManager().merge(entidade));
			this.getManager().flush();
		}
	}
	
	private Session getSession() {
		return (Session) this.getManager().getDelegate();
	}
	
	public Criteria novaCriteria() {
		return this.getSession().createCriteria(this.getTypeClass());
	}

	public EntityManager getManager() {
		return manager;
	}

}
