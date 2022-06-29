package ar.edu.unq.desaap.grupoj.backenddesappapi.exceptions;

import java.text.MessageFormat;

public class IntentionNotFoundException extends  Exception{

    public IntentionNotFoundException(String message) {
        super(MessageFormat.format("Intention with id {0} could not be found", message));
    }
}