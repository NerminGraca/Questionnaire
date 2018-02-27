package daos;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expression;
import com.avaje.ebean.Query;

import models.ModelUpdater;

public abstract class EbeanBaseDAO <T> implements BaseDAO <T> {
	
	private final Class<T> persistentClass;
	
	@SuppressWarnings("unchecked")
	protected EbeanBaseDAO() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Override
	public void save(T object) {
		if(object instanceof ModelUpdater){
			((ModelUpdater) object).prepareForSaveOrUpdate();
		}
		Ebean.save(object);
	}
	
	@Override
	public void saveAll(List<T> objects) {
		Ebean.saveAll(objects);
	}

	@Override
	public void update(T object) {
		if(object instanceof ModelUpdater){
			((ModelUpdater) object).prepareForSaveOrUpdate();
		}
		Ebean.update(object);
	}

	@Override
	public void delete(T object) {
		Ebean.delete(object);
	}

	@Override
	public void deleteById(Integer id) {
		T entity = findById(id);
		Ebean.delete(entity);
	}

	@Override
	public T findById(Integer id) {
		return Ebean.find(persistentClass, id);
	}

	@Override
	public List<T> findByProperty(String name, Object value) {
		return Ebean.find(persistentClass).where().eq(name, value).findList();
	}
	
	@Override
	public T findUniqueByProperty(String name, Object value) {
		return Ebean.find(persistentClass).where().eq(name, value).findUnique();
	}
	
	@Override
	public T findUniqueByExpression(Expression expression) {
		return getQueryWithExpression(expression).findUnique();
	}
	
	@Override
	public List<T> findByExpression(Expression expression) {
		return getQueryWithExpression(expression).findList();
	}
	
	private Query<T> getQueryWithExpression(Expression expression){
		Query<T> query = Ebean.createQuery(persistentClass);
		if (expression != null) {
			query.where().add(expression);
		}
		return query;
	}

	@Override
	public List<T> findAll() {
		return Ebean.find(persistentClass).findList();
	}

}
