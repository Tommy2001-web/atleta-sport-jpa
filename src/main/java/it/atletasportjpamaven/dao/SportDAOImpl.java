package it.atletasportjpamaven.dao;

import it.atletasportjpamaven.model.Sport;

import javax.persistence.EntityManager;
import java.util.List;

public class SportDAOImpl implements SportDAO{

    private EntityManager entityManager;

    @Override
    public List<Sport> listAll() throws Exception {
        return entityManager.createQuery("from Sport", Sport.class).getResultList();
    }

    @Override
    public Sport findById(Long id) throws Exception {
        return entityManager.find(Sport.class, id);
    }

    @Override
    public void insert(Sport sport) throws Exception {
        if (sport == null) throw new Exception("input non valido");
        entityManager.persist(sport);
    }

    @Override
    public void delete(Sport sport) throws Exception {
        if (sport == null) throw new Exception("input non valido");
        entityManager.remove(entityManager.merge(sport));
    }

    @Override
    public void update(Sport sport) throws Exception {
        if (sport == null) throw new Exception("input non valido");
        entityManager.merge(sport);
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
