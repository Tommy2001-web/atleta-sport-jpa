package it.atletasportjpamaven.service;

import it.atletasportjpamaven.dao.AtletaDAO;
import it.atletasportjpamaven.dao.SportDAO;
import it.atletasportjpamaven.model.Atleta;
import it.atletasportjpamaven.model.AttivitaSportiva;
import it.atletasportjpamaven.model.Sport;

import java.util.List;

public interface SportService {

    public List<Sport> listAll() throws Exception;

    public Sport findById(Long id) throws Exception;

    public void update(Sport sportInstance) throws Exception;

    public void insert(Sport sportInstance) throws Exception;

    public void delete(Long id) throws Exception;

    public void collegaAdAtletaEsistente(Atleta atletaEsistente, Sport ruoloInstance) throws Exception;

    public Sport findByDescrizione(AttivitaSportiva descrizione) throws Exception;

    public void scollegaAtletaDaSport(Long idAtleta) throws Exception;

    public void setSportDAO(SportDAO sportDAO);
}
