package it.atletasportjpamaven.service;

import it.atletasportjpamaven.dao.AtletaDAO;
import it.atletasportjpamaven.dao.EntityManagerUtil;
import it.atletasportjpamaven.dao.SportDAO;
import it.atletasportjpamaven.model.Atleta;
import it.atletasportjpamaven.model.Sport;

import javax.persistence.EntityManager;
import java.util.List;

public class AtletaServiceImpl implements AtletaService {

    private SportDAO sportDAO;
    private AtletaDAO atletaDAO;

    @Override
    public List<Atleta> listAll() throws Exception {
        EntityManager entityManager = EntityManagerUtil.getEntityManager();

        try {
            // uso l'injection per il dao
            atletaDAO.setEntityManager(entityManager);

            // eseguo quello che realmente devo fare
            return atletaDAO.listAll();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            EntityManagerUtil.closeEntityManager(entityManager);
        }
    }

    @Override
    public Atleta findById(Long id) throws Exception {
        EntityManager entityManager = EntityManagerUtil.getEntityManager();

        try {
            // uso l'injection per il dao
            atletaDAO.setEntityManager(entityManager);

            // eseguo quello che realmente devo fare
            return atletaDAO.findById(id);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            EntityManagerUtil.closeEntityManager(entityManager);
        }
    }

    @Override
    public void update(Atleta atletaInstance) throws Exception {
        EntityManager entityManager = EntityManagerUtil.getEntityManager();

        try {
            // questo è come il MyConnection.getConnection()
            entityManager.getTransaction().begin();

            // uso l'injection per il dao
            atletaDAO.setEntityManager(entityManager);

            // eseguo quello che realmente devo fare
            atletaDAO.update(atletaInstance);

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
    public void insert(Atleta atletaInstance) throws Exception {

        EntityManager entityManager = EntityManagerUtil.getEntityManager();

        try {
            // questo è come il MyConnection.getConnection()
            entityManager.getTransaction().begin();

            // uso l'injection per il dao
            atletaDAO.setEntityManager(entityManager);

            // eseguo quello che realmente devo fare
            atletaDAO.insert(atletaInstance);

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
            atletaDAO.setEntityManager(entityManager);

            // eseguo quello che realmente devo fare
            Atleta atletaDaRimuovere = atletaDAO.findById(id);
            atletaDAO.delete(atletaDaRimuovere);

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
    public int quanteMedaglieVinteDaAtletiConSportChiusi() throws Exception {
        EntityManager entityManager = EntityManagerUtil.getEntityManager();

        try {
            atletaDAO.setEntityManager(entityManager);

            return atletaDAO.quanteMedaglieVinteDaAtletiConSportChiusi();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            EntityManagerUtil.closeEntityManager(entityManager);
        }
    }

    @Override
    public void aggiungiSport(Atleta atletaEsistente, Sport sportInstance) throws Exception {
        EntityManager entityManager = EntityManagerUtil.getEntityManager();

        try {
            // questo è come il MyConnection.getConnection()
            entityManager.getTransaction().begin();

            // uso l'injection per il dao
            atletaDAO.setEntityManager(entityManager);

            // 'attacco' alla sessione di hibernate i due oggetti
            // così jpa capisce che se è già presente quel ruolo non deve essere inserito
            atletaEsistente = entityManager.merge(atletaEsistente);
            sportInstance = entityManager.merge(sportInstance);

            atletaEsistente.getSport().add(sportInstance);
            // l'update non viene richiamato a mano in quanto
            // risulta automatico, infatti il contesto di persistenza
            // rileva che utenteEsistente ora è dirty vale a dire che una sua
            // proprieta ha subito una modifica (vale anche per i Set ovviamente)

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
    public Atleta findAtletaByIdWithSport(Long id) throws Exception {
        EntityManager entityManager = EntityManagerUtil.getEntityManager();

        try {

            atletaDAO.setEntityManager(entityManager);

            return atletaDAO.findAtletaByIdWithSport(id);

        } catch(Exception e) {
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

    @Override
    public void setAtletaDAO(AtletaDAO atletaDAO) {
        this.atletaDAO = atletaDAO;
    }

}
