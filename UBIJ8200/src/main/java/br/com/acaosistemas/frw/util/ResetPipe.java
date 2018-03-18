package br.com.acaosistemas.frw.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import oracle.jdbc.OracleTypes;

/**
 * Classe utilitaria limpar o pipe de comunicao do banco Oracle.
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
public final class ResetPipe {

	private static final Logger logger = LogManager.getLogger(ResetPipe.class);
	
    public static void reset(Connection pConn, String pPipeName) {
    	CallableStatement stmt;
    	// Prepara a chamada da funcao no banco de dados
		try {
			stmt = pConn.prepareCall("{? = call dbms_pipe.remove_pipe(?)}");

			// Define que o tipo de retorno da funcao sera um NUMBER
			stmt.registerOutParameter(1, OracleTypes.NUMBER);

			// Define o nome do pipe que sera lido do banco.
			stmt.setString(2, pPipeName);

			// Executa a funcao do banco
			stmt.execute();

		} catch (SQLException e) {
			logger.error(e);
		}   	
    }
}
