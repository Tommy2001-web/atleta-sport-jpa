package it.atletasportjpamaven.service;

import it.atletasportjpamaven.dao.AtletaDAO;
import it.atletasportjpamaven.dao.SportDAO;
import it.atletasportjpamaven.model.Atleta;
import it.atletasportjpamaven.model.Sport;

import java.util.List;

public interface AtletaService {

    public List<Atleta> listAll() throws Exception;

    public Atleta findById(Long id) throws Exception;

    public void update(Atleta atletaInstance) throws Exception;

    public void insert(Atleta atletaInstance) throws Exception;

    public void delete(Long id) throws Exception;

    public void setSportDAO(SportDAO sportDAO);
    public void setAtletaDAO(AtletaDAO atletaDAO);

    public Atleta findAtletaByIdWithSport(Long id) throws Exception;
}
