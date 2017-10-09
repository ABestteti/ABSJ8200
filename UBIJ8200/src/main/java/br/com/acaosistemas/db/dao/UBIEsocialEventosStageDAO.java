package br.com.acaosistemas.db.dao;

import java.sql.Connection;

import br.com.acaosistemas.db.connection.ConnectionFactory;
import br.com.acaosistemas.db.model.UBIPoboxXml;

public class UBIEsocialEventosStageDAO {

	private Connection conn;
	private UBIPoboxXml ubpx;
	
	public UBIEsocialEventosStageDAO() {
		conn = new ConnectionFactory().getConnection();
	}

}
