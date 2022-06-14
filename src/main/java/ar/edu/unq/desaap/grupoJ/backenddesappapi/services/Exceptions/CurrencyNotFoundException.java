package ar.edu.unq.desaap.grupoJ.backenddesappapi.services.Exceptions;

public class CurrencyNotFoundException extends  Exception{

    public CurrencyNotFoundException(String message) {
        super(String.format("Currency with id {0} could not be found", message));
    }
}