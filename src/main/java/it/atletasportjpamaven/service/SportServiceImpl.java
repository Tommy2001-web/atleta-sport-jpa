package it.atletasportjpamaven.service;

import it.atletasportjpamaven.dao.EntityManagerUtil;
import it.atletasportjpamaven.dao.SportDAO;
import it.atletasportjpamaven.model.Sport;

import javax.persistence.EntityManager;
import java.util.List;

public class SportServiceImpl implements SportService{

    private SportDAO sportDAO;

    @Override
    public List<Sport> listAll() throws Exception {
        EntityManager entityManager = EntityManagerUtil.getEntityManager();

        try {
            // uso l'injection per il dao
            sportDAO.setEntityManager(entityManager);

            // eseguo quello che realmente devo fare
            return sportDAO.listAll();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            EntityManagerUtil.closeEntityManager(entityManager);
        }
    }

    @Override
    public Sport findById(Long id) throws Exception {
        EntityManager entityManager = EntityManagerUtil.getEntityManager();

        try {
            // uso l'injection per il dao
            sportDAO.setEntityManager(entityManager);

            // eseguo quello che realmente devo fare
            return sportDAO.findById(id);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            EntityManagerUtil.closeEntityManager(entityManager);
        }
    }

    @Override
    public void update(Sport sportInstance) throws Exception {
        EntityManager entityManager = EntityManagerUtil.getEntityManager();

        try {
            // questo è come il MyConnection.getConnection()
            entityManager.getTransaction().begin();

            // uso l'injection per il dao
            sportDAO.setEntityManager(entityManager);

            // eseguo quello che realmente devo fare
            sportDAO.update(sportInstance);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            throw e;
        } finally {
            EntityManagerUtil.closeEntityManager(entityManager);
        }
    }

    @Override
    public void insert(Sport sportInstance) throws Exception {

        EntityManager entityManager = EntityManagerUtil.getEntityManager();

        try {
            // questo è come il MyConnection.getConnection()
            entityManager.getTransaction().begin();

            // uso l'injection per il dao
            sportDAO.setEntityManager(entityManager);

            // eseguo quello che realmente devo fare
            sportDAO.insert(sportInstance);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            throw e;
        } finally {
            EntityManagerUtil.closeEntityManager(entityManager);
        }
    }

    @Override
    public void delete(Long id) throws Exception {

        EntityManager entityManager = EntityManagerUtil.getEntityManager();

        try {
            // questo è come il MyConnection.getConnection()
            entityManager.getTransaction().begin();

            // uso l'injection per il dao
            sportDAO.setEntityManager(entityManager);

            // eseguo quello che realmente devo fare
            Sport sportDaRimuovere = sportDAO.findById(id);
            sportDAO.delete(sportDaRimuovere);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            throw e;
        } finally {
            EntityManagerUtil.closeEntityManager(entityManager);
        }
    }

    @Override
    public void setSportDAO(SportDAO sportDAO) {
        this.sportDAO = sportDAO;
    }

}
