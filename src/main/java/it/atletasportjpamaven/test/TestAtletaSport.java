package it.atletasportjpamaven.test;

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
            /*
            System.out.println("nel db ci sono " + sportServiceInstance.listAll().size() + " sport e "
                + atletaServiceInstance.listAll().size() + " atleti");
            testCRUD(sportServiceInstance);

            testCollegaSportAdAtletaEsistente(atletaServiceInstance, sportServiceInstance);
            */
            testScollegaAtletaDaSport(sportServiceInstance, atletaServiceInstance);

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

    private static void testCollegaSportAdAtletaEsistente(AtletaService atletaServiceInstance,
                                                         SportService sportServiceInstance) throws Exception {
        System.out.println(".......testCollegaSportAdAtletaEsistente inizio.............");

        Sport sportEsistenteSuDb = sportServiceInstance.findByDescrizione(AttivitaSportiva.CALCIO);
        if (sportEsistenteSuDb == null)
            throw new RuntimeException("testCollegaSportAdAtletaEsistente fallito: sport inesistente ");

        // mi creo un atleta inserendolo direttamente su db
        Atleta atletaNuovo = new Atleta("giovanni", "fortunati",
                LocalDate.of(1980, 04, 12), "104", 5);
        atletaServiceInstance.insert(atletaNuovo);
        if (atletaNuovo.getId() == null)
            throw new RuntimeException("testCollegaSportAdAtletaEsistente fallito: atleta non inserito ");

        sportServiceInstance.collegaAdAtletaEsistente(atletaNuovo, sportEsistenteSuDb);
        // per fare il test ricarico interamente l'oggetto e la relazione
        Atleta atletaReloaded = atletaServiceInstance.findAtletaByIdWithSport(atletaNuovo.getId());
        if (atletaReloaded.getSport().size() != 1)
            throw new RuntimeException("testCollegaSportAdAtletaEsistente fallito: sport non aggiunti ");

        System.out.println(".......testCollegaSportAdAtletaEsistente fine: PASSED.............");
    }

    private static void testScollegaAtletaDaSport(SportService sportServiceInstance, AtletaService atletaServiceInstance)
            throws Exception {
        System.out.println(".......testScollegaAtletaDaSport inizio.............");

        // carico un sport e lo associo ad un nuovo atleta
        Sport sportEsistenteSuDb = sportServiceInstance.findByDescrizione(AttivitaSportiva.CALCIO);
        if (sportEsistenteSuDb == null)
            throw new RuntimeException("testScollegaAtletaDaSport fallito: sport inesistente ");

        // mi creo un atleta inserendolo direttamente su db
        Atleta atletaNuovo = new Atleta("francesco", "paolantoni",
                LocalDate.of(1990, 9, 1), "200", 1);
        ;
        atletaServiceInstance.insert(atletaNuovo);
        if (atletaNuovo.getId() == null)
            throw new RuntimeException("testScollegaAtletaDaSport fallito: atleta non inserito ");
        atletaServiceInstance.aggiungiSport(atletaNuovo, sportEsistenteSuDb);

        // ora ricarico il record e provo a disassociare il sport
        Atleta atletaReloaded = atletaServiceInstance.findAtletaByIdWithSport(atletaNuovo.getId());
        boolean confermoSportPresente = false;
        for (Sport sportItem : atletaReloaded.getSport()) {
            if (sportItem.getDescrizione().equals(sportEsistenteSuDb.getDescrizione())) {
                confermoSportPresente = true;
                break;
            }
        }

        if (!confermoSportPresente)
            throw new RuntimeException("testScollegaAtletaDaSport fallito: atleta e sport non associati ");

        // ora provo la rimozione vera e propria ma poi forzo il caricamento per fare un
        // confronto 'pulito'
        sportServiceInstance.scollegaAtletaDaSport(atletaReloaded.getId());
        atletaReloaded = atletaServiceInstance.findAtletaByIdWithSport(atletaNuovo.getId());
        if (!atletaReloaded.getSport().isEmpty())
            throw new RuntimeException("testScollegaAtletaDaSport fallito: sport ancora associato ");

        System.out.println(".......testScollegaAtletaDaSport fine: PASSED.............");
    }
}
