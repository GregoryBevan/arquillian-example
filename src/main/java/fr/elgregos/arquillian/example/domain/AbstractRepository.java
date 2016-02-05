package fr.elgregos.arquillian.example.domain;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public void delete(Object entity) {
	getSession().delete(entity);
    }

    public <T> T get(Long id, Class<T> clazz) {
	return getSession().get(clazz, id);
    }

    protected Session getSession() {
	return sessionFactory.getCurrentSession();
    }

    public void persist(Object entity) {
	getSession().persist(entity);
    }

}
