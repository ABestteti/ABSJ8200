package br.com.acaosistemas.main;
/**
 * Classe para retornar a versao do programa
 * 
 * @author Anderson Bestteti Santos
 *
 */
public final class Versao {
    
	private static String empresa   = "Universo Desenvolvimento de Sistemas Ltda\n";
	private static String copyright = "Direitos Autorais (c) 2017-2018\n";
	private static String descricao = "Servico de consumo do web service do correio.\n";
	private static String programa  = "UBIJ8200";
	private static String versao    = "3.0.00.14.03.2018";
	
	
	public static String getStringVersao() {
		return programa + " Vrs. " + versao;
	}
	
	public static String ver() {
		return empresa+copyright+descricao+getStringVersao();
	}

}
