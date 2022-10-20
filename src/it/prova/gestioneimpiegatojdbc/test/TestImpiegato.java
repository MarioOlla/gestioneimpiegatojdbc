package it.prova.gestioneimpiegatojdbc.test;

import java.sql.Connection;

import it.prova.gestioneimpiegatojdbc.DAO.Constants;
import it.prova.gestioneimpiegatojdbc.DAO.nongeneriche.CompagniaDAO;
import it.prova.gestioneimpiegatojdbc.DAO.nongeneriche.CompagniaDAOImpl;
import it.prova.gestioneimpiegatojdbc.DAO.nongeneriche.ImpiegatoDAO;
import it.prova.gestioneimpiegatojdbc.DAO.nongeneriche.ImpiegatoDAOImpl;
import it.prova.gestioneimpiegatojdbc.connection.MyConnection;

public class TestImpiegato {

	public static void main(String[] args) {
		ImpiegatoDAO impiegatoDAOInstance = null;
		CompagniaDAO compagniaDAOInstance = null;
		
		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)){
			
			compagniaDAOInstance = new CompagniaDAOImpl(connection);
			impiegatoDAOInstance = new ImpiegatoDAOImpl(connection);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
