/**
 * 
 */
package br.com.acaosistemas.wsclientes;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

import br.com.acaosistemas.db.enumeration.StatusPoboxXMLEnum;
import br.com.acaosistemas.db.model.UBIPoboxXml;
import br.com.acaosistemas.frw.util.ExceptionUtils;
import br.com.acaosistemas.frw.util.HttpUtils;

/**
 * @author Anderson Bestteti Santos
 *
 * Classe cliente do web service do correio do UBI
 * 
 * Referencias:
 *   http://www.devmedia.com.br/consumindo-um-web-service-rest-com-java/27286
 *   https://pt.stackoverflow.com/questions/97370/como-fazer-post-com-par%C3%A2metros-em-webservice-rest-em-java
 *   http://jersey.576304.n2.nabble.com/Example-of-consuming-raw-binary-content-td6218279.html
 *   https://www.caelum.com.br/apostila-java-web/bancos-de-dados-e-jdbc/#2-10-dao-data-access-object
 *  
 */
public class ClienteWSCorreios {
	
	/**
	 * Construtor default da classe
	 */
	public ClienteWSCorreios() {
	}
	
	public void execWebService(UBIPoboxXml pUbpxRow) throws MalformedURLException, IOException {
		String parametros = new String();
		String wsEndPoint;
		
		if (pUbpxRow.getWsEndpoint() == null) {
			// Caso nao exista o endereco de endpoint definido, deve ser gerado uma excecao 
			// para invalidar o envolope lido do banco de dados.
			throw new IOException("Nao foi definido no envelope o endereco do servico web (endpoint).");
		}
		
		// Recupera o endereco de endpoint do web service da ubi_pobox_xml
		// remota que esta grava na ubi_pobox_xml local.
		wsEndPoint = pUbpxRow.getWsEndpoint();
		
		// Antes de invocar o web service o atributo Status precisa ser
		// ajustado para NAO_PROCESSADO;
		pUbpxRow.setStatus(StatusPoboxXMLEnum.NAO_PROCESSADO);
		
        // Monta os parametros para chamada do servico web da POBOX_XML
		// remota.
		parametros  = "nomeTapi=" + pUbpxRow.getNomeTapi() + "&";
		parametros += "sistemaDestinatario=" + pUbpxRow.getSistemaDestinatario() + "&";
		parametros += "sistemaRemetente=" + pUbpxRow.getSistemaRemetente() + "&";
		parametros += "wsEndpoint=" + pUbpxRow.getWsEndpoint() + "&";
		parametros += "tableName=" + pUbpxRow.getTableName() + "&";
		parametros += "status=" + pUbpxRow.getStatus().getId() + "&";
		parametros += "tipoRecurso=" + pUbpxRow.getTipoRecurso().getId() + "&";
		parametros += "cnpj=" + pUbpxRow.getCnpj();
		
		try {			
			// Cria a URL para posterior invocacao do servico web.
			URL url = new URL(wsEndPoint+parametros);
			
			// Cria uma requisicao de conexao com o servidor remoto.
			HttpURLConnection request = (HttpURLConnection) url.openConnection();			

			// Define que a conexao pode enviar informacoes e obte-las de volta:
			request.setDoOutput(true);
			request.setDoInput(true);
			
			// Define o content-type para trabalhar com o corpo da mensagem HTTP em
			// formato octet-stream, pois o web service da POBOX espera receber esse
			// formato para manter intacto o formato UTF-8 do XML.
			request.setRequestProperty("Content-Type", "application/octet-stream");
			request.setRequestProperty("Content-Length", String.valueOf(pUbpxRow.getXml().length()));
			request.setRequestProperty("Transfer-Encoding", "chunked");
			
			// Define o metodo da requisicao
			request.setRequestMethod("POST");
			
			// Conecta na URL
			request.connect();
			
			// Escreve o objeto XML usando o OutputStream da requisicao
			// para enviar para o web service.
            try (OutputStream outputStream = request.getOutputStream()) {
            	    outputStream.write(pUbpxRow.getXml().getBytes("UTF-8"));
            }
			
			if (request.getResponseCode() != HttpURLConnection.HTTP_OK) {
			    System.out.println("     Codigo de erro HTTP..: "+ request.getResponseCode() + " [" + wsEndPoint + "]");
			    
			    if (request.getResponseCode() == HttpURLConnection.HTTP_INTERNAL_ERROR) {
				    throw new MalformedURLException("Codigo HTTP retornado: " + 
			                                        request.getResponseCode() + 
			                                        " [" + wsEndPoint + "]\n" +
			                                        "Parametros: "            + 
			                                        parametros);
			    }
			    else {
			    	throw new IOException("Codigo HTTP retornado: "     + 
			                              request.getResponseCode() + 
			                              " [" + wsEndPoint + "]\n" +
			                              "Parametros: "            +
			                              parametros);
			    }
			}
			else {
				System.out.println("     Codigo HTTP..........: " + request.getResponseMessage());
				System.out.println("     Mensagem do servico..: " + HttpUtils.readResponse(request) + " [" + wsEndPoint + "]");
			}			
		} catch (MalformedURLException e) {
			System.out.println("     Codigo de erro.......: " + e.toString());
			throw new MalformedURLException(e.getMessage()+":\n" + ExceptionUtils.stringStackTrace(e));
		} catch (SocketTimeoutException e) {
			System.out.println("     Codigo de erro.......: " + e.toString());
			throw new SocketTimeoutException(e.getMessage()+":\n" + ExceptionUtils.stringStackTrace(e));
		} catch (IOException e) {
			System.out.println("     Codigo de erro.......: " + e.toString());
			throw new IOException(e.getMessage()+":\n" + ExceptionUtils.stringStackTrace(e));
		}
	}
}
