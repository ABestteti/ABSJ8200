package br.com.acaosistemas.db.enumeration;

import br.com.acaosistemas.frw.enumeration.BaseEnum;

/**
 * Enum para o dominio de UBI_POBOX_XML.status
 *
 * @author Cleber da Silveira - versao original
 * @author Anderson Bestteti - alteracoes
 */
public enum StatusPoboxXMLEnum implements BaseEnum<Integer> {

    /**
     * Indica que o XML da P. O. Box nao foi processado.
     */
    NAO_PROCESSADO(0, "Não Processado"),
	
	/**
     * Indica que o XML da P. O. Box esta pronto para transmitir.
     */
    A_TRANSMITIR(101, "A transmitir"),
 
    /**
     * Indica que o XML da P. O. Box esta pronto para transmitir.
     */
    ERRO_PROCESSAMENTO_RECUPERAVEL(196, "Erro no processamento - recuperável"),

    /**
     * Indica que o XML da P. O. Box foi processado com sucesso.
     */
    PROCESSAMENTO_COM_SUCESSO(198, "Processamento com sucesso"),
    
    /**
     * Indica que houve um erro na integracao do XML da P. O. Box.
     */
    ERRO_PROCESSAMENTO_IRRECUPERAVEL(199, "Erro no processamento - irrecuperável");

    private Integer id;
    private String descricao;

    /**
     * Construtor.
     *
     * @param id Identificador da enumeracao.
     * @param descricao Descricao da enumeracao.
     */
    StatusPoboxXMLEnum(final Integer id, final String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    /**
     * Recupera a enumeracao atraves do ID informado.
     *
     * @param id Identificador da enumeracao.
     * @return Enumeracao alcancada.
     */
    public static StatusPoboxXMLEnum getById(final Integer id) {
        StatusPoboxXMLEnum statusPoboxXMLEnum = null;
        for (final StatusPoboxXMLEnum someEnum : values()) {
            if (someEnum.getId().equals(id)) {
                statusPoboxXMLEnum =  someEnum;
            }
        }
        return statusPoboxXMLEnum;
    }
}
