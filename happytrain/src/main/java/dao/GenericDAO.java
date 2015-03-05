package dao;

import java.util.List;

/**
 *
 *
 * @param <K> Primary Key type
 * @param <E> Entity type
 */
public interface GenericDAO<K, E> {
	/**
	 * Save Entity in DB.
	 * @param entity Entity
	 */
	void persist(E entity);
	
    /**Delete Entity from DB.
     * @param entity Entity
     */
    void remove(E entity);
    
    /**Update Entity in DB.
     * @param entity Entity
     */
    void update(E entity);
    
    /**Select Entity from DB with given Id.
     * @param id Id
     * @return Entity 
     */
    E findById(K id);
    
    /**Select all Entities from table.
     * @return list of Entities
     */
    List<E> findAll();
}
