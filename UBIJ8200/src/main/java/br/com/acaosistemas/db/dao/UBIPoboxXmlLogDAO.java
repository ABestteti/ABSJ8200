package br.com.acaosistemas.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
					"INSERT INTO ubi_pobox_xml_log (ubpx_seq_reg,seq_reg,dt_mov,num_erro,mensagem,status) VALUES (?,?,?,?,?,?)");

			stmt.setLong(1, pUbxl.getUbpxSeqReg());
			stmt.setLong(2, getNextSeqReg());
			stmt.setTimestamp(3, pUbxl.getDtMov());
			stmt.setLong(4, pUbxl.getNumErro());
			stmt.setString(5, Versao.getStringVersao() + "\n" + pUbxl.getMensagem());
			stmt.setInt(6, pUbxl.getStatus().getId());

			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/***
	 * 
	 * @return
	 * Retorna o proximo valor da sequencia de banco UBI_SEQ
	 */
	private Long getNextSeqReg() {
		Long nextVal = 0L;
		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement("SELECT ubi_seq.nextval FROM dual");

			ResultSet rs = stmt.executeQuery();
			
			rs.next();
			nextVal = rs.getLong(1);

			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
		return nextVal;
	}	
}