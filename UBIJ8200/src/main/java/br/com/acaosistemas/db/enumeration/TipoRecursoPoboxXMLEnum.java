package br.com.acaosistemas.db.enumeration;

import br.com.acaosistemas.frw.enumeration.BaseEnum;

/**
 * Enum para o dominio UBI_POBOX_XML.TIPO_RECURSO
 * <p>
 * <b>Empresa:</b> Acao Sistemas de Informatica Ltda.
 * <p>
 * Alterações:
 * <p>
 * 2018.03.15 - ABS - Adiconado JavaDOc.
 * 
 * @author Cleber da Silveira
 * @author Anderson Bestteti
 */
public enum TipoRecursoPoboxXMLEnum implements BaseEnum<String> {
    /**
     * Indica que a integracao sera feita por web-services.
     */
    WS("WS", "Web Service"),
    /**
     * Indica que a integracao sera feita por Table API.
     */
    TAPI("TAPI", "Table API");

    private String id;
    private String descricao;

    /**
     * Construtor.
     *
     * @param id Identificador da enumeracao.
     * @param descricao Descricao da enumeracao.
     */
    TipoRecursoPoboxXMLEnum(final String id, final String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    @Override
    public String getId() {
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
    public static TipoRecursoPoboxXMLEnum getById(final String id) {
        TipoRecursoPoboxXMLEnum result = null;
        for (final TipoRecursoPoboxXMLEnum someEnum : values()) {
            if (someEnum.getId().equals(id)) {
                result =  someEnum;
            }
        }
        return result;
    }

}
