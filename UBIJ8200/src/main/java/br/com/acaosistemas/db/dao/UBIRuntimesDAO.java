package br.com.acaosistemas.db.dao;

import oracle.jdbc.OracleConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.acaosistemas.db.connection.ConnectionFactory;
import br.com.acaosistemas.db.model.UBIRuntimes;

/**
 * DAO para manipulacao da tabela UBI_RUNTIMES
 * <p>
 * <b>Empresa:</b> Acao Sistemas de Informatica Ltda.
 * <p>
 * Alterações:
 * <p>
 * 2018.03.15 - ABS - Adicionado sistema de log com a biblioteca log4j2.
 *                  - Adicionado JavaDoc.
 * 
 * @author Anderson Bestteti Santos
 *
 */
public class UBIRuntimesDAO {

	private static final Logger logger = LogManager.getLogger(UBIRuntimesDAO.class);
	
	private OracleConnection conn;
	private UBIRuntimes runt;
	
	public UBIRuntimesDAO() {
		conn = new ConnectionFactory().getConnection();
	}
	
	public String getRuntimeValue(String pRuntimeID) {
		PreparedStatement stmt = null;
		
		runt = new UBIRuntimes();
		
		try {
			stmt = conn.prepareStatement(
					  "SELECT"
					+ "   ubru.valor "
					+ "FROM"
					+ "   ubi_runtimes ubru "
					+ "WHERE"
					+ "   ubru.id = ?");
			stmt.setString(1, pRuntimeID);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				runt.setValor(rs.getString("valor"));
			}
			
			rs.close();
			
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.error(e);
			}			
		}
		return runt.getValor();
	}
}
