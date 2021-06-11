package br.com.fiap.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.fiap.model.Hotel;
import br.com.fiap.model.User;
import br.com.fiap.util.JPAUtil;

public class DAO<T> {
	
	private Class<T> classs;
	
	public DAO(Class<T> classs) {
		this.classs = classs;
	}
	
	private EntityManager manager = JPAUtil.getEntityManager();

	public void save(T t) {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		manager.close();
	}

	public List<T> getAll() {
		CriteriaQuery<T> query = manager.getCriteriaBuilder().createQuery(classs);
		query.select(query.from(classs));
		TypedQuery<T> createQuery = manager.createQuery(query);
		List<T> resultList = createQuery.getResultList();
		return resultList;
	}

	public T findById(Long id) {
		return manager.find(classs, id);
	}

	public void update(Hotel hotel) {
		manager.getTransaction().begin();
		manager.merge(hotel);
		manager.flush();
		manager.getTransaction().commit();
	}

	public boolean exist(User user) {
		TypedQuery<User> query = manager.createQuery("SELECT u from User u WHERE "
				+ "email= :email AND "
				+ "password = :password", User.class);
		
		query.setParameter("email", user.getEmail());
		query.setParameter("password", user.getPassword());
		
		User result = query.getSingleResult();
		return result != null;
	}

	public void updateUser(User user) {
		manager.getTransaction().begin();
		manager.merge(user);
		manager.flush();
		manager.getTransaction().commit();
	}

}
