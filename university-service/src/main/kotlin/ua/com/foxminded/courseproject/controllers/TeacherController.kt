package ua.com.foxminded.courseproject.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.dto.TeacherDto
import ua.com.foxminded.courseproject.service.TeacherServiceImpl
import ua.com.foxminded.courseproject.utils.PageableTeacher
import ua.com.foxminded.courseproject.utils.Role
import java.util.*
import javax.annotation.security.RolesAllowed
import javax.validation.Valid

@RestController
class TeacherController @Autowired constructor(teacherService: TeacherServiceImpl) :
    PersonController<TeacherDto, TeacherServiceImpl>() {
    init {
        service = teacherService
    }

    @Operation(summary = "Get all teachers.")
    @ApiResponse(
        responseCode = "200",
        description = "Teachers are found.",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = PageableTeacher::class))]
    )
    @GetMapping(value = ["/teachers"])
    @RolesAllowed(Role.ADMIN)
    fun getTeachers(): ResponseEntity<*> {
        return getPersons()
    }

    @Operation(summary = "Create new teacher.")
    @ApiResponse(responseCode = "201", description = "Teacher is created.", content = [Content()])
    @PostMapping(value = ["/teachers"])
    @RolesAllowed(Role.ADMIN)
    fun createTeacher(teacherDto: @Valid TeacherDto): Mono<ResponseEntity<*>> {
        return service.save(teacherDto).map { ResponseEntity<Any>(HttpStatus.CREATED) }
    }

    @Operation(summary = "Update teacher.")
    @ApiResponse(responseCode = "205", description = "Teacher is updated.", content = [Content()])
    @PutMapping(value = ["/teachers/{id}"])
    @RolesAllowed(Role.ADMIN)
    fun updateTeacher(teacherDto: @Valid TeacherDto): Mono<ResponseEntity<*>> {
        teacherDto.id?.let { getPersonById(it) }
        return service.save(teacherDto).map { ResponseEntity<Any>(HttpStatus.RESET_CONTENT) }
    }

    @Operation(summary = "Get teacher by id.")
    @ApiResponse(
        responseCode = "200",
        description = "Teacher is found.",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = TeacherDto::class))]
    )
    @GetMapping(value = ["/teachers/{id}"])
    @RolesAllowed(Role.ADMIN)
    fun getTeacher(@PathVariable(name = "id", required = true) id: UUID): ResponseEntity<*> {
        return getPersonById(id)
    }

    @Operation(summary = "Delete teacher by id.")
    @ApiResponse(responseCode = "204", description = "Teacher is deleted.", content = [Content()])
    @DeleteMapping(value = ["/teachers/{id}"])
    @RolesAllowed(Role.ADMIN)
    fun deleteTeacher(@PathVariable(name = "id", required = true) id: UUID): Mono<ResponseEntity<*>> {
        return deletePersonById(id)
    }
}
