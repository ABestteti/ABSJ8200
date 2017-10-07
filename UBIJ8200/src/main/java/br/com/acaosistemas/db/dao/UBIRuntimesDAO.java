package br.com.acaosistemas.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.acaosistemas.db.connection.ConnectionFactory;
import br.com.acaosistemas.db.model.UBIRuntimes;

public class UBIRuntimesDAO {

	private Connection conn;
	private UBIRuntimes runt;
	
	public UBIRuntimesDAO() {
		conn = new ConnectionFactory().getConnection();
	}
	
	public void closeConnection () {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	
	public String getRuntimeValue(String pRuntimeID) {
		PreparedStatement stmt = null;
		
		runt = new UBIRuntimes();
		
		try {
			stmt = conn.prepareStatement("SELECT ubru.valor FROM ubi_runtimes ubru WHERE ubru.id = ?");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			stmt.setString(1, pRuntimeID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				runt.setValor(rs.getString("valor"));
			}
			
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return runt.getValor();
	}

}
