package br.com.acaosistemas.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

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
		
		final String ORA_DUP_VAL_ON_INDEX_ERROR = "ORA-00001"; // CHAVE DUPLICADA
        final int    RETRIES                    = 3;		
		PreparedStatement stmt					= null;

		// La�o para tratar erro ORA_DUP_VAL_ON_INDEX_ERROR. Se n�o ocorrer o erro na primeira itera��o,
		// ent�o o la�o ser� interrompido. Caso, contr�rio ser�o feitas mais duas tentativas de inser��o
		// com a atualiza��o de pUbxl com novo Timestamp.		
		for (int tentativa = 1; tentativa <= RETRIES; tentativa++) {
			
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
				break; // cai fora do la�o caso a inser��o ocorra sem problema.
				
			} catch (SQLException e) {
				if (e.getMessage().contains(ORA_DUP_VAL_ON_INDEX_ERROR)) {

					if (tentativa < RETRIES) {
						try {
							// Aguarda 250 milisegundos para atualizar o TimeStamp de
							// pUbxl.setDtMov.
							Thread.sleep(250);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}

						// Atualiza pUbxl.setDtMov com o novo TimeStamp para tentar nova
						// inser��o na tabela UBI_POBOX_XML_LOG.
						pUbxl.setDtMov(new Timestamp(System.currentTimeMillis()));

					} else {
						System.out.println(
								RETRIES +
								" tentativas de inclus�o do log sem �xito.");
						e.printStackTrace();
					}
				} else {
					e.printStackTrace();
				}				
			}
			finally {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}		
		}
	}
}