package dao;

import java.util.List;

public interface GenericDAO<K,E> {
	void persist(E entity);
    void remove(E entity);
    void update(E entity);
    E findById(K id);
    List<E> findAll();
}
