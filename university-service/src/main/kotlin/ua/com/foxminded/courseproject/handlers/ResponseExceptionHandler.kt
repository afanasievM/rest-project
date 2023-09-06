package ua.com.foxminded.courseproject.handlers

import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import ua.com.foxminded.courseproject.exceptions.StudentConflictException
import ua.com.foxminded.courseproject.exceptions.StudentNotFoundException
import ua.com.foxminded.courseproject.exceptions.TeacherConflictException
import ua.com.foxminded.courseproject.exceptions.TeacherNotFoundException
import ua.com.foxminded.courseproject.exceptions.UserNotFoundException
import java.util.Arrays

@ControllerAdvice
class ResponseExceptionHandler {
    private val logger = LoggerFactory.getLogger(ResponseExceptionHandler::class.java)

    @ExceptionHandler(StudentNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponse(responseCode = "404", description = "Student isn't found.", content = [Content()])
    fun handleStudentNotFoundException(ex: Exception): ResponseEntity<*> {
        logger.error(stackTraceToString(ex.stackTrace))
        return ResponseEntity("Student isn't found.", HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(StudentConflictException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ApiResponse(responseCode = "409", description = "Student already exists.", content = [Content()])
    fun handleStudentConflictException(ex: Exception): ResponseEntity<*> {
        logger.error(stackTraceToString(ex.stackTrace))
        return ResponseEntity("Student already exists.", HttpStatus.CONFLICT)
    }

    @ExceptionHandler(UserNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponse(responseCode = "404", description = "User isn't found.", content = [Content()])
    fun handleUserNotFoundException(ex: Exception): ResponseEntity<*> {
        logger.error(stackTraceToString(ex.stackTrace))
        return ResponseEntity("User isn't found.", HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(TeacherNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponse(responseCode = "404", description = "Teacher isn't found.", content = [Content()])
    fun handleTeacherNotFoundException(ex: Exception): ResponseEntity<*> {
        logger.error(stackTraceToString(ex.stackTrace))
        return ResponseEntity("Teacher isn't found.", HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(TeacherConflictException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ApiResponse(responseCode = "409", description = "Teacher already exists.", content = [Content()])
    fun handleTeacherConflictException(ex: Exception): ResponseEntity<*> {
        logger.error(stackTraceToString(ex.stackTrace))
        return ResponseEntity("Teacher already exists.", HttpStatus.CONFLICT)
    }

    private fun stackTraceToString(stackTraceElements: Array<StackTraceElement>): String {
        val result = StringBuilder()
        Arrays.stream(stackTraceElements).forEach { stack: StackTraceElement -> result.append(stack.toString() + "\n") }
        return result.toString()
    }
}
