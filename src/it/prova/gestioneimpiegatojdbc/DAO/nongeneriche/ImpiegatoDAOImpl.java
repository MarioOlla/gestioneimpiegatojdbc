package it.prova.gestioneimpiegatojdbc.DAO.nongeneriche;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.prova.gestioneimpiegatojdbc.DAO.AbstractMySQLDAO;
import it.prova.gestioneimpiegatojdbc.model.Compagnia;
import it.prova.gestioneimpiegatojdbc.model.Impiegato;

public class ImpiegatoDAOImpl extends AbstractMySQLDAO implements ImpiegatoDAO {

	public ImpiegatoDAOImpl(Connection connection) {
		super(connection);
	}

	@Override
	public List<Impiegato> list() throws Exception {
		if (isNotActive())
			throw new Exception("Impossibile interrogare il DB. La connessione non e' attiva.");
		List<Impiegato> result = new ArrayList<>();
		Impiegato temp;
		try (Statement s = connection.createStatement();
				ResultSet rs = s
						.executeQuery("select * from impiegato i inner join compagnia c on i.compagnia_id=c.id;")) {
			while (rs.next()) {
				temp = new Impiegato();
				temp.setId(rs.getLong("i.id"));
				temp.setNome(rs.getString("i.nome"));
				temp.setCognome(rs.getString("i.cognome"));
				temp.setCodiceFiscale(rs.getString("i.codicefiscale"));
				temp.setDataNascita(rs.getDate("i.datanascita"));
				temp.setDataAssunzione(rs.getDate("i.dataassunzione"));

				Compagnia compTemp = new Compagnia();
				compTemp.setId(rs.getLong("c.id"));
				compTemp.setRagioneSociale(rs.getString("c.ragionesociale"));
				compTemp.setFatturatoAnnuo(rs.getInt("c.fatturatoannuo"));
				compTemp.setDataFondazione(rs.getDate("c.datafondazione"));
				temp.setCompagnia(compTemp);
				result.add(temp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public Impiegato get(Long idInput) throws Exception {
		if (isNotActive())
			throw new Exception("Impossibile interrogare il DB. La connessione non e' attiva.");
		if (idInput < 1)
			throw new Exception("Impossibile interrogare il DB. Input non valido.");
		Impiegato result = null;
		;
		try (PreparedStatement ps = connection.prepareStatement(
				"select * from impiegato i inner join compagnia c on i.compagnia_id=c.id where i.id=?;")) {
			ps.setLong(1, idInput);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					result = new Impiegato();
					result.setId(rs.getLong("i.id"));
					result.setNome(rs.getString("i.nome"));
					result.setCognome(rs.getString("i.cognome"));
					result.setCodiceFiscale(rs.getString("i.codicefiscale"));
					result.setDataNascita(rs.getDate("i.datanascita"));
					result.setDataAssunzione(rs.getDate("i.dataassunzione"));

					Compagnia compTemp = new Compagnia();
					compTemp.setId(rs.getLong("c.id"));
					compTemp.setRagioneSociale(rs.getString("c.ragionesociale"));
					compTemp.setFatturatoAnnuo(rs.getInt("c.fatturatoannuo"));
					compTemp.setDataFondazione(rs.getDate("c.datafondazione"));
					result.setCompagnia(compTemp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int update(Impiegato input) throws Exception {
		if (isNotActive())
			throw new Exception("Impossibile modificare il DB. La connessione non e' attiva.");
		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Impossibile modificare il DB. Input non valido.");
		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"update impiegato set nome=?,cognome=?,codicefiscale=?,datanascita=?,dataassunzione=?,compagnia_id=? where id=?;")) {
			ps.setString(1, input.getNome());
			ps.setString(2, input.getCognome());
			ps.setString(3, input.getCodiceFiscale());
			ps.setDate(4, new java.sql.Date(input.getDataNascita().getTime()));
			ps.setDate(5, new java.sql.Date(input.getDataAssunzione().getTime()));
			ps.setLong(6, input.getCompagnia().getId());
			ps.setLong(1, input.getId());
			ps.setLong(7, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int insert(Impiegato input) throws Exception {
		if (isNotActive())
			throw new Exception("Impossibile modificare il DB. La connessione non e' attiva.");
		if (input == null)
			throw new Exception("Impossibile modificare il DB. Input non valido.");
		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"insert into impiegato (nome, cognome, codicefiscale,datanascita, dataassunzione, compagnia_id) values (?,?,?,?,?,?);")) {
			ps.setString(1, input.getNome());
			ps.setString(2, input.getCognome());
			ps.setString(3, input.getCodiceFiscale());
			ps.setDate(4, new java.sql.Date(input.getDataNascita().getTime()));
			ps.setDate(5, new java.sql.Date(input.getDataAssunzione().getTime()));
			ps.setLong(6, input.getCompagnia().getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int delete(Impiegato input) throws Exception {
		if (isNotActive())
			throw new Exception("Impossibile modificare il DB. La connessione non e' attiva.");
		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Impossibile modificare il DB. Input non valido.");
		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement("delete from impiegato where id=?;")) {
			ps.setLong(1, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Impiegato> findByExample(Impiegato input) throws Exception {
		if (isNotActive())
			throw new Exception("Impossibile effettuare la ricerca. La connessione non e' attiva.");
		if (input == null)
			throw new Exception("Impossibile effettuare la ricerca. Input non valido.");
		List<Impiegato> result = new ArrayList<>();
		String query = "select * from impiegato ";
		boolean almenoUnCampoNonNullo = false;
		if (input.getNome() != null && (!input.getNome().isBlank() || input.getNome().isEmpty())) {
			if (!almenoUnCampoNonNullo)
				query += "where ";
			query += "nome like '" + input.getNome() + "%' ";
			almenoUnCampoNonNullo = true;
		}
		if (input.getCognome() != null && (!input.getCognome().isBlank() || input.getCognome().isEmpty())) {
			if (!almenoUnCampoNonNullo)
				query += "where ";
			else
				query += "and ";
			query += "cognome like '" + input.getCognome() + "%' ";
			almenoUnCampoNonNullo = true;
		}
		if (input.getCodiceFiscale() != null
				&& (!input.getCodiceFiscale().isBlank() || input.getCodiceFiscale().isEmpty())) {
			if (!almenoUnCampoNonNullo)
				query += "where ";
			else
				query += "and ";
			query += "codicefiscale like '" + input.getCodiceFiscale() + "%' ";
			almenoUnCampoNonNullo = true;
		}
		if (input.getDataNascita() != null) {
			if (!almenoUnCampoNonNullo)
				query += "where ";
			else
				query += "and ";
			query += "datanascita >= '" + input.getDataNascita() + "' ";
			almenoUnCampoNonNullo = true;
		}
		if (input.getDataAssunzione() != null) {
			if (!almenoUnCampoNonNullo)
				query += "where ";
			else
				query += "and ";
			query += "dataassunzione >= '" + input.getDataAssunzione() + "' ";
			almenoUnCampoNonNullo = true;
		}
		if (input.getCompagnia() != null && input.getCompagnia().getId() > 0) {
			if (!almenoUnCampoNonNullo)
				query += "where ";
			else
				query += "and ";
			query += "compagnia_id = " + input.getCompagnia().getId();
		}
		query += ";";

		System.out.println(query);
		try (Statement s = connection.createStatement(); ResultSet rs = s.executeQuery(query)) {
			Impiegato temp;
			while (rs.next()) {
				temp = new Impiegato();
				temp.setNome(rs.getString("nome"));
				temp.setCognome(rs.getString("cognome"));
				temp.setCodiceFiscale(rs.getString("codicefiscale"));
				temp.setDataNascita(rs.getDate("datanascita"));
				temp.setDataAssunzione(rs.getDate("dataassunzione"));
				temp.setCompagnia(new CompagniaDAOImpl(connection).get(rs.getLong("compagnia_id")));
				temp.setId(rs.getLong("id"));
				result.add(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Impiegato> findAllByCompagnia(Compagnia input) throws Exception {
		if (input != null)
			throw new Exception("Impossibile effettuare la ricerca. Input non valido");
		return this.findByExample(new Impiegato(0, null, null, null, null, null, input));
	}

	@Override
	public List<Integer> countByDataFondazioneCompagniaGreaterThan(Date dataFondazioneDa) throws Exception {
		if (dataFondazioneDa == null)
			throw new Exception("Impossibile effettuare la ricerca. Input non valido");
		List<Integer> result = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(
				"select count(i.id) from impiegato i inner join compagnia c on i.compagnia_id=c.id where c.datafondazione>?;")) {
			ps.setDate(1, new java.sql.Date(dataFondazioneDa.getTime()));
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next())
					result.add(rs.getInt("count(i.id)"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Impiegato> findAllErroriAssunzione() throws Exception {
		if (isNotActive())
			throw new Exception("Impossibile interrogare il DB. La connessione non e' attiva.");
		List<Impiegato> result = new ArrayList<>();
		Impiegato temp;
		try (Statement s = connection.createStatement();
				ResultSet rs = s.executeQuery(
						"select * from impiegato i inner join Compagnia c on i.compagnia_id=c.id where i.dataassunzione > c.datafondazione;")) {
			while (rs.next()) {
				temp = new Impiegato();
				temp.setId(rs.getLong("i.id"));
				temp.setNome(rs.getString("i.nome"));
				temp.setCognome(rs.getString("i.cognome"));
				temp.setCodiceFiscale(rs.getString("i.codicefiscale"));
				temp.setDataNascita(rs.getDate("i.datanascita"));
				temp.setDataAssunzione(rs.getDate("i.dataassunzione"));

				Compagnia compTemp = new Compagnia();
				compTemp.setId(rs.getLong("c.id"));
				compTemp.setRagioneSociale(rs.getString("c.ragionesociale"));
				compTemp.setFatturatoAnnuo(rs.getInt("c.fatturatoannuo"));
				compTemp.setDataFondazione(rs.getDate("c.datafondazione"));
				temp.setCompagnia(compTemp);
				result.add(temp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

}
