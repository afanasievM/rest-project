package ua.com.foxminded.courseproject.exceptions;

import ua.com.foxminded.courseproject.dto.TeacherDto;

public class TeacherConflictException extends RuntimeException {

    static private final String message = "Teacher %s %s %s already exists.";

    public TeacherConflictException() {
        super();
    }

    public TeacherConflictException(String s, Throwable cause) {
        super(s, cause);
    }

    public TeacherConflictException(Throwable cause) {
        super(cause);
    }

    public TeacherConflictException(String s) {
        super(s);
    }

    public TeacherConflictException (TeacherDto teacherDto){
        super(message.formatted(teacherDto.getFirstName(), teacherDto.getLastName(), teacherDto.getBirthDay().toString()));
    }
}
