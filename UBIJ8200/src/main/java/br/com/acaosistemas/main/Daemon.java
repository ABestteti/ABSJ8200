package br.com.acaosistemas.main;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import br.com.acaosistemas.db.connection.ConnectionFactory;
import br.com.acaosistemas.db.connection.DBConnectionInfo;
import br.com.acaosistemas.db.dao.UBIRuntimesDAO;
import oracle.jdbc.OracleTypes;

/**
 * 
 * @author Anderson Bestteti
 * Classe reponsavel por ler o pipe de comunicacao do banco
 * 
 * Referencias:
 *  https://stackoverflow.com/questions/19333011/how-to-call-a-stored-function-from-jdbc
 *  http://docs.oracle.com/cd/A84870_01/doc/java.816/a81354/samapp2.htm
 *  https://docs.oracle.com/cd/B19306_01/java.102/b14355/toc.htm
 */
public class Daemon {

	private static final int STOP_DAEMON      = 1;
	private static final int CONSULTAR_STATUS = 2;
	
	private static final int DEAMON_ALIVE     = 1;

	private Connection conn;
	private CallableStatement stmt;

	public static void main(String[] args) {

		if (args.length != 3) {
			System.out.println(Versao.ver()+"\n");
			System.out.println("Quantidade de parametros insuficientes.");
			System.out.println("Utilize o comando abaixo para executar a aplicacao, utilizando o Java 1.8 ou superior:");
			System.out.println("java -jar UBIJ8200.jar usuarioDB senhaDB servidorDB:portaListner:instanciaDB");
			System.exit(1);
		}
		
		Daemon procPoboxXml = new Daemon();
		
		String dbUserName = args[0];
		String dbPassWord = args[1];
		String dbStrConn  = args[2];

		
		// Salva em memoria as informacoes de conexao com o banco
		// de dados para posterior uso pela classes DAO.
		DBConnectionInfo.setDbUserName(dbUserName);
		DBConnectionInfo.setDbPassWord(dbPassWord);
		DBConnectionInfo.setDbStrConnect(dbStrConn);
		
		// Entra no loop de leitura da tabela UBI_POBOX_XML
		procPoboxXml.lerPipeDB();

	}
	
    private void lerPipeDB() {
		// Rowid de uma linha da table UBI_POBOX_XML
		String pipeConteudo  = "";
		
		// Variaveis para trabalhar com o pipe de banco
		String pipeName   = "";
		int    pipeCmd    = -1;
		int    pipeStatus = -1;
		
		// Controla o loop de leitura do PIPE
		boolean stopDeamon = false;
		
		// Objects de acesso as tabelas do banco de dados
		UBIRuntimesDAO runtimeDAO = new UBIRuntimesDAO();
		
		pipeName = runtimeDAO.getRuntimeValue("PIPEUBI");
		runtimeDAO.closeConnection();
		
		// Abre conexao com o banco para leitura do pipe do
		// banco de dados.
		conn = new ConnectionFactory().getConnection();
		
		System.out.println("Processando registros dos correios...");
		
		// Loop para leitura constante do pipe de comunicacao
		// do deamon e por procura de registros com status 0 (nao processado)
		// na tabela UBI_POBOX_XML
		while (!stopDeamon) {
			
			// Pausa a execucao da thread principal por 5 segundos
			// Com iso, e liberado o lock da dbms_pipe, permitindo que a 
			// consiliacao de usuario possa conceder grant da package para
			// o usuario que esta sem conciliado.
            try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				throw new RuntimeException(e1);
			}
            
			// Prepara a chamada da funcao no banco de dados
			try {
				stmt = conn.prepareCall("{? = call dbms_pipe.receive_message(?,1)}");

				// Define que o tipo de retorno da funcao sera um NUMBER
				stmt.registerOutParameter(1, OracleTypes.NUMBER);

				// Define o nome do pipe que sera lido do banco.
				stmt.setString(2, pipeName);

				// Executa a funcao do banco
				stmt.execute();

				// Recupera o status da leitura do pipe do banco
				pipeStatus = stmt.getInt(1);

			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			
			// Se o retorno do pipe foi obtido com sucesso (valor 0),
			// busca o comando enviado.
			if (pipeStatus == 0) {

				try {
					
					stmt = conn.prepareCall("BEGIN dbms_pipe.unpack_message(?); dbms_pipe.unpack_message(?); END;");

					// Define que o parametro e do tipo OUT, retornando um NUMBER
					// e um VARCHAR, respectivamente.
					stmt.registerOutParameter(1, OracleTypes.NUMBER);
					stmt.registerOutParameter(2, OracleTypes.VARCHAR);

					// Executa a funcao do banco
					stmt.execute();

					// Recupera os valores retornados do pipe
					pipeCmd = stmt.getInt(1);
					pipeConteudo = stmt.getString(2);
							;
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}

				switch (pipeCmd) {
				case CONSULTAR_STATUS:
					System.out.println("Recebido comando status do servico!");
					
					// Nesse caso o objeto pipeConteudo armazena o nome do
					// pipe de retorno que sera usado para enviar o status
					// de volta para o PL/SQL, sinalizando que o daemon esta
					// rodando.
					statusDaemon(pipeConteudo);
			     	break;
				case STOP_DAEMON:
					System.out.println("Recebido comando stop do servico!");
					stopDeamon = true;
					break;
				}
			}
			
			try {
				if (!stmt.isClosed()) {
				   stmt.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException(e) ;
			}
			
			// Inicia o processo de leitura dos registros não processados
			// na tabela UBI_POBOX_XML
			new ProcessarUbiPoboxXml().lerRegistrosNaoProcessados();
		}
		
		try {
			stmt.close();
	        conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e) ;
		}
		
		System.out.println("Servico encerrado por requisicao do usuario.");
		System.exit(0);
	}
	
	private void statusDaemon(String pPipeReturn) {
		try {
			if (!stmt.isClosed()) {
				stmt.close();
			}
			
			// Retorna o status do deamon, informando
			// que ele esta ativo: DEAMON_ALIVE
			//dbms_pipe.pack_message(pipe_name);
			stmt = conn.prepareCall("BEGIN dbms_pipe.pack_message(?); ? := dbms_pipe.send_message(?,2); END;");
			
			// Manda para o pipe o status que representa que o
			// o deamon esta rodando.
			stmt.setInt(1, DEAMON_ALIVE);
			stmt.registerOutParameter(2, OracleTypes.NUMBER);			
			stmt.setString(3, pPipeReturn);
			
			stmt.execute();
			
			stmt.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	    
	}
}
