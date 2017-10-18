package br.com.acaosistemas.main;

import java.util.ArrayList;
import java.util.List;

import br.com.acaosistemas.db.dao.UBIPoboxXmlDAO;
import br.com.acaosistemas.db.enumeration.StatusPoboxXMLEnum;
import br.com.acaosistemas.db.model.UBIPoboxXml;
import br.com.acaosistemas.wsclientes.ClienteWSCorreios;

public class ProcessarUbiPoboxXml {

	public ProcessarUbiPoboxXml() {
	}

	public void lerRegistrosNaoProcessados() {
		ClienteWSCorreios clientWS         = new ClienteWSCorreios();
		UBIPoboxXmlDAO ubpxDAO             = new UBIPoboxXmlDAO();
		List<UBIPoboxXml> listaUbiPoboxXml = new ArrayList<UBIPoboxXml>();
		
		listaUbiPoboxXml = ubpxDAO.listPoboxXml();
				
		System.out.println("Processando registros da UBI_POBOX_XML...");
		
		for (UBIPoboxXml ubpxRow : listaUbiPoboxXml) {
			
			System.out.println("Processando rowId: "+ubpxRow.getRowId());
			clientWS.execWebService(ubpxRow);
			
			// Atualiza o status da tabela UBI_POBOX_XML para
			// PROCESSAMENTO_COM_SUCESSO (198)
			ubpxDAO.updateStatus(StatusPoboxXMLEnum.PROCESSAMENTO_COM_SUCESSO, ubpxRow.getRowId());
		}
		
		ubpxDAO.closeConnection();
		System.out.println("Finalizado processomento da UBI_POBOX_XML.");
	}
}
