/**
 * 
 */
package br.com.acaosistemas.wsclientes;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import br.com.acaosistemas.db.dao.UBIPoboxXmlDAO;
import br.com.acaosistemas.db.dao.UBIRuntimesDAO;
import br.com.acaosistemas.db.enumeration.StatusPoboxXMLEnum;
import br.com.acaosistemas.db.model.UBIPoboxXml;
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
		// TODO Auto-generated constructor stub
	}
	
	public void execWebService(String pRowID) {
		String parametros = new String();
		String wsEndPoint;
		
		// Objeto de representacao de um registro da
		// da tabela UBI_POBOX_XML
		UBIPoboxXml ubpx;
		
		// Objects de acesso as tabelas do banco de dados
		UBIPoboxXmlDAO ubpxDAO    = new UBIPoboxXmlDAO();
		UBIRuntimesDAO runtimeDAO = new UBIRuntimesDAO();
		
		// Recupera do banco de dados a informacao do runtime UBIWSINSPOBOXXML
		wsEndPoint = runtimeDAO.getRuntimeValue("UBIWSINSPOBOXXML");
		
		// Fecha a conexao com o banco de daos
		runtimeDAO.closeConnection();
		
		// Recupera do banco de dados as informacoes da tabela
		// UBI_POBOX_XML
		ubpx = ubpxDAO.getUBIPoboxXML(pRowID);
		
		// Antes de invocar o web service o atributo Status precisa ser
		// ajustado para NAO_PROCESSADO;
		ubpx.setStatus(StatusPoboxXMLEnum.NAO_PROCESSADO);
		
		parametros  = "nomeTapi=" + ubpx.getNomeTapi() + "&";
		parametros += "sistemaDestinatario=" + ubpx.getSistemaDestinatario() + "&";
		parametros += "sistemaRemetente=" + ubpx.getSistemaRemetente() + "&";
		parametros += "wsEndpoint=" + ubpx.getWsEndpoint() + "&";
		parametros += "tableName=" + ubpx.getTableName() + "&";
		parametros += "status=" + ubpx.getStatus().getId() + "&";
		parametros += "tipoRecurso=" + ubpx.getTipoRecurso().getId();
		
		try {
			
			URL url = new URL(wsEndPoint+parametros);
			
			HttpURLConnection request = (HttpURLConnection) url.openConnection();			

			// Define que a conexao pode enviar informacoes e obte-las de volta:
			request.setDoOutput(true);
			request.setDoInput(true);
			
			// Define o content-type para trabalhar com o corpo da mensagem HTTP em
			// formato octet-stream, pois o web service da POBOX espera receber esse
			// formato para manter intacto o formato UTF-8 do XML.
			request.setRequestProperty("Content-Type", "application/octet-stream");
			
			request.setRequestProperty("Content-Length", String.valueOf(ubpx.getXml().length()));
			
			request.setRequestProperty("Transfer-Encoding", "chunked");
			
			// Define o metodo da requisicao
			request.setRequestMethod("POST");
			
			// Conecta na URL
			request.connect();
			
			// Escreve o objeto XML usando o OutputStream da requisicao:
			// para enviar para o web service.
            try (OutputStream outputStream = request.getOutputStream()) {
            	outputStream.write(ubpx.getXml().getBytes("UTF-8"));
            }
			
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
	
	public void execWebService(UBIPoboxXml pUbpxRow) {
		String parametros = new String();
		String wsEndPoint;
				
		UBIRuntimesDAO runtimeDAO = new UBIRuntimesDAO();
		
		// Recupera do banco de dados a informacao do runtime UBIWSINSPOBOXXML
		wsEndPoint = runtimeDAO.getRuntimeValue("UBIWSINSPOBOXXML");
		
		// Fecha a conexao com o banco de daos
		runtimeDAO.closeConnection();
		
		// Antes de invocar o web service o atributo Status precisa ser
		// ajustado para NAO_PROCESSADO;
		pUbpxRow.setStatus(StatusPoboxXMLEnum.NAO_PROCESSADO);
		
		parametros  = "nomeTapi=" + pUbpxRow.getNomeTapi() + "&";
		parametros += "sistemaDestinatario=" + pUbpxRow.getSistemaDestinatario() + "&";
		parametros += "sistemaRemetente=" + pUbpxRow.getSistemaRemetente() + "&";
		parametros += "wsEndpoint=" + pUbpxRow.getWsEndpoint() + "&";
		parametros += "tableName=" + pUbpxRow.getTableName() + "&";
		parametros += "status=" + pUbpxRow.getStatus().getId() + "&";
		parametros += "tipoRecurso=" + pUbpxRow.getTipoRecurso().getId();
		
		try {
			
			URL url = new URL(wsEndPoint+parametros);
			
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
			
			// Escreve o objeto XML usando o OutputStream da requisicao:
			// para enviar para o web service.
            try (OutputStream outputStream = request.getOutputStream()) {
            	outputStream.write(pUbpxRow.getXml().getBytes("UTF-8"));
            }
			
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
