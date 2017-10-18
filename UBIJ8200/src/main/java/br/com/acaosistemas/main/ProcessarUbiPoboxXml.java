package br.com.acaosistemas.main;

import java.util.ArrayList;
import java.util.List;

import br.com.acaosistemas.db.dao.UBIPoboxXmlDAO;
import br.com.acaosistemas.db.model.UBIPoboxXml;
import br.com.acaosistemas.wsclientes.ClienteWSCorreios;

public class ProcessarUbiPoboxXml {

	public ProcessarUbiPoboxXml() {
	}

	public void lerRegistrosNaoProcessados() {
		ClienteWSCorreios clientWS = new ClienteWSCorreios();
		UBIPoboxXmlDAO ubpxDAO     = new UBIPoboxXmlDAO();
		
		List<UBIPoboxXml> listaUbiPoboxXml = new ArrayList<UBIPoboxXml>();
		listaUbiPoboxXml = ubpxDAO.listPoboxXml();
		
		ubpxDAO.closeConnection();
		
		System.out.println("Processando registros da UBI_POBOX_XML...");
		for (UBIPoboxXml ubpxRow : listaUbiPoboxXml) {
			
			System.out.println("Processando rowId: "+ubpxRow.getRowId());
			clientWS.execWebService(ubpxRow);
		}
		System.out.println("Finalizado processomento da UBI_POBOX_XML.");
	}
}
