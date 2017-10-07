package br.com.acaosistemas.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	public Connection getConnection() {
	        try {

	            Class.forName("oracle.jdbc.driver.OracleDriver");

	        } catch (ClassNotFoundException e) {

	            System.err.println("Biblioteca JDBC da Oracle não encontrada.");
	            throw new RuntimeException(e);
	        }
	        
	        try {

	        	return DriverManager.getConnection(
	                    "jdbc:oracle:thin:@"+DBConnectionInfo.getDbStrConnect(), 
	                    DBConnectionInfo.getDbUserName(), 
	                    DBConnectionInfo.getDbPassWord());

	        } catch (SQLException e) {

	            System.out.println("Erro durante a conexão com o banco de dados.");
	            System.out.println("Revise se os parâmetros usuário, senha e string"); 
	            System.out.println("de conexão estão corretos.");
	            throw new RuntimeException(e);
	        }
		}
	}
