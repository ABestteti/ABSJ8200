package br.com.acaosistemas.db.dao;

import oracle.jdbc.OracleConnection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.acaosistemas.db.connection.ConnectionFactory;
import br.com.acaosistemas.db.model.UBIPoboxXmlLog;
import br.com.acaosistemas.main.Versao;
import oracle.jdbc.OracleTypes;

/**
 * DAO para manipulacao da tabela UBI_POBOX_XML_LOG
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
public class UBIPoboxXmlLogDAO {

	private static final Logger logger = LogManager.getLogger(UBIPoboxXmlLogDAO.class);
	
	private OracleConnection conn;
	
	public UBIPoboxXmlLogDAO() {
		conn = new ConnectionFactory().getConnection();
	}
	
	public void insert(UBIPoboxXmlLog pUbxl) {
		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(
					  "INSERT INTO "
					+ "   ubi_pobox_xml_log "
					+ "   (ubpx_seq_reg,"
					+ "    seq_reg,"
					+ "    dt_mov,"
					+ "    num_erro,"
					+ "    mensagem,"
					+ "    status) "
					+ "VALUES "
					+ "    (?,"
					+ "     ?,"
					+ "     ?,"
					+ "     ?,"
					+ "     ?,"
					+ "     ?)");

			stmt.setLong(1, pUbxl.getUbpxSeqReg());
			stmt.setLong(2, getNextSeqReg());
			stmt.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
			stmt.setLong(4, pUbxl.getNumErro());
			stmt.setString(5, Versao.getStringVersao() + "\n" + pUbxl.getMensagem());
			stmt.setInt(6, pUbxl.getStatus().getId());

			stmt.execute();
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.error(e);
			}
		}
	}
	
	/***
	 * Retorna a sequencia gerada pelo package de banco ubip8100.gera_seq_chave.
	 * @return Um numero que representa a sequencia gerada pela funcao de banco.
	 * 
	 */
	private Long getNextSeqReg() {
		Long              nextVal = 0L;
		CallableStatement    stmt = null;

		try {
			// Executa a funcao gera_seq_chave do package ubip8100. 
			stmt = conn.prepareCall("{? = call ubip8100.gera_seq_chave}");
			
			// Define que o tipo de retorno da funcao sera um NUMBER
			stmt.registerOutParameter(1, OracleTypes.NUMBER);
			stmt.execute();
			
			nextVal = stmt.getLong(1);
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.error(e);
			}
		}
		return nextVal;
	}	
}