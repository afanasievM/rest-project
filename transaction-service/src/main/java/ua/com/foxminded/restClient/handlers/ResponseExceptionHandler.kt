//package ua.com.foxminded.restClient.handlers
//
//import io.swagger.v3.oas.annotations.media.Content
//import io.swagger.v3.oas.annotations.responses.ApiResponse
//import org.slf4j.LoggerFactory
//import org.springframework.http.HttpStatus
//import org.springframework.http.ResponseEntity
//import org.springframework.web.bind.annotation.ControllerAdvice
//import org.springframework.web.bind.annotation.ExceptionHandler
//import org.springframework.web.bind.annotation.ResponseStatus
//import org.springframework.web.client.HttpClientErrorException
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
//import ua.com.foxminded.restClient.exceptions.CurrencyNotFoundException
//import ua.com.foxminded.restClient.exceptions.PersonNotFoundException
//import java.util.*
//
//@ControllerAdvice
//class ResponseExceptionHandler : ResponseEntityExceptionHandler() {
//    private val logger = LoggerFactory.getLogger(ResponseExceptionHandler::class.java)
//    @ExceptionHandler(PersonNotFoundException::class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ApiResponse(responseCode = "404", description = "Person isn't found.", content = [Content()])
//    fun handlePersonNotFoundException(ex: Exception): ResponseEntity<*> {
//        logger.error(stackTraceToString(ex.stackTrace))
//        return ResponseEntity<Any?>("Person isn't found.", HttpStatus.NOT_FOUND)
//    }
//
//    @ExceptionHandler(HttpClientErrorException::class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ApiResponse(responseCode = "429", description = "Too many requests.", content = [Content()])
//    fun handleTooManyException(ex: Exception): ResponseEntity<*> {
//        logger.error(stackTraceToString(ex.stackTrace))
//        return ResponseEntity<Any?>("Too many requests.", HttpStatus.TOO_MANY_REQUESTS)
//    }
//
//    @ExceptionHandler(CurrencyNotFoundException::class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ApiResponse(responseCode = "404", description = "Currency rate not found.", content = [Content()])
//    fun handleCurrencyNotFoundException(ex: Exception): ResponseEntity<*> {
//        logger.error(stackTraceToString(ex.stackTrace))
//        return ResponseEntity<Any?>("Currency rate not found.", HttpStatus.NOT_FOUND)
//    }
//
//    private fun stackTraceToString(stackTraceElements: Array<StackTraceElement>): String {
//        val result = StringBuilder()
//        Arrays.stream(stackTraceElements).forEach { stack: StackTraceElement -> result.append(stack.toString() + "\n") }
//        return result.toString()
//    }
//}
