package br.com.acaosistemas.db.model;

import java.sql.Date;

import br.com.acaosistemas.db.enumeration.StatusPoboxXMLEnum;

/**
 * Entidade representando tabela UBI_POBOX_XML_LOG
 * <p>
 * <b>Empresa:</b> Acao Sistemas de Informatica Ltda.
 * <p>
 * Alterações:
 * <p>
 * 2018.03.15 - ABS - Implementado metodo toString().
 *                    
 *
 * @author Anderson Bestteti Santos
 * 
 */
public class UBIPoboxXmlLog {

	private Long ubpxSeqReg;
    private Long seqReg;
    private Date dtMov;
    private String mensagem;
    private Long numErro;
    private StatusPoboxXMLEnum status;
    
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
	public Long getUbpxSeqReg() {
		return ubpxSeqReg;
	}
	public void setUbpxSeqReg(Long ubpxSeqReg) {
		this.ubpxSeqReg = ubpxSeqReg;
	}
	public Long getSeqReg() {
		return seqReg;
	}
	public void setSeqReg(Long seqReg) {
		this.seqReg = seqReg;
	}
	public Date getDtMov() {
		return dtMov;
	}
	public void setDtMov(Date dtMov) {
		this.dtMov = dtMov;
	}
	@Override
	public String toString() {
		return "UBIPoboxXmlLog [ubpxSeqReg=" + ubpxSeqReg + ", seqReg=" + seqReg + ", dtMov=" + dtMov + ", mensagem="
				+ mensagem + ", numErro=" + numErro + ", status=" + status + "]";
	}
    
}
