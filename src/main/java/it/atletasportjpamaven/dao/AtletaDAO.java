package it.atletasportjpamaven.dao;

import it.atletasportjpamaven.model.Atleta;

public interface AtletaDAO extends IBaseDAO<Atleta> {

    public Atleta findAtletaByIdWithSport(Long id) throws Exception;
    public int quanteMedaglieVinteDaAtletiConSportChiusi() throws Exception;
}
