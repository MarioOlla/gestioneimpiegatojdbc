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

			testGetCompagnia(compagniaDAOInstance);

			testInsertImpiegato(impiegatoDAOInstance, compagniaDAOInstance);

			System.out.println("Sul DB di Impiegato ci sono " + impiegatoDAOInstance.list().size() + " record.");

			testUpdateImpiegato(impiegatoDAOInstance);

			testGetImpiegato(impiegatoDAOInstance);

			testFindByExampleCompagnia(compagniaDAOInstance);
			testFindByExampleImpiegato(impiegatoDAOInstance);

			testFindAllByRagioneSocialeContieneCompagnia(compagniaDAOInstance);

			testFindAllByCompagnia(impiegatoDAOInstance, compagniaDAOInstance);

			testcountByDataFondazioneCompagniaGreaterThan(impiegatoDAOInstance);

			testFindAllErroriAssunzioni(impiegatoDAOInstance, compagniaDAOInstance);

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

	private static void testGetCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println("\n__inizio testGetCompagnia...");
		List<Compagnia> elencoVociEsistenti = compagniaDAOInstance.list();
		if (elencoVociEsistenti.isEmpty())
			throw new RuntimeException("testGetCompagnia : FAILED, il DB e' vuoto");
		Compagnia risultatoRicerca = compagniaDAOInstance.get(elencoVociEsistenti.get(0).getId());
		if (!risultatoRicerca.equals(elencoVociEsistenti.get(0)))
			throw new RuntimeException("testGetCompagnia : FAILED, non ho trovato l'elemento cercato");
		System.out.println("___fine testGetCompagnia : PASSED");
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

	private static void testGetImpiegato(ImpiegatoDAO impiegatoDAOInstance) throws Exception {
		System.out.println("\n__inizio testGetImpiegato...");
		List<Impiegato> elencoVociEsistenti = impiegatoDAOInstance.list();
		if (elencoVociEsistenti.isEmpty())
			throw new RuntimeException("testGetImpiegato : FAILED, il DB e' vuoto");
		Impiegato risultatoRicerca = impiegatoDAOInstance.get(elencoVociEsistenti.get(0).getId());
		if (!risultatoRicerca.equals(elencoVociEsistenti.get(0)))
			throw new RuntimeException("testGetImpiegato : FAILED, non ho trovato l'elemento cercato");
		System.out.println("___fine testGetImpiegato : PASSED");
	}

	private static void testUpdateImpiegato(ImpiegatoDAO impiegatoDAOInstance) throws Exception {
		System.out.println("\n___inizio testUpdateImpiegato... ");
		List<Impiegato> elencoVociEsistenti = impiegatoDAOInstance.list();
		if (elencoVociEsistenti.isEmpty())
			throw new RuntimeException("testUpdateImpiegato : FAILED, il DB e' vuoto");
		Impiegato daAggiungere = new Impiegato(elencoVociEsistenti.get(0).getId(), "Marcello", "Giorgi",
				"codice fiscale di marcello giorgi", new SimpleDateFormat("dd-MM-yyyy").parse("05-03-1998"),
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
		System.out.println("\n___inizio testFindByExampleCompagnia... ");
		List<Compagnia> elencoVociEsistenti = compagniaDAOInstance.list();
		if (elencoVociEsistenti.isEmpty())
			throw new RuntimeException("testFindByExampleCompagnia : FAILED,il db e' vuoto");
		List<Compagnia> risultatoRicerca = compagniaDAOInstance
				.findByExample(new Compagnia(0, "ma", 0, null));
		if (risultatoRicerca.isEmpty())
			throw new RuntimeException("testFindByExampleCompagnia : FAILED, la ricerca non e' andata a buon fine");
		System.out.println("___fine testFindByExampleCompagnia : PASSED");
	}

	private static void testFindByExampleImpiegato(ImpiegatoDAO impiegatoDAOInstance) throws Exception {
		System.out.println("\n___inizio testFindByExampleImpiegato... ");
		List<Impiegato> elencoVociEsistenti = impiegatoDAOInstance.list();
		if (elencoVociEsistenti.isEmpty())
			throw new RuntimeException("testFindByExampleImpiegato : FAILED,il db e' vuoto");
		List<Impiegato> risultatoRicerca = impiegatoDAOInstance.findByExample(new Impiegato(0, null, "gio", null,
				null, null, null));
		if (risultatoRicerca.isEmpty())
			throw new RuntimeException("testFindByExampleImpiegato : FAILED, la ricerca non e' andata a buon fine");
		System.out.println("___fine testFindByExampleImpiegato : PASSED");
	}

	// private static void
	// testFindAllByDataAssunzioneMaggioreDiCompagnia(CompagniaDAO
	// compagniaDAOInstance) {
//
//	}

	private static void testFindAllByRagioneSocialeContieneCompagnia(CompagniaDAO compagniaDAOInstance)
			throws Exception {
		System.out.println("\n___inizio testFindAllByRagioneSocialeContieneCompagnia... ");
		List<Compagnia> elencoVociEsistenti = compagniaDAOInstance.list();
		if (elencoVociEsistenti.isEmpty())
			throw new RuntimeException("testFindAllByRagioneSocialeContieneCompagnia : FAILED,il db e' vuoto");
		List<Compagnia> risultatoRicerca = compagniaDAOInstance.findByExample(new Compagnia(0, "%rel", 0, null));
		if (risultatoRicerca.isEmpty())
			throw new RuntimeException(
					"testFindAllByRagioneSocialeContieneCompagnia : FAILED, la ricerca non e' andata a buon fine");
		System.out.println("___fine testFindAllByRagioneSocialeContieneCompagnia : PASSED");
	}

	private static void testFindAllByCompagnia(ImpiegatoDAO impiegatoDAOInstance, CompagniaDAO compagniaDAOInstance)
			throws Exception {
		System.out.println("___inizio testFindAllByCompagnia...");
		if (impiegatoDAOInstance.list().isEmpty() || compagniaDAOInstance.list().isEmpty())
			throw new RuntimeException("testFindAllByCompagnia : FAILED, uno o piu database vuoti");
		List<Impiegato> risultatoRicerca = impiegatoDAOInstance.findAllByCompagnia(compagniaDAOInstance.list().get(0));
		if (risultatoRicerca.isEmpty())
			throw new RuntimeException(
					"testFindAllByCompagnia : FAILED, la ricerca non ha prodotto i risultati desiderati");
		System.out.println("___fine testFindAllByCompagnia : PASSED");
	}

	private static void testcountByDataFondazioneCompagniaGreaterThan(ImpiegatoDAO impiegatoDAOInstance)
			throws Exception {
		System.out.println("___inizio testcountByDataFondazioneCompagniaGreaterThan...");
		if (impiegatoDAOInstance.list().isEmpty())
			throw new RuntimeException("testcountByDataFondazioneCompagniaGreaterThan :FAILED, DB vuoto");
		List<Integer> risultatoRicerca = impiegatoDAOInstance
				.countByDataFondazioneCompagniaGreaterThan(new SimpleDateFormat("dd-MM-yyyy").parse("05-03-1990"));
		if (risultatoRicerca.isEmpty())
			throw new RuntimeException(
					"testcountByDataFondazioneCompagniaGreaterThan :FAILED,la ricerca non ha prodotto i risultati attesi");
		System.out.println("___fine testcountByDataFondazioneCompagniaGreaterThan : PASSED");
	}

	private static void testFindAllErroriAssunzioni(ImpiegatoDAO impiegatoDAOInstance,
			CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println("___inizio testFindAllErroriAssunzioni...");
		impiegatoDAOInstance.insert(new Impiegato(0, "Fabio", "Torti", "cf di fabio",
				new SimpleDateFormat("dd-MM-yyyy").parse("08-08-1965"),
				new SimpleDateFormat("dd-MM-yyyy").parse("08-08-1989"), compagniaDAOInstance.list().get(0)));
		List<Impiegato> risultatoRicerca = impiegatoDAOInstance.findAllErroriAssunzione();
		if (risultatoRicerca.isEmpty())
			throw new RuntimeException(
					"testFindAllErroriAssunzioni : FAILED, la ricerca non ha prodotto i risultati attesi");
		impiegatoDAOInstance.delete(risultatoRicerca.get(0));
		System.out.println("___fine testFindAllErroriAssunzioni");
	}

}
