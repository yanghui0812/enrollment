package com.enroll.security.dao.impl;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.enroll.security.dao.GenericEntityDao;

public class GenericEntityDaoImpl implements GenericEntityDao {
	
	protected static final Logger LOGGER = LogManager.getLogger(GenericEntityDao.class);
	
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getEntityManager() {
		return sessionFactory.getCurrentSession();
	}

	@Override
    public <T> T save(T object) {
		getEntityManager().saveOrUpdate(object);
        return object;
    }

    @Override
    public void persist(Object object) {
    	getEntityManager().persist(object);
    }

    @Override
    public void flush() {
    	getEntityManager().flush();
    }

    @Override
    public void clear() {
    	getEntityManager().clear();
    }

    @Override
    public boolean sessionContains(Object object) {
        return getEntityManager().contains(object);
    }

	@Override
	public <T> T readGenericEntity(Class<T> clazz, Long id) {
		return getEntityManager().get(clazz, id);
	}

	@Override
	public <T> T readGenericEntity(Class<T> clazz, String id) {
		return getEntityManager().get(clazz, id);
	}

	@Override
	public void delete(Object object) {
		getEntityManager().delete(object);
	}
}