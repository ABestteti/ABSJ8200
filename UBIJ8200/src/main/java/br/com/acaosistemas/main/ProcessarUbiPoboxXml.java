package br.com.acaosistemas.main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.acaosistemas.db.dao.UBIPoboxXmlDAO;
import br.com.acaosistemas.db.dao.UBIPoboxXmlLogDAO;
import br.com.acaosistemas.db.enumeration.StatusPoboxXMLEnum;
import br.com.acaosistemas.db.model.UBIPoboxXml;
import br.com.acaosistemas.db.model.UBIPoboxXmlLog;
import br.com.acaosistemas.frw.util.ExceptionUtils;
import br.com.acaosistemas.wsclientes.ClienteWSCorreios;

public class ProcessarUbiPoboxXml {

	private static final Logger logger = LogManager.getLogger(ProcessarUbiPoboxXml.class);
	
	public ProcessarUbiPoboxXml() {
	}

	public void lerRegistrosNaoProcessados() {
		ClienteWSCorreios clientWS         = new ClienteWSCorreios();
		UBIPoboxXmlDAO    ubpxDAO          = new UBIPoboxXmlDAO();
		List<UBIPoboxXml> listaUbiPoboxXml = new ArrayList<UBIPoboxXml>();
		UBIPoboxXmlLog    ubxl             = new UBIPoboxXmlLog();
						
		logger.info("   Processando registros da UBI_POBOX_XML...");
		
		// Obtem a lista de registros da tabeke UBI_POBOX_XML a serem
		// processados.
		listaUbiPoboxXml = ubpxDAO.listPoboxXml();
		for (UBIPoboxXml ubpxRow : listaUbiPoboxXml) {
			
			logger.info("     Processando rowId....: " + ubpxRow.getRowId());
			logger.info("     Sequencia do registro: " + ubpxRow.getSeqReg());
				
			try {
				clientWS.execWebService(ubpxRow);
				
				// Atualiza o status da tabela UBI_POBOX_XML para
				// PROCESSAMENTO_COM_SUCESSO (198)
				ubpxRow.setStatus(StatusPoboxXMLEnum.PROCESSAMENTO_COM_SUCESSO);
				ubpxDAO.updateStatus(ubpxRow);
				
				// Prepara o insert no log com resultado da chamada do web service
				ubxl.setUbpxSeqReg(ubpxRow.getSeqReg());
				ubxl.setMensagem(StatusPoboxXMLEnum.PROCESSAMENTO_COM_SUCESSO.getId() + "-" +
						         StatusPoboxXMLEnum.PROCESSAMENTO_COM_SUCESSO.getDescricao());
				ubxl.setStatus(StatusPoboxXMLEnum.PROCESSAMENTO_COM_SUCESSO);
				ubxl.setNumErro(0L);
				
				UBIPoboxXmlLogDAO ubxlDAO = new UBIPoboxXmlLogDAO();
				ubxlDAO.insert(ubxl);
				ubxlDAO.closeConnection();
			} catch (MalformedURLException e) {
				// Caso a chamada do web service do correio retornar a excecao
				// MalformedURLException, faz a atualizacao do status com o
		        // valor apropriado.
				ubpxRow.setStatus(StatusPoboxXMLEnum.ERRO_PROCESSAMENTO_IRRECUPERAVEL);
				gravaExcecaoLog(ubpxRow, e);
			} catch (SocketTimeoutException e) {
				// Caso a chamada do web service do correio retornar a excecao
				// IOException, faz a atualizacao do status com o
		        // valor apropriado
				ubpxRow.setStatus(StatusPoboxXMLEnum.ERRO_PROCESSAMENTO_IRRECUPERAVEL);
				gravaExcecaoLog(ubpxRow, e);
			} catch (IOException e) {
				// Caso a chamada do web service do correio retornar a excecao
				// IOException, faz a atualizacao do status com o
		        // valor apropriado
				ubpxRow.setStatus(StatusPoboxXMLEnum.ERRO_PROCESSAMENTO_IRRECUPERAVEL);
				gravaExcecaoLog(ubpxRow, e);
			}
		}
		
		ubpxDAO.closeConnection();
		logger.info("   Finalizado processomento da UBI_POBOX_XML.");
	}
	
	private void gravaExcecaoLog(UBIPoboxXml pUbpxRow, Exception pException) {
		UBIPoboxXmlDAO ubpxDAO = new UBIPoboxXmlDAO();
		
		ubpxDAO.updateStatus(pUbpxRow);
		
		// Grava na tabela UBI_POBOX_XML_LOG a string com a mensagem de
		// erro completa.				
		UBIPoboxXmlLogDAO ubxlDAO = new UBIPoboxXmlLogDAO();
		UBIPoboxXmlLog    ubxl    = new UBIPoboxXmlLog();
		
		ubxl.setUbpxSeqReg(pUbpxRow.getSeqReg());
		ubxl.setStatus(pUbpxRow.getStatus());
		ubxl.setMensagem(pUbpxRow.getStatus().getId() + "-"   +
				         pUbpxRow.getStatus().getDescricao()  +
				         "\n"                                 +
				         ExceptionUtils.stringStackTrace(pException));
		ubxl.setNumErro(new Long(pUbpxRow.getStatus().getId()));
		
		ubxlDAO.insert(ubxl);
		ubxlDAO.closeConnection();		
	}
}
