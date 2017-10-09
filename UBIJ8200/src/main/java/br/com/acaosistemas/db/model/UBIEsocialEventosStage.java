package br.com.acaosistemas.db.model;

import java.sql.Timestamp;

import br.com.acaosistemas.db.enumeration.SimNaoEnum;

public class UBIEsocialEventosStage {
    private Timestamp id;
    private Long cnpj;
    private Long grupoEventos;
    private Long ubleUbiLoteNumero;
    private String ubriSistemaRemetente;
    private String xml;
    private SimNaoEnum xmlAssinado;
}
