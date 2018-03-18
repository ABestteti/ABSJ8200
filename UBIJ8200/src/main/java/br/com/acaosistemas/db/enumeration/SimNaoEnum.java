package br.com.acaosistemas.db.enumeration;

/**
 * Enumeracao para representar os valores SIM e NAO.
 * <p>
 * <b>Empresa:</b> Acao Sistemas de Informatica Ltda.
 * <p>
 * Alterações:
 * <p>
 * 2018.03.15 - ABS - Adiconado JavaDOc.
 * 
 * @author Anderson Bestteti Santos
 *
 */
public enum SimNaoEnum {
    /**
     * Enumeração para SIM.
     */
    SIM("S", "Sim"),
    /**
     * Enumeração para Não.
     */
    NAO("N", "Não");
    private String id;
    private String descricao;

    /**
     * Construtor padrão para a Enum.
     * @param id com o código.
     * @param descricao com descritivo da Enum.
     */
    SimNaoEnum(final String id, final String descricao) {
        setId(id);
        setDescricao(descricao);
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    /**
     * Recupera a enumeração com base no identificador.
     *
     * @param id
     *            Valor para o identificador.
     * @return A enumeração alcançada.
     */
    public static SimNaoEnum getById(final String id) {
        SimNaoEnum result = null;
        for (final SimNaoEnum someEnum : values()) {
            if (someEnum.getId().equals(id)) {
                result = someEnum;
            }
        }
        return result;
    }

}
