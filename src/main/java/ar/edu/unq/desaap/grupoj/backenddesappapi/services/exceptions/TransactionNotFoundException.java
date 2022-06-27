package ar.edu.unq.desaap.grupoj.backenddesappapi.services.exceptions;

import static java.text.MessageFormat.format;

public class TransactionNotFoundException extends  Exception{

    public TransactionNotFoundException(String message) {
        super(format("Transaction with id {0} could not be found", message));
    }
}