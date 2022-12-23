package ua.com.foxminded.courseproject.exceptions;

import ua.com.foxminded.courseproject.dto.StudentDto;

public class StudentConflictException extends RuntimeException {

    static private final String message = "Student %s %s %s already exists.";

    public StudentConflictException() {
        super();
    }

    public StudentConflictException(String s, Throwable cause) {
        super(s, cause);
    }

    public StudentConflictException(Throwable cause) {
        super(cause);
    }

    public StudentConflictException(String s) {
        super(s);
    }

    public StudentConflictException (StudentDto studentDto){
        super(message.formatted(studentDto.getFirstName(), studentDto.getLastName(), studentDto.getBirthDay().toString()));
    }

}
