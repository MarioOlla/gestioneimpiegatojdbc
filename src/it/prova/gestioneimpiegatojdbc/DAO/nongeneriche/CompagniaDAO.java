package it.prova.gestioneimpiegatojdbc.DAO.nongeneriche;

import java.util.Date;
import java.util.List;

import it.prova.gestioneimpiegatojdbc.DAO.IBaseDAO;
import it.prova.gestioneimpiegatojdbc.model.Compagnia;

public interface CompagniaDAO extends IBaseDAO<Compagnia> {
	public abstract List<Compagnia> findAllByDataAssunzioneMaggioreDi(Date dataAssunzioneDa)throws Exception;
	public abstract List<Compagnia> findAllByRagioneSocialeContiene(String lettereContenute) throws Exception;
}
