package ua.com.foxminded.courseproject.exceptions;

import java.util.NoSuchElementException;
import java.util.UUID;

public class StudentNotFoundException extends NoSuchElementException {

    static private final String message = "Can't find student with ID=%s";

    public StudentNotFoundException() {
        super();
    }

    public StudentNotFoundException(String s, Throwable cause) {
        super(s, cause);
    }

    public StudentNotFoundException(Throwable cause) {
        super(cause);
    }

    public StudentNotFoundException(String s) {
        super(s);
    }

    public StudentNotFoundException(UUID s) {
        super(message.formatted(s.toString()));
    }
}
