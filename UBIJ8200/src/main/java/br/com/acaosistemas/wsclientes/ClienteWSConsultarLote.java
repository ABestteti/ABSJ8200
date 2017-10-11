package br.com.acaosistemas.wsclientes;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import br.com.acaosistemas.db.dao.UBILotesEsocialDAO;
import br.com.acaosistemas.db.dao.UBIRuntimesDAO;
import br.com.acaosistemas.db.model.UBILotesEsocial;
import br.com.acaosistemas.frw.util.HttpUtils;

public class ClienteWSConsultarLote {

	/**
	 * Construtor default da classe
	 */
	public ClienteWSConsultarLote() {
		// TODO Auto-generated constructor stub
	}

	public void execWebService(String pRowID) {
		String parametros = new String();
		String wsEndPoint;
		
		// Objeto de representacao de um registro da
		// da tabela UBI_POBOX_XML
		UBILotesEsocial uble;
		
		// Objects de acesso as tabelas do banco de dados
		UBILotesEsocialDAO ubleDAO = new UBILotesEsocialDAO();
		UBIRuntimesDAO runtimeDAO  = new UBIRuntimesDAO();
		
		// Recupera do banco de dados a informacao do runtime UBIWSCONSULTALOTE
		wsEndPoint = runtimeDAO.getRuntimeValue("UBIWSCONSULTALOTE");
		
		// Fecha a conexao com o banco de dados
		runtimeDAO.closeConnection();
		
		// Recupera do banco de dados as informacoes da tabela
		// UBI_LOTES_ESOCIAL
		uble = ubleDAO.getUBILotesEsocial(pRowID);

		// Monta o parametro de chamada do web service
		parametros  = uble.getUbiLoteNumero().toString();
		
		try {
			URL url = new URL(wsEndPoint+parametros);
			
			HttpURLConnection request = (HttpURLConnection) url.openConnection();			

			// Define que a requisicao pode obter informacoes de retorno.
		    request.setDoOutput(true);
						
			// Define o metodo da requisicao
			request.setRequestMethod("GET");
			
			// Conecta na URL
			request.connect();
			
			if (request.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("HTTP error code : "+ request.getResponseCode() + " [" + wsEndPoint + "]");
			}
			else {
				System.out.println("HTTP code .....: " + request.getResponseMessage());
				System.err.println("Message from ws: " + HttpUtils.readResponse(request) + " [" + wsEndPoint + "]");
			}
						
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
