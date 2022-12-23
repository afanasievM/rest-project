package ua.com.foxminded.restClient.exceptions;

import java.util.UUID;

public class PersonNotFoundException extends RuntimeException {

    static private final String message = "Person with id=%s not found.";

    public PersonNotFoundException() {
        super();
    }

    public PersonNotFoundException(String s, Throwable cause) {
        super(s, cause);
    }

    public PersonNotFoundException(Throwable cause) {
        super(cause);
    }

    public PersonNotFoundException(String s) {
        super(s);
    }

    public PersonNotFoundException(UUID uuid) {
        super(message.formatted(uuid));
    }

}
