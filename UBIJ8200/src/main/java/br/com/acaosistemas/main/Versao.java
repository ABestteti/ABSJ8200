package br.com.acaosistemas.main;
/**
 * 
 * @author Anderson Bestteti Santos
 *
 * Classe para retornar a versao do programa
 */
public final class Versao {
    
	private static String programa = "UBIJ8200";
	private static String versao   = "2.0.00.19.10.2017";
	
	public static String getStringVersao() {
		return programa + " Vrs. " + versao;
	}

}
