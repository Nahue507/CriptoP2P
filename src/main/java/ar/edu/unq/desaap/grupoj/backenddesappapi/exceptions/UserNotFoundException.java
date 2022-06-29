package ar.edu.unq.desaap.grupoj.backenddesappapi.exceptions;

import java.text.MessageFormat;

public class UserNotFoundException extends  Exception{

    public UserNotFoundException(String message) {
        super(MessageFormat.format("User with id {0} could not be found", message));
    }
}