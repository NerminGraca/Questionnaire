package daos;

import java.util.List;

import com.avaje.ebean.Expression;

public interface BaseDAO <T> {
	
	public void save(T object);
	
	public void saveAll(List<T> objects);
	
	public void update(T object);
	
	public void delete(T object);
	
	public void deleteById(Integer id);

	public T findById(Integer id);
	
	public List<T> findByProperty(String name, Object value);

	public T findUniqueByProperty(String name, Object value);
	
	public T findUniqueByExpression(Expression expression);
	
	public List<T> findByExpression(Expression expression);

	public List<T> findAll();
}
