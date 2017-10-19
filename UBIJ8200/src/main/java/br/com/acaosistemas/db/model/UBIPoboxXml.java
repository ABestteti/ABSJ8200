package br.com.acaosistemas.db.model;

import java.sql.Timestamp;

import br.com.acaosistemas.db.enumeration.StatusPoboxXMLEnum;
import br.com.acaosistemas.db.enumeration.TipoRecursoPoboxXMLEnum;

/**
 * Entidade representando tabela UBI_POBOX_XML
 *
 * @author Anderson Bestteti Santos
 */
public class UBIPoboxXml {
	
    private Timestamp id;
    private String nomeTapi;
    private String sistemaDestinatario;
    private String sistemaRemetente;
    private StatusPoboxXMLEnum status;
    private String tableName;
    private TipoRecursoPoboxXMLEnum tipoRecurso;
    private String wsEndpoint;
    private String xml;
    private String rowId;
    
	public Timestamp getId() {
		return id;
	}
	public void setId(Timestamp id) {
		this.id = id;
	}
	public String getNomeTapi() {
		return nomeTapi;
	}
	public void setNomeTapi(String nomeTapi) {
		this.nomeTapi = nomeTapi;
	}
	public String getSistemaDestinatario() {
		return sistemaDestinatario;
	}
	public void setSistemaDestinatario(String sistemaDestinatario) {
		this.sistemaDestinatario = sistemaDestinatario;
	}
	public String getSistemaRemetente() {
		return sistemaRemetente;
	}
	public void setSistemaRemetente(String sistemaRemetente) {
		this.sistemaRemetente = sistemaRemetente;
	}
	public StatusPoboxXMLEnum getStatus() {
		return status;
	}
	public void setStatus(StatusPoboxXMLEnum status) {
		this.status = status;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public TipoRecursoPoboxXMLEnum getTipoRecurso() {
		return tipoRecurso;
	}
	public void setTipoRecurso(TipoRecursoPoboxXMLEnum tipoRecurso) {
		this.tipoRecurso = tipoRecurso;
	}
	public String getWsEndpoint() {
		return wsEndpoint;
	}
	public void setWsEndpoint(String wsEndpoint) {
		this.wsEndpoint = wsEndpoint;
	}
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}               
}
