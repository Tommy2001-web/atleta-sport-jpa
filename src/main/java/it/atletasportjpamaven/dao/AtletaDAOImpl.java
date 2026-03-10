package it.atletasportjpamaven.dao;

import it.atletasportjpamaven.model.Atleta;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class AtletaDAOImpl implements AtletaDAO {

    private EntityManager entityManager;

    @Override
    public List<Atleta> listAll() throws Exception {
        return entityManager.createQuery("from Atleta", Atleta.class).getResultList();
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
        if (atleta == null) {
            throw new Exception("input non valido");
        }
        atleta = entityManager.merge(atleta);

    }

    @Override
    public Atleta findAtletaByIdWithSport(Long idAtleta) throws Exception {
        TypedQuery<Atleta> query = entityManager.createQuery("select a from Atleta a " +
                        "left join fetch a.sport s " +
                        "where a.id = :idAtleta",
                Atleta.class);
        query.setParameter("idAtleta", idAtleta);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public int quanteMedaglieVinteDaAtletiConSportChiusi() throws Exception {
        Long query = entityManager.createQuery(  "select sum(a.numeroMedaglieVinte) " +
                        "from Atleta a join a.sport s " +
                        "where s.dataFine is not null",
                Long.class).getSingleResult();
        int result = query.intValue();

        return result;
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
