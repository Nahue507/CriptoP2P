package ar.edu.unq.desaap.grupoJ.backenddesappapi.services.Exceptions;

public class CurrencyNotFoundException extends  Exception{

    public CurrencyNotFoundException(String message) {
        super(message);
    }
}