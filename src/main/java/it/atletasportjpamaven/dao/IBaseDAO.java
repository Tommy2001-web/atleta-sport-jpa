package it.atletasportjpamaven.dao;

import javax.persistence.EntityManager;
import java.util.List;

public interface IBaseDAO<T> {
    public List<T> listAll() throws Exception;
    public T findById(Long id) throws Exception;
    public void insert(T o) throws Exception;
    public void delete(T o) throws Exception;
    public void update(T o) throws Exception;

    // serve per la injection
    public void setEntityManager(EntityManager entityManager);

}
