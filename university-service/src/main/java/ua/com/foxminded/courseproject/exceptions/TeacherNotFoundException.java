package ua.com.foxminded.courseproject.exceptions;

import java.util.NoSuchElementException;
import java.util.UUID;

public class TeacherNotFoundException extends NoSuchElementException {

    static private final String message = "Can't find teacher with ID=%s";

    public TeacherNotFoundException() {
        super();
    }

    public TeacherNotFoundException(String s, Throwable cause) {
        super(s, cause);
    }

    public TeacherNotFoundException(Throwable cause) {
        super(cause);
    }

    public TeacherNotFoundException(String s) {
        super(s);
    }

    public TeacherNotFoundException(UUID s) {
        super(message.formatted(s.toString()));
    }
}
