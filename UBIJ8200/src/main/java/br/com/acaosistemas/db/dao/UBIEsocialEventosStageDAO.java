package br.com.acaosistemas.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.acaosistemas.db.connection.ConnectionFactory;
import br.com.acaosistemas.db.model.UBIEsocialEventosStage;
import br.com.acaosistemas.db.model.UBIPoboxXml;

public class UBIEsocialEventosStageDAO {

	private Connection             conn;
	private UBIEsocialEventosStage ubes;
	
	public UBIEsocialEventosStageDAO() {
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
	
	public UBIEsocialEventosStage getUBIEsocialEventosStage(String pRowID) {
		ubes = new UBIEsocialEventosStage();
		
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(
					"SELECT ubes.dt_mov FROM ubi_eventos_esocial_stage ubes WHERE ubes.rowid = ?");
			
			stmt.setString(1, pRowID);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				ubes.setDtMov(rs.getTimestamp("dt_mov"));
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return ubes;
	}
}
