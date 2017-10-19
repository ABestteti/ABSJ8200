package br.com.acaosistemas.main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import br.com.acaosistemas.db.dao.UBIPoboxXmlDAO;
import br.com.acaosistemas.db.dao.UBIPoboxXmlLogDAO;
import br.com.acaosistemas.db.enumeration.StatusPoboxXMLEnum;
import br.com.acaosistemas.db.model.UBIPoboxXml;
import br.com.acaosistemas.db.model.UBIPoboxXmlLog;
import br.com.acaosistemas.wsclientes.ClienteWSCorreios;

public class ProcessarUbiPoboxXml {

	public ProcessarUbiPoboxXml() {
	}

	public void lerRegistrosNaoProcessados() {
		ClienteWSCorreios clientWS         = new ClienteWSCorreios();
		UBIPoboxXmlDAO    ubpxDAO          = new UBIPoboxXmlDAO();
		List<UBIPoboxXml> listaUbiPoboxXml = new ArrayList<UBIPoboxXml>();
		
		listaUbiPoboxXml = ubpxDAO.listPoboxXml();
				
		System.out.println("   Processando registros da UBI_POBOX_XML...");
		
		for (UBIPoboxXml ubpxRow : listaUbiPoboxXml) {
			
			System.out.println("     Processando rowId: "+ubpxRow.getRowId());
				
			try {
				clientWS.execWebService(ubpxRow);
				
				// Atualiza o status da tabela UBI_POBOX_XML para
				// PROCESSAMENTO_COM_SUCESSO (198)
				ubpxRow.setStatus(StatusPoboxXMLEnum.PROCESSAMENTO_COM_SUCESSO);
				ubpxDAO.updateStatus(ubpxRow);
			} catch (MalformedURLException e) {
				// Caso a chamada do web service do correio retornar a excecao
				// MalformedURLException, faz a atualizacao do status com o
		        // valor apropriado.
				ubpxRow.setStatus(StatusPoboxXMLEnum.ERRO_PROCESSAMENTO_RECUPERAVEL);
				ubpxDAO.updateStatus(ubpxRow);
				
				// Grava na tabela UBI_POBOX_XML_LOG a string com a mensagem de
				// erro completa				
				UBIPoboxXmlLogDAO ubxlDAO = new UBIPoboxXmlLogDAO();
				UBIPoboxXmlLog    ubxl    = new UBIPoboxXmlLog();
				
				ubxl.setUbpxDtMov(ubpxRow.getId());
				ubxl.setDtMov(new Timestamp(System.currentTimeMillis()));
				ubxl.setStatus(StatusPoboxXMLEnum.ERRO_PROCESSAMENTO_RECUPERAVEL);
				ubxl.setMensagem(e.getMessage());
				ubxl.setNumErro(new Long(StatusPoboxXMLEnum.ERRO_PROCESSAMENTO_RECUPERAVEL.getId()));
				
				ubxlDAO.insert(ubxl);
				ubxlDAO.closeConnection();
				
			} catch (IOException e) {
				// Caso a chamada do web service do correio retornar a excecao
				// IOException, faz a atualizacao do status com o
		        // valor apropriado
				ubpxRow.setStatus(StatusPoboxXMLEnum.ERRO_PROCESSAMENTO_IRRECUPERAVEL);
				ubpxDAO.updateStatus(ubpxRow);
				
				// Grava na tabela UBI_POBOX_XML_LOG a string com a mensagem de
				// erro completa				
				UBIPoboxXmlLogDAO ubxlDAO = new UBIPoboxXmlLogDAO();
				UBIPoboxXmlLog    ubxl    = new UBIPoboxXmlLog();
				
				ubxl.setUbpxDtMov(ubpxRow.getId());
				ubxl.setDtMov(new Timestamp(System.currentTimeMillis()));
				ubxl.setStatus(StatusPoboxXMLEnum.ERRO_PROCESSAMENTO_IRRECUPERAVEL);
				ubxl.setMensagem(e.getMessage());
				ubxl.setNumErro(new Long(StatusPoboxXMLEnum.ERRO_PROCESSAMENTO_IRRECUPERAVEL.getId()));
				
				ubxlDAO.insert(ubxl);
				ubxlDAO.closeConnection();
			}
		}
		
		ubpxDAO.closeConnection();
		System.out.println("   Finalizado processomento da UBI_POBOX_XML.");
	}
}
