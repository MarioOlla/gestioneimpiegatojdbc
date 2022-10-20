package it.prova.gestioneimpiegatojdbc.test;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.prova.gestioneimpiegatojdbc.DAO.Constants;
import it.prova.gestioneimpiegatojdbc.DAO.nongeneriche.CompagniaDAO;
import it.prova.gestioneimpiegatojdbc.DAO.nongeneriche.CompagniaDAOImpl;
import it.prova.gestioneimpiegatojdbc.DAO.nongeneriche.ImpiegatoDAO;
import it.prova.gestioneimpiegatojdbc.DAO.nongeneriche.ImpiegatoDAOImpl;
import it.prova.gestioneimpiegatojdbc.connection.MyConnection;
import it.prova.gestioneimpiegatojdbc.model.Compagnia;
import it.prova.gestioneimpiegatojdbc.model.Impiegato;

public class TestImpiegato {

	public static void main(String[] args) {
		ImpiegatoDAO impiegatoDAOInstance = null;
		CompagniaDAO compagniaDAOInstance = null;

		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {

			compagniaDAOInstance = new CompagniaDAOImpl(connection);
			impiegatoDAOInstance = new ImpiegatoDAOImpl(connection);

			System.out.println("Sul DB di Compagnia ci sono " + compagniaDAOInstance.list().size() + " record.");
			System.out.println("Sul DB di Impiegato ci sono " + impiegatoDAOInstance.list().size() + " record.");

			testInsertCompagnia(compagniaDAOInstance);

			System.out.println("Sul DB di Compagnia ci sono " + compagniaDAOInstance.list().size() + " record.");

			testUpdateCompagnia(compagniaDAOInstance);
			
			testInsertImpiegato(impiegatoDAOInstance, compagniaDAOInstance);
			
			System.out.println("Sul DB di Impiegato ci sono " + impiegatoDAOInstance.list().size() + " record.");
			
			testUpdateImpiegato(impiegatoDAOInstance);
			
			testDeleteImpiegato(impiegatoDAOInstance);
			
			System.out.println("Sul DB di Compagnia ci sono " + impiegatoDAOInstance.list().size() + " record.");

			testDeleteCompagnia(compagniaDAOInstance);

			System.out.println("Sul DB di Compagnia ci sono " + compagniaDAOInstance.list().size() + " record.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void testInsertCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println("\n___inizio testInsertCompagnia... ");
		Compagnia daAggiungere = new Compagnia(0, "Marrella impianti", 30000000,
				new SimpleDateFormat("dd-MM-yyyy").parse("05-03-1995"));
		int result = compagniaDAOInstance.insert(daAggiungere);
		if (result < 1)
			throw new RuntimeException("testInsertCompagnia : FAILED, non sono stati aggiunti nuovi record");
		System.out.println("___fine testInsertCompagnia : PASSED");
	}

	private static void testGetCompagnia(CompagniaDAO compagniaDAOInstance) {

	}

	private static void testUpdateCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println("\n___inizio testUpdateCompagnia... ");
		List<Compagnia> elencoVociEsistenti = compagniaDAOInstance.list();
		if (elencoVociEsistenti.isEmpty())
			throw new RuntimeException("testUpdateCompagnia : FAILED, il DB e' vuoto");
		Compagnia compagniaConValoriAggiornati = new Compagnia(elencoVociEsistenti.get(0).getId(), "Marrella Solutions",
				100000000, new SimpleDateFormat("dd-MM-yyyy").parse("05-03-1995"));
		int result = compagniaDAOInstance.update(compagniaConValoriAggiornati);
		if (result < 1)
			throw new RuntimeException("testUpdateCompagnia : FAILED, nessun record e' stato aggiornato");
		System.out.println("___fine testUpdateCompagnia : PASSED");
	}

	private static void testInsertImpiegato(ImpiegatoDAO impiegatoDAOInstance, CompagniaDAO compagniaDAOInstance)
			throws Exception {
		System.out.println("\n___inizio testInsertImpiegato... ");
		Compagnia compagniaDiAppartenenza = compagniaDAOInstance.list().get(0);
		Impiegato daAggiungere = new Impiegato(0, "Giovanni", "Giorgio", "codice fiscale di giovanni giorgio",
				new SimpleDateFormat("dd-MM-yyyy").parse("05-03-2000"),
				new SimpleDateFormat("dd-MM-yyyy").parse("05-03-2020"), compagniaDiAppartenenza);
		int result = impiegatoDAOInstance.insert(daAggiungere);
		if (result < 1)
			throw new RuntimeException("testInsertImpiegato : FAILED, non sono stati aggiunti nuovi record");
		System.out.println("___testInsertImpiegato : PASSED");
	}

	private static void testUpdateImpiegato(ImpiegatoDAO impiegatoDAOInstance) throws Exception {
		System.out.println("\n___inizio testUpdateImpiegato... ");
		List<Impiegato> elencoVociEsistenti = impiegatoDAOInstance.list();
		if (elencoVociEsistenti.isEmpty())
			throw new RuntimeException("testUpdateImpiegato : FAILED, il DB e' vuoto");
		Impiegato daAggiungere = new Impiegato(elencoVociEsistenti.get(0).getId(), "Marcello", "Giorgi", "codice fiscale di marcello giorgi",
				new SimpleDateFormat("dd-MM-yyyy").parse("05-03-1998"),
				new SimpleDateFormat("dd-MM-yyyy").parse("05-03-2021"), elencoVociEsistenti.get(0).getCompagnia());
		int result = impiegatoDAOInstance.update(daAggiungere);
		if (result < 1)
			throw new RuntimeException("testUpdateImpiegato : FAILED, nessun record e' stato aggiornato");
		System.out.println("___fine testUpdateImpiegato : PASSED");
	}

	private static void testDeleteImpiegato(ImpiegatoDAO impiegatoDAOInstance) throws Exception {
		System.out.println("\n___inizio testDeleteImpiegato... ");
		List<Impiegato> elencoVociEsistenti = impiegatoDAOInstance.list();
		if (elencoVociEsistenti.isEmpty())
			throw new RuntimeException("testDeleteImpiegato : FAILED, il DB e' vuoto");
		int result = impiegatoDAOInstance.delete(elencoVociEsistenti.get(0));
		if (result < 1)
			throw new RuntimeException("testDeleteImpiegato : FAILED, nessun record e' stato eliminato");
		System.out.println("___fine testDeleteImpiegato : PASSED");
	}

	private static void testDeleteCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println("\n___inizio testDeleteCompagnia... ");
		List<Compagnia> elencoVociEsistenti = compagniaDAOInstance.list();
		if (elencoVociEsistenti.isEmpty())
			throw new RuntimeException("testDeleteCompagnia : FAILED, il DB e' vuoto");
		int result = compagniaDAOInstance.delete(elencoVociEsistenti.get(0));
		if (result < 1)
			throw new RuntimeException("testDeleteCompagnia : FAILED, nessun record e' stato eliminato");
		System.out.println("___fine testDeleteCompagnia : PASSED");
	}

	private static void testFindByExampleCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {

	}

	private static void testFindAllByDataAssunzioneMaggioreDiCompagnia(CompagniaDAO compagniaDAOInstance) {

	}

	private static void testFindAllByRagioneSocialeContieneCompagnia(CompagniaDAO compagniaDAOInstance) {

	}

//	private static void test(ImpiegatoDAO impiegatoDAOInstance) throws Exception {
//
//	}

//	private static void test(CompagniaDAO compagniaDAOInstance) throws Exception{
//
//	}

}
