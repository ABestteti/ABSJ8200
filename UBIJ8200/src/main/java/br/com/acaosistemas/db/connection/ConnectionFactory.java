package br.com.acaosistemas.db.connection;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import oracle.jdbc.OracleConnection;
import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;

/**
 * Classe reponsavel estabelecer a conexao com o banco Oracle.
 * <p>
 * <b>Empresa:</b> Acao Sistemas de Informatica Ltda.
 * <p>
 * Alterações:
 * <p>
 * 2018.03.15 - ABS - Adicionado sistema de log com a biblioteca log4j2.
 *                  - Adicionado suporte do PoolDataSource da Oracle.
 * 
 * @author Anderson Bestteti
 */
public class ConnectionFactory {

	private static final Logger logger = LogManager.getLogger(ConnectionFactory.class);
	
	// Get the PoolDataSource for UCP
	static PoolDataSource   pds    = null;
	static OracleConnection connDB = null;
	
	public OracleConnection getConnection() {
	        try {	
	        	if (pds == null) {
	        		logger.info("Conectando no banco de dados...");

	        		pds = PoolDataSourceFactory.getPoolDataSource();

	        		// Prepara a conexao com o banco Oracle
	        		pds.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");	        
	        		pds.setURL("jdbc:oracle:thin:@"+DBConnectionInfo.getDbStrConnect());
	        		pds.setUser(DBConnectionInfo.getDbUserName());
	        		pds.setPassword(DBConnectionInfo.getDbPassWord());

	        		// Define propriedades do pool de conexao do banco Oracle
	        		pds.setConnectionPoolName("JDBC_UCP_POOL");
	        		pds.setInitialPoolSize(1);
	        		pds.setMinPoolSize(1);
	        		pds.setMaxPoolSize(5);
	        		
		           	// Cria uma conexao com o banco Oracle
	        		connDB = (OracleConnection) pds.getConnection();
	        		
	        		logger.info(  "Conectado ao banco "
	        				    + DBConnectionInfo.getDbUserName()
	        				    + "@"
	        				    + DBConnectionInfo.getDbStrConnect()
	        		            + " com sucesso.");
	        	}
	        } catch (SQLException e) {
	            logger.fatal(  "\nErro durante a conexao com o banco de dados.\n"
	                         + "Revise se os parametros usuario, senha e string\n"
	            	         + "de conexao estao corretos, ou se existe algum problema com a rede.",
	            	        e);
	            throw new RuntimeException(e);
	        }
        	return connDB;
		}
	}