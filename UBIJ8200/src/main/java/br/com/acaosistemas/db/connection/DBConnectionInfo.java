package br.com.acaosistemas.db.connection;

public final class DBConnectionInfo {

	// Nome do usuario do banco de dados
	private static String dbUserName;

	// Senha do usuario do banco de dados
	private static String dbPassWord;

	// A string de conexao deve ser no seguinte
	// format: localhost:1521:xe
	private static String dbStrConnect;
	
	private DBConnectionInfo() {
		dbUserName   = null;
		dbPassWord   = null;
		dbStrConnect = null;
	}

	public static String getDbUserName() {
		return dbUserName;
	}

	public static void setDbUserName(String dbUserName) {
		DBConnectionInfo.dbUserName = dbUserName;
	}

	public static String getDbPassWord() {
		return dbPassWord;
	}

	public static void setDbPassWord(String dbPassWord) {
		DBConnectionInfo.dbPassWord = dbPassWord;
	}

	public static String getDbStrConnect() {
		return dbStrConnect;
	}

	public static void setDbStrConnect(String dbStrConnect) {
		DBConnectionInfo.dbStrConnect = dbStrConnect;
	}
	
	
}
