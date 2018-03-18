package br.com.acaosistemas.frw.enumeration;

/**
 * Interface que define se uma enumeracao e identificavel por ID.
 *
 * @author Estevao Cavinato
 * @param <T> Tipo da chave da enumeracao.
 */
public interface IdentifiableEnum<T extends Object> {

    /**
     * ID da enumeracao, a ser tipada na definicao.
     *
     * @return ID da enumeracao.
     */
    T getId();

    /**
     *
     * Metodo que retorna a descricao da Enumeracao.
     *
     * @return a descricao da Enumeracao.
     */
    String getDescricao();

}
