package it.prova.gestioneimpiegatojdbc.DAO.nongeneriche;

import java.util.Date;
import java.util.List;

import it.prova.gestioneimpiegatojdbc.DAO.IBaseDAO;
import it.prova.gestioneimpiegatojdbc.model.Compagnia;
import it.prova.gestioneimpiegatojdbc.model.Impiegato;

public interface ImpiegatoDAO extends IBaseDAO<Impiegato> {
	public abstract List<Impiegato> findAllByCompagnia(Compagnia input) throws Exception;

	public abstract List<Integer> countByDataFondazioneCompagniaGreaterThan(Date dataFondazioneDa) throws Exception;

	public abstract List<Impiegato> findAllErroriAssunzione() throws Exception;
}
