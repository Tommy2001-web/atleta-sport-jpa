package it.atletasportjpamaven.test;

import it.atletasportjpamaven.dao.AtletaDAO;
import it.atletasportjpamaven.dao.AtletaDAOImpl;
import it.atletasportjpamaven.dao.EntityManagerUtil;
import it.atletasportjpamaven.model.Atleta;
import it.atletasportjpamaven.model.AttivitaSportiva;
import it.atletasportjpamaven.model.Sport;
import it.atletasportjpamaven.service.AtletaService;
import it.atletasportjpamaven.service.MyServiceFactory;
import it.atletasportjpamaven.service.SportService;

import java.time.LocalDate;
import java.util.List;

public class TestAtletaSport {
    public static void main(String[] args) {

        AtletaService atletaServiceInstance = MyServiceFactory.getAtletaServiceInstance();
        SportService sportServiceInstance = MyServiceFactory.getSportServiceInstance();

        try {

            System.out.println("nel db ci sono " + sportServiceInstance.listAll().size() + " sport e "
                + atletaServiceInstance.listAll().size() + " atleti");
            initDatiPerTest(atletaServiceInstance, sportServiceInstance);
            System.out.println("nel db ci sono " + sportServiceInstance.listAll().size() + " sport e "
                + atletaServiceInstance.listAll().size() + " atleti");
            testCRUD(sportServiceInstance);

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            EntityManagerUtil.shutdown();
        }
    }

    private static void initDatiPerTest (AtletaService atletaServiceInstance, SportService sportServiceInstance)
            throws Exception{
        if (sportServiceInstance.listAll().isEmpty()) {
            sportServiceInstance.insert(new Sport(AttivitaSportiva.CALCIO,
                    LocalDate.of(2024, 12, 11),
                    LocalDate.of(2025, 12, 11)));
            sportServiceInstance.insert(new Sport(AttivitaSportiva.CORSA,
                    LocalDate.of(2025, 12, 11),
                    LocalDate.of(2026, 02, 11)));

        }
        if (atletaServiceInstance.listAll().isEmpty()) {
            atletaServiceInstance.insert(new Atleta("franco", "franchini",
                    LocalDate.of(2000, 02, 10), "007", 2));
            atletaServiceInstance.insert(new Atleta("mario", "rossi",
                    LocalDate.of(1997, 10, 24), "010", 7));

        }

    }

    private static void testCRUD (SportService sportServiceInstance) throws Exception {
        System.out.println("-------- testCRUD inizio---------");
        // testo la select
        List<Sport> listaSport = sportServiceInstance.listAll();
        if (listaSport == null) throw new Exception("testCRUD FAILED: nessun valore trovato con listAll");

        // testo la insert
        Sport sportInstance = new Sport(AttivitaSportiva.CALCIO,
                LocalDate.of(2021, 12, 11),
                LocalDate.of(2023, 7, 5));
        sportServiceInstance.insert(sportInstance);
        List<Sport> nuovaLista = sportServiceInstance.listAll();
        if(listaSport.size() == nuovaLista.size()) throw new Exception("testCRUD FAILED: insert fallito");

        // testo l'update
        AttivitaSportiva attivitaPrecedente = sportInstance.getDescrizione();
        sportInstance.setDescrizione(AttivitaSportiva.NUOTO);
        sportServiceInstance.update(sportInstance);
        sportInstance = sportServiceInstance.findById(3L);
        if (attivitaPrecedente.equals(sportInstance.getDescrizione()))
            throw new Exception("testCRUD FAILED: update fallito");

        // testo la delete
        sportServiceInstance.delete(sportInstance.getId());
        if(sportServiceInstance.findById(sportInstance.getId()) != null)
            throw new Exception("testCRUD FAILED: delete fallita");

        System.out.println("--------testCRUD PASSED: fine----------");
    }
}
