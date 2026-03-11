package it.atletasportjpamaven.dao;

import it.atletasportjpamaven.model.AttivitaSportiva;
import it.atletasportjpamaven.model.Sport;

import java.util.List;

public interface SportDAO extends IBaseDAO<Sport> {
    public Sport findByDescrizione(AttivitaSportiva descrizione) throws Exception;
    public void scollegaAtletaDaSport (Long idAtleta) throws Exception;
    public List<Sport> findErrors () throws Exception;
}
