package ua.com.foxminded.courseproject.exceptions;

import java.util.NoSuchElementException;

public class UserNotFoundException extends NoSuchElementException {

    static private final String message = "Can't find user with username - '%s'";

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String s, Throwable cause) {
        super(s, cause);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

    public UserNotFoundException(String s) {
        super(message.formatted(s.toString()));
    }

}
