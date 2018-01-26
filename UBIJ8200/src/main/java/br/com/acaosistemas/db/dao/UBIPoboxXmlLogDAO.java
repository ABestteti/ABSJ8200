package br.com.acaosistemas.db.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import br.com.acaosistemas.db.connection.ConnectionFactory;
import br.com.acaosistemas.db.model.UBIPoboxXmlLog;
import br.com.acaosistemas.main.Versao;
import oracle.jdbc.OracleTypes;

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
		String             dateTimeSeq = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		DecimalFormat nextValFormatter = new DecimalFormat("00000");
		Long                   nextVal = 0L;
		CallableStatement         stmt = null;

		try {
			stmt = conn.prepareCall("{? = call ubip8100.gera_seq_chave}");
			
			// Define que o tipo de retorno da funcao sera um NUMBER
			stmt.registerOutParameter(1, OracleTypes.NUMBER);

			ResultSet rs = stmt.executeQuery();
			
			rs.next();
			nextVal = Long.parseLong(dateTimeSeq.concat(nextValFormatter.format(rs.getLong(1))));
						
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return nextVal;
	}	
}