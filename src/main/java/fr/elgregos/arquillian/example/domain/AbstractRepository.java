package fr.elgregos.arquillian.example.domain;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractRepository {

	@Autowired
	private SessionFactory sessionFactory;

	public void delete(final Object entity) {
		this.getSession().delete(entity);
	}

	public <T> T get(final Long id, final Class<T> clazz) {
		return this.getSession().get(clazz, id);
	}

	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public void persist(final Object entity) {
		this.getSession().persist(entity);
	}

}
