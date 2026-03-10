package it.atletasportjpamaven.dao;

import it.atletasportjpamaven.model.Atleta;

import javax.persistence.EntityManager;
import java.util.List;

public class AtletaDAOImpl implements AtletaDAO{

    private EntityManager entityManager;

    @Override
    public List<Atleta> listAll() throws Exception {
        return entityManager.createQuery("from Atleta",Atleta.class).getResultList();
    }

    @Override
    public Atleta findById(Long id) throws Exception {
        return entityManager.find(Atleta.class, id);
    }

    @Override
    public void insert(Atleta atleta) throws Exception {
        if (atleta == null) throw new Exception("Input non valido");
        entityManager.persist(atleta);
    }

    @Override
    public void delete(Atleta atleta) throws Exception {
        if (atleta == null) {
            throw new Exception("input non valido");
        }
        entityManager.remove(entityManager.merge(atleta));
    }

    @Override
    public void update(Atleta atleta) throws Exception {
        if (atleta == null){
            throw new Exception("input non valido");
        }
        atleta = entityManager.merge(atleta);

    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
