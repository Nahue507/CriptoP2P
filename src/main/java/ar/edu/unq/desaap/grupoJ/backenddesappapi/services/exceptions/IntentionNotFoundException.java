package ar.edu.unq.desaap.grupoJ.backenddesappapi.services.exceptions;

import java.text.MessageFormat;

public class IntentionNotFoundException extends  Exception{

    public IntentionNotFoundException(String message) {
        super(MessageFormat.format("Intention with id {0} could not be found", message));
    }
}