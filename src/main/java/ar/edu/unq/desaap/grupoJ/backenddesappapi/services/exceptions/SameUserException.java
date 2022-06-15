package ar.edu.unq.desaap.grupoJ.backenddesappapi.services.exceptions;

public class SameUserException extends Exception {

    public SameUserException(String message) {
        super(message);
    }
}
