package ua.com.foxminded.courseproject.handlers;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.com.foxminded.courseproject.exceptions.StudentConflictException;
import ua.com.foxminded.courseproject.exceptions.StudentNotFoundException;
import ua.com.foxminded.courseproject.exceptions.TeacherConflictException;
import ua.com.foxminded.courseproject.exceptions.TeacherNotFoundException;
import ua.com.foxminded.courseproject.exceptions.UserNotFoundException;

import java.util.Arrays;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(ResponseExceptionHandler.class);

    @ExceptionHandler({StudentNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponse(responseCode = "404", description = "Student isn't found.", content = @Content)
    public ResponseEntity handleStudentNotFoundException(Exception ex) {
        logger.error(stackTraceToString(ex.getStackTrace()));
        return new ResponseEntity("Student isn't found.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({StudentConflictException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    @ApiResponse(responseCode = "409", description = "Student already exists.", content = @Content)
    public ResponseEntity handleStudentConflictException(Exception ex) {
        logger.error(stackTraceToString(ex.getStackTrace()));
        return new ResponseEntity("Student already exists.", HttpStatus.CONFLICT);
    }

    @ExceptionHandler({UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponse(responseCode = "404", description = "User isn't found.", content = @Content)
    public ResponseEntity handleUserNotFoundException(Exception ex) {
        logger.error(stackTraceToString(ex.getStackTrace()));
        return new ResponseEntity("User isn't found.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({TeacherNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponse(responseCode = "404", description = "Teacher isn't found.", content = @Content)
    public ResponseEntity handleTeacherNotFoundException(Exception ex) {
        logger.error(stackTraceToString(ex.getStackTrace()));
        return new ResponseEntity("Teacher isn't found.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({TeacherConflictException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    @ApiResponse(responseCode = "409", description = "Teacher already exists.", content = @Content)
    public ResponseEntity handleTeacherConflictException(Exception ex) {
        logger.error(stackTraceToString(ex.getStackTrace()));
        return new ResponseEntity("Teacher already exists.", HttpStatus.CONFLICT);
    }

    private String stackTraceToString(StackTraceElement[] stackTraceElements) {
        StringBuilder result = new StringBuilder();
        Arrays.stream(stackTraceElements).forEach(stack -> result.append(stack.toString() + "\n"));
        return result.toString();
    }
}
