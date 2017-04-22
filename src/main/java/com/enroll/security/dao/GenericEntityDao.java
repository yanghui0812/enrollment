package com.enroll.security.dao;

public interface GenericEntityDao {
	
	 /**
     * Finds a generic entity by a classname and id
     * 
     * @param className
     * @param id
     * @return the entity
     */
    public <T> T readGenericEntity(Class<T> clazz, Long id);
    
    /**
     * Finds a generic entity by a classname and id
     * 
     * @param className
     * @param id
     * @return the entity
     */
    public <T> T readGenericEntity(Class<T> clazz, String id);

    /**
     * Saves a generic entity
     * 
     * @param object
     * @return the persisted version of the entity
     */
    public <T> T save(T object);

    /**
     * Persist the new entity
     *
     * @param object
     */
    void persist(Object object);

    void flush();

    void clear();

    boolean sessionContains(Object object);

    void delete(Object object);
}
