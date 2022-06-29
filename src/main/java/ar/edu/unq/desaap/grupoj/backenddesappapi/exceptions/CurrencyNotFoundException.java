package ar.edu.unq.desaap.grupoj.backenddesappapi.exceptions;

import static java.text.MessageFormat.format;

public class CurrencyNotFoundException extends  Exception{

    public CurrencyNotFoundException(String message) {
        super(format("Currency with id {0} could not be found", message));
    }
}