package br.com.acaosistemas.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.acaosistemas.db.connection.ConnectionFactory;
import br.com.acaosistemas.db.model.UBIPoboxXmlLog;
import br.com.acaosistemas.main.Versao;

public class UBIPoboxXmlLogDAO {

	private Connection conn;
	
	public UBIPoboxXmlLogDAO() {
		conn = new ConnectionFactory().getConnection();
	}
	
	public void closeConnection () {
		try {
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void insert(UBIPoboxXmlLog pUbxl) {
		
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(
					"INSERT INTO ubi_pobox_xml_log (ubpx_dt_mov,dt_mov,num_erro,mensagem,status) VALUES (?,?,?,?,?)");
		
			stmt.setTimestamp(1, pUbxl.getUbpxDtMov());
			stmt.setTimestamp(2, pUbxl.getDtMov());
			stmt.setLong(3, pUbxl.getNumErro());
			stmt.setString(4, Versao.getStringVersao() + "\n" + pUbxl.getMensagem());
			stmt.setInt(5, pUbxl.getStatus().getId());
			
			stmt.execute();
			stmt.close();
			
			conn.commit();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
