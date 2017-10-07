package br.com.acaosistemas.frw.enumeration;

/**
 * Interface que define se uma enumeração é identificável por ID.
 *
 * @author Estevão Cavinato
 * @param <T> Tipo da chave da enumeração.
 */
public interface IdentifiableEnum<T extends Object> {

    /**
     * ID da enumeração, a ser tipada na definição.
     *
     * @return ID da enumeração.
     */
    T getId();

    /**
     *
     * Método que retorna a descrição da Enumeração.
     *
     * @return a descrição da Enumeração.
     */
    String getDescricao();

}
