package br.com.acaosistemas.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.acaosistemas.db.connection.ConnectionFactory;
import br.com.acaosistemas.db.model.UBILotesEsocial;


public class UBILotesEsocialDAO {

	private Connection      conn;
	private UBILotesEsocial uble;
	
	public UBILotesEsocialDAO() {
		conn = new ConnectionFactory().getConnection();
	}
	
	public UBILotesEsocial getUBILotesEsocial(String pRowID ) {
		uble = new UBILotesEsocial();
		
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(
					"SELECT uble.ubi_lote_numero FROM ubi_lotes_esocial uble WHERE uble.rowid = ?");
			
			stmt.setString(1, pRowID);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				uble.setUbiLoteNumero(rs.getLong("ubi_lote_numero"));
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}		
		
		return uble;
	}

}
