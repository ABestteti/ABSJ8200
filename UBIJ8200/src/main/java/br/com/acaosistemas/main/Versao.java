package br.com.acaosistemas.main;
/**
 * 
 * @author Anderson Bestteti Santos
 *
 * Classe para retornar a versao do programa
 */
public final class Versao {
    
	private static String empresa   = "Universo Desenvolvimento de Sistemas Ltda\n";
	private static String copyright = "Direitos Autorais (c) 2017-2017\n";
	private static String descricao = "Servico de consumo do web service do correio.\n";
	private static String programa  = "UBIJ8200";
	private static String versao    = "3.0.00.31.10.2017";
	
	
	public static String getStringVersao() {
		return programa + " Vrs. " + versao;
	}
	
	public static String ver() {
		return empresa+copyright+descricao+getStringVersao();
	}

}
