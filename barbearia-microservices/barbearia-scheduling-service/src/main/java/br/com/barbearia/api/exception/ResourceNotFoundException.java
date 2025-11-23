package br.com.barbearia.api.exception;

/**
 * Exceção customizada para sinalizar que um recurso solicitado (ex: por ID)
 * não foi encontrado no sistema. Esta exceção será mapeada para o
 * status HTTP 404 (Not Found) pelo manipulador de exceções (ExceptionHandler).
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Construtor que recebe uma mensagem detalhando o recurso que não foi encontrado.
     * @param message Mensagem de erro.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}