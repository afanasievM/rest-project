package ua.com.foxminded.courseproject.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springdoc.api.annotations.ParameterObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ua.com.foxminded.courseproject.dto.StudentDto
import ua.com.foxminded.courseproject.exceptions.StudentConflictException
import ua.com.foxminded.courseproject.service.StudentServiceImpl
import ua.com.foxminded.courseproject.utils.PageableStudent
import ua.com.foxminded.courseproject.utils.Role
import java.util.*
import javax.annotation.security.RolesAllowed
import javax.validation.Valid

@RestController
class StudentController @Autowired constructor(studentService: StudentServiceImpl) :
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
    fun getStudents(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "5") size: Int
    ): ResponseEntity<*> {
        return getPersons(PageRequest.of(page,size))
    }

    @Operation(summary = "Create new student.")
    @ApiResponse(responseCode = "201", description = "Student is created.", content = [Content()])
    @PostMapping(value = ["/students"])
    @RolesAllowed(Role.ADMIN)
    fun createStudent(studentDto: @Valid StudentDto): ResponseEntity<*> {
//        if (service.personExists(studentDto).block() == true) {
//            throw StudentConflictException(studentDto)
//        }
        service.save(studentDto)
        return ResponseEntity<Any>(HttpStatus.CREATED)
    }

    @Operation(summary = "Update student.")
    @ApiResponse(responseCode = "205", description = "Student is updated.", content = [Content()])
    @PutMapping(value = ["/students/{id}"])
    @RolesAllowed(Role.ADMIN)
    fun updateStudent(studentDto: @Valid StudentDto): ResponseEntity<*> {
        studentDto.id?.let { getPersonById(it) }
        service.save(studentDto)
        return ResponseEntity<Any>(HttpStatus.RESET_CONTENT)
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
    fun deleteStudent(@PathVariable(name = "id", required = true) id: UUID): ResponseEntity<*> {
        return deletePersonById(id)
    }
}
