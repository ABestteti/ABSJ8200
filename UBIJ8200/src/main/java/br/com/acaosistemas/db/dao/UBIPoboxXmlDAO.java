package br.com.acaosistemas.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.acaosistemas.db.connection.ConnectionFactory;
import br.com.acaosistemas.db.connection.DBConnectionInfo;
import br.com.acaosistemas.db.enumeration.StatusPoboxXMLEnum;
import br.com.acaosistemas.db.enumeration.TipoRecursoPoboxXMLEnum;
import br.com.acaosistemas.db.model.UBIPoboxXml;

/**
 * DAO para manipulacao da tabela UBI_POBOX_XML
 * <p>
 * <b>Empresa:</b> Acao Sistemas de Informatica Ltda.
 * <p>
 * Alterações:
 * <p>
 * 2018.03.15 - ABS - Adicionado sistema de log com a biblioteca log4j2.
 *                  - Adicionado JavaDoc.
 * 
 * @author Anderson Bestteti Santos
 *
 */
public class UBIPoboxXmlDAO {

	private static final Logger logger = LogManager.getLogger(UBIPoboxXmlDAO.class);
	
	private Connection conn;
	private UBIPoboxXml ubpx;
	public UBIPoboxXmlDAO() {
		conn = new ConnectionFactory().getConnection();
	}
	
	public UBIPoboxXml getUBIPoboxXML(String pRowID) {
		ubpx                   = new UBIPoboxXml();
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(
					  "SELECT "
					+ "   ubpx.rowid,"
					+ "   ubpx.seq_reg,"
					+ "   ubpx.dt_mov,"
					+ "   ubpx.status,"
					+ "   ubpx.tipo_recurso,"
					+ "   ubpx.ws_endpoint,"
					+ "   ubpx.table_name,"
					+ "   ubpx.nome_tapi,"
					+ "   ubpx.sistema_remetente,"
					+ "   ubpx.sistema_destinatario,"
					+ "   ubpx.xml,"
					+ "   ubpx.cnpj "
					+ "FROM"
					+ "   ubi_pobox_xml ubpx "
					+ "WHERE"
					+ "   ubpx.rowid = ?");
		
			stmt.setString(1, pRowID);
			stmt.setFetchSize(DBConnectionInfo.MAX_FETCH_SIZE);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				ubpx.setRowId(rs.getString("rowid"));
				ubpx.setSeqReg(rs.getLong("seq_reg"));
				ubpx.setDtMov(rs.getDate("dt_mov"));
				ubpx.setStatus(StatusPoboxXMLEnum.getById(rs.getInt("status")));
				ubpx.setTipoRecurso(TipoRecursoPoboxXMLEnum.getById(rs.getString("tipo_recurso")));
				ubpx.setWsEndpoint(rs.getString("ws_endpoint"));
				ubpx.setTableName(rs.getString("table_name"));
				ubpx.setNomeTapi(rs.getString("nome_tapi"));
				ubpx.setSistemaRemetente(rs.getString("sistema_remetente"));
				ubpx.setSistemaDestinatario(rs.getString("sistema_destinatario"));
				ubpx.setXml(rs.getString("xml"));
				ubpx.setCnpj(rs.getLong("cnpj"));
			}
			
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.error(e);
			}
		}
		
		return ubpx;
	}
	
	/**
	 * Recupera todos os registros da tabela UBI_POBOX_XML cujo status seja
	 * A_TRANSMITIR (101).
	 * 
	 * @return
	 * Retorna uma lista com os registros recuperados pela consulta SQL.
	 */
	public List<UBIPoboxXml> listPoboxXml() {
		
		PreparedStatement stmt             = null;
		List<UBIPoboxXml> listaUbiPoboxXml = new ArrayList<UBIPoboxXml>();		
		try {
			stmt = conn.prepareStatement(
					  "SELECT"
					+ "   ubpx.rowid,"
					+ "   ubpx.seq_reg,"
					+ "   ubpx.dt_mov,"
					+ "   ubpx.status,"
					+ "   ubpx.tipo_recurso,"
					+ "   ubpx.ws_endpoint,"
					+ "   ubpx.table_name,"
					+ "   ubpx.nome_tapi,"
					+ "   ubpx.sistema_remetente,"
					+ "   ubpx.sistema_destinatario,"
					+ "   ubpx.xml,"
					+ "   ubpx.cnpj "
					+ "FROM"
					+ "   ubi_pobox_xml ubpx "
					+ "WHERE"
					+ "   ubpx.status = ?");
			
			stmt.setInt(1, StatusPoboxXMLEnum.A_TRANSMITIR.getId());
			stmt.setFetchSize(DBConnectionInfo.MAX_FETCH_SIZE);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				UBIPoboxXml ubpx = new UBIPoboxXml();
				
				ubpx.setRowId(rs.getString("rowId"));
				ubpx.setSeqReg(rs.getLong("seq_reg"));
				ubpx.setDtMov(rs.getDate("dt_mov"));
				ubpx.setStatus(StatusPoboxXMLEnum.getById(rs.getInt("status")));
				ubpx.setTipoRecurso(TipoRecursoPoboxXMLEnum.getById(rs.getString("tipo_recurso")));
				ubpx.setWsEndpoint(rs.getString("ws_endpoint"));
				ubpx.setTableName(rs.getString("table_name"));
				ubpx.setNomeTapi(rs.getString("nome_tapi"));
				ubpx.setSistemaRemetente(rs.getString("sistema_remetente"));
				ubpx.setSistemaDestinatario(rs.getString("sistema_destinatario"));
				ubpx.setXml(rs.getString("xml"));
				ubpx.setCnpj(rs.getLong("cnpj"));
				
				listaUbiPoboxXml.add(ubpx);
			}

		} catch (SQLException e) {
			logger.error(e);
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.error(e);
			}
		}
		
		return listaUbiPoboxXml;
	}
	
	public void updateStatus(UBIPoboxXml pUbpxRow) {
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(
					  "UPDATE"
					+ "   ubi_pobox_xml ubpx "
					+ "SET"
					+ "   ubpx.status = ? "
					+ "WHERE "
					+ "   ubpx.rowid = ?");
		
			stmt.setInt(1, pUbpxRow.getStatus().getId());
			stmt.setString(2, pUbpxRow.getRowId());
			
			stmt.execute();
			stmt.close();			
		} catch (SQLException e) {
			logger.error(e);
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.error(e);
			}
		}
	}	
}
