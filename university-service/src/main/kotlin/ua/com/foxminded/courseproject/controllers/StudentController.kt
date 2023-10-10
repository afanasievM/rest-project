package ua.com.foxminded.courseproject.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import java.util.UUID
import javax.annotation.security.RolesAllowed
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.dto.StudentDto
import ua.com.foxminded.courseproject.service.StudentServiceImpl
import ua.com.foxminded.courseproject.utils.PageableStudent
import ua.com.foxminded.courseproject.utils.Role

@RestController
class StudentController(studentService: StudentServiceImpl) :
    PersonController<StudentDto, StudentServiceImpl>() {
    init {
        service = studentService
    }

    @Operation(summary = "Get all students.")
    @ApiResponse(
        responseCode = "200",
        description = "Students are found.",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = PageableStudent::class))]
    )
    @GetMapping(value = ["/students"])
    @RolesAllowed(value = [Role.ADMIN, Role.TEACHER])
    fun getStudents(): ResponseEntity<*> {
        return getPersons()
    }

    @Operation(summary = "Create new student.")
    @ApiResponse(responseCode = "201", description = "Student is created.", content = [Content()])
    @PostMapping(value = ["/students"])
    @RolesAllowed(Role.ADMIN)
    fun createStudent(studentDto: @Valid StudentDto): Mono<ResponseEntity<*>> {
        return service.save(studentDto).map { ResponseEntity<Any>(HttpStatus.CREATED) }

    }

    @Operation(summary = "Update student.")
    @ApiResponse(responseCode = "205", description = "Student is updated.", content = [Content()])
    @PutMapping(value = ["/students/{id}"])
    @RolesAllowed(Role.ADMIN)
    fun updateStudent(studentDto: @Valid StudentDto): Mono<ResponseEntity<*>> {
        studentDto.id?.let { getPersonById(it) }
        return service.save(studentDto).map { ResponseEntity<Any>(HttpStatus.RESET_CONTENT) }
    }

    @Operation(summary = "Get student by id.")
    @ApiResponse(
        responseCode = "200",
        description = "Student is found.",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = StudentDto::class))]
    )
    @GetMapping(value = ["/students/{id}"])
    @RolesAllowed(Role.ADMIN)
    fun getStudent(@PathVariable(name = "id", required = true) id: UUID): ResponseEntity<*> {
        return getPersonById(id)
    }

    @Operation(summary = "Delete student by id.")
    @ApiResponse(responseCode = "204", description = "Student is deleted.", content = [Content()])
    @DeleteMapping(value = ["/students/{id}"])
    @RolesAllowed(Role.ADMIN)
    fun deleteStudent(@PathVariable(name = "id", required = true) id: UUID): Mono<ResponseEntity<*>> {
        return deletePersonById(id)
    }
}
