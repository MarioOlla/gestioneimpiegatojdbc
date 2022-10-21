package it.prova.gestioneimpiegatojdbc.DAO.nongeneriche;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.prova.gestioneimpiegatojdbc.DAO.AbstractMySQLDAO;
import it.prova.gestioneimpiegatojdbc.model.Compagnia;
import it.prova.gestioneimpiegatojdbc.util.DateConverter;

public class CompagniaDAOImpl extends AbstractMySQLDAO implements CompagniaDAO {

	public CompagniaDAOImpl(Connection connection) {
		super(connection);
	}

	@Override
	public List<Compagnia> list() throws Exception {
		if (isNotActive())
			throw new Exception("Impossibile interrogare il DB. La connessione non e' attiva.");
		List<Compagnia> result = new ArrayList<>();
		Compagnia compTemp = null;
		try (Statement s = connection.createStatement(); ResultSet rs = s.executeQuery("select * from compagnia;")) {
			while (rs.next()) {
				compTemp = new Compagnia();
				compTemp.setId(rs.getLong("id"));
				compTemp.setRagioneSociale(rs.getString("ragionesociale"));
				compTemp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
				compTemp.setDataFondazione(DateConverter.fromSqlToUtil(rs.getDate("datafondazione")));
				result.add(compTemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public Compagnia get(Long idInput) throws Exception {
		if (isNotActive())
			throw new Exception("Impossibile interrogare il DB. La connessione non e' attiva.");
		if (idInput < 1)
			throw new Exception("Impossibile interrogare il DB. Input non valido.");
		Compagnia result = null;
		try (PreparedStatement ps = connection.prepareStatement("select * from compagnia where id=?;")) {
			ps.setLong(1, idInput);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					result = new Compagnia();
					result.setId(rs.getLong("id"));
					result.setRagioneSociale(rs.getString("ragionesociale"));
					result.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
					result.setDataFondazione(DateConverter.fromSqlToUtil(rs.getDate("datafondazione")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int update(Compagnia input) throws Exception {
		if (isNotActive())
			throw new Exception("Impossibile modificare il DB. La connessione non e' attiva.");
		if (input == null)
			throw new Exception("Impossibile modificare il DB. Input non valido.");
		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"update compagnia set ragionesociale=?, fatturatoannuo=?, datafondazione=? where id=?;")) {
			ps.setString(1, input.getRagioneSociale());
			ps.setInt(2, input.getFatturatoAnnuo());
			ps.setDate(3, DateConverter.fromUtilToSql (input.getDataFondazione()));
			ps.setLong(4, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int insert(Compagnia input) throws Exception {
		if (isNotActive())
			throw new Exception("Impossibile modificare il DB. La connessione non e' attiva.");
		if (input == null)
			throw new Exception("Impossibile modificare il DB. Input non valido.");
		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"insert into compagnia (ragionesociale, fatturatoannuo, datafondazione) values (?,?,?);")) {
			ps.setString(1, input.getRagioneSociale());
			ps.setInt(2, input.getFatturatoAnnuo());
			ps.setDate(3, DateConverter.fromUtilToSql (input.getDataFondazione()));
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int delete(Compagnia input) throws Exception {
		if (isNotActive())
			throw new Exception("Impossibile modificare il DB. La connessione non e' attiva.");
		if (input == null)
			throw new Exception("Impossibile modificare il DB. Input non valido.");
		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement("delete from compagnia where id=?;")) {
			ps.setLong(1, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Compagnia> findByExample(Compagnia input) throws Exception {
		if (isNotActive())
			throw new Exception("Impossibile effettuare la ricerca. La connessione non e' attiva.");
		if (input == null)
			throw new Exception("Impossibile effettuare la ricerca. Input non valido.");
		List<Compagnia> result = new ArrayList<>();
		String query = "select * from compagnia ";
		boolean almenoUnCampoNonNullo = false;
		if (input.getRagioneSociale() != null
				&& (!input.getRagioneSociale().isBlank() || input.getRagioneSociale().isEmpty())) {
			if (!almenoUnCampoNonNullo)
				query += "where ";
			query += "ragionesociale like '" + input.getRagioneSociale() + "%' ";
			almenoUnCampoNonNullo = true;
		}
		if (input.getFatturatoAnnuo() != null && input.getFatturatoAnnuo() >= 0) {
			if (!almenoUnCampoNonNullo)
				query += "where ";
			else
				query += "and ";
			query += "fatturatoannuo >= " + input.getFatturatoAnnuo() + " ";
			almenoUnCampoNonNullo = true;
		}
		if (input.getDataFondazione() != null) {
			if (!almenoUnCampoNonNullo)
				query += "where ";
			else
				query += "and ";
			query += "datafondazione >= '" + DateConverter.fromUtilToSql( input.getDataFondazione()) + "' ";
		}
		query += ";";
		try (Statement s = connection.createStatement(); ResultSet rs = s.executeQuery(query)) {
			Compagnia temp;
			while (rs.next()) {
				temp = new Compagnia();
				temp.setRagioneSociale(rs.getString("ragionesociale"));
				temp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
				temp.setDataFondazione(DateConverter.fromSqlToUtil(rs.getDate("datafondazione")));
				temp.setId(rs.getLong("ID"));
				result.add(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Compagnia> findAllByDataAssunzioneMaggioreDi(Date dataAssunzioneDa) throws Exception {
		if (isNotActive())
			throw new Exception("Impossibile effettuare la ricerca. La connessione non e' attiva.");
		if (dataAssunzioneDa == null)
			throw new Exception("Impossibile effettuare la ricerca. Input non valido.");
		List<Compagnia> result = new ArrayList<>();
		try(PreparedStatement ps = connection.prepareStatement("select distinct * from compagnia c inner join impiegato i on i.compagnia_id=c.id where i.dataassunzione > ?")){
			ps.setDate(1, DateConverter.fromUtilToSql(dataAssunzioneDa));
			try(ResultSet rs = ps.executeQuery()){
				Compagnia temp ;
				while(rs.next()) {
					temp = new Compagnia();
					temp.setId(rs.getLong("c.id"));
					temp.setRagioneSociale(rs.getString("ragionesociale"));
					temp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
					temp.setDataFondazione(DateConverter.fromSqlToUtil(rs.getDate("datafondazione")));
					result.add(temp);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Compagnia> findAllByRagioneSocialeContiene(String lettereContenute) throws Exception {
		if (isNotActive())
			throw new Exception("Impossibile effettuare la ricerca. La connessione non e' attiva.");
		if (lettereContenute == null)
			throw new Exception("Impossibile effettuare la ricerca. Input non valido.");
		List<Compagnia> result = new ArrayList<>();
		result = this.findByExample(new Compagnia(0, lettereContenute, 0, null));
		return result;
	}

}
