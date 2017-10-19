package br.com.acaosistemas.db.model;

import java.sql.Timestamp;

import br.com.acaosistemas.db.enumeration.SimNaoEnum;

/**
 * Entidade representando tabela UBI_ESOCIAL_EVENTOS_STAGE
 *
 * @author Anderson Bestteti Santos
 */
public class UBIEsocialEventosStage {
	
    private Timestamp dtMov;
    
	public Timestamp getDtMov() {
		return dtMov;
	}
	public void setDtMov(Timestamp dtMov) {
		this.dtMov = dtMov;
	}
}
