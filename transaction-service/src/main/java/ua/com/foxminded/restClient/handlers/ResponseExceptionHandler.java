package ua.com.foxminded.restClient.handlers;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.com.foxminded.restClient.exceptions.CurrencyNotFoundException;
import ua.com.foxminded.restClient.exceptions.PersonNotFoundException;


import java.util.Arrays;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(ResponseExceptionHandler.class);

    @ExceptionHandler({PersonNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponse(responseCode = "404", description = "Person isn't found.", content = @Content)
    public ResponseEntity handlePersonNotFoundException(Exception ex) {
        logger.error(stackTraceToString(ex.getStackTrace()));
        return new ResponseEntity("Person isn't found.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({HttpClientErrorException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponse(responseCode = "429", description = "Too many requests.", content = @Content)
    public ResponseEntity handleTooManyException(Exception ex) {
        logger.error(stackTraceToString(ex.getStackTrace()));
        return new ResponseEntity("Too many requests.", HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler({CurrencyNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponse(responseCode = "404", description = "Currency rate not found.", content = @Content)
    public ResponseEntity handleCurrencyNotFoundException(Exception ex) {
        logger.error(stackTraceToString(ex.getStackTrace()));
        return new ResponseEntity("Currency rate not found.", HttpStatus.NOT_FOUND);
    }



    private String stackTraceToString(StackTraceElement[] stackTraceElements) {
        StringBuilder result = new StringBuilder();
        Arrays.stream(stackTraceElements).forEach(stack -> result.append(stack.toString() + "\n"));
        return result.toString();
    }
}
