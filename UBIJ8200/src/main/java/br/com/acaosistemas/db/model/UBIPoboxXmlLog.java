package br.com.acaosistemas.db.model;

import java.sql.Timestamp;

import br.com.acaosistemas.db.enumeration.StatusPoboxXMLEnum;

/**
 * Entidade representando tabela UBI_POBOX_XML_LOG
 *
 * @author Anderson Bestteti Santos
 */
public class UBIPoboxXmlLog {

    private Timestamp ubpxDtMov;
    private Timestamp dtMov;
    private String mensagem;
    private Long numErro;
    private StatusPoboxXMLEnum status;
    
	public Timestamp getUbpxDtMov() {
		return ubpxDtMov;
	}
	public void setUbpxDtMov(Timestamp ubpxDtMov) {
		this.ubpxDtMov = ubpxDtMov;
	}
	public Timestamp getDtMov() {
		return dtMov;
	}
	public void setDtMov(Timestamp dtMov) {
		this.dtMov = dtMov;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public Long getNumErro() {
		return numErro;
	}
	public void setNumErro(Long numErro) {
		this.numErro = numErro;
	}
	public StatusPoboxXMLEnum getStatus() {
		return status;
	}
	public void setStatus(StatusPoboxXMLEnum status) {
		this.status = status;
	}
    
}
