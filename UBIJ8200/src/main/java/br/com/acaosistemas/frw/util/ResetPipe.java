package br.com.acaosistemas.frw.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

public final class ResetPipe {

    public static void reset(Connection pConn, String pPipeName) {
    	CallableStatement stmt;
    	Integer pipeStatus;
    	
		// Prepara a chamada da funcao no banco de dados
		try {
			stmt = pConn.prepareCall("{? = call dbms_pipe.remove_pipe(?)}");

			// Define que o tipo de retorno da funcao sera um NUMBER
			stmt.registerOutParameter(1, OracleTypes.NUMBER);

			// Define o nome do pipe que sera lido do banco.
			stmt.setString(2, pPipeName);

			// Executa a funcao do banco
			stmt.execute();

			// Recupera o status da leitura do pipe do banco
			pipeStatus = stmt.getInt(1);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}   	
    }
}
