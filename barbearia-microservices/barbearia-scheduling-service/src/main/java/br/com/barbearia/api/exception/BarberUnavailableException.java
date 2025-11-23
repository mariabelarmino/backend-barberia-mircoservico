package br.com.barbearia.api.exception;

/**
 * Exceção lançada quando um barbeiro está indisponível para um agendamento no horário solicitado.
 */
public class BarberUnavailableException extends RuntimeException {

    public BarberUnavailableException(String message) {
        super(message);
    }

    public BarberUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}