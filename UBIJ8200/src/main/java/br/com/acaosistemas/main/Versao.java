package br.com.acaosistemas.main;

public final class Versao {
    
	private static String programa;
	private static String versao;
	
	public Versao() {
		programa = "UBIJ8200";
		versao   = "19.10.2017";
	}
	
	public static String getStringVersao() {
		return programa + " Vrs " + versao;
	}

}
