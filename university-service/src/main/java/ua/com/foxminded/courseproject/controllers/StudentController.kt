package ua.com.foxminded.courseproject.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.foxminded.courseproject.dto.StudentDto;
import ua.com.foxminded.courseproject.utils.Role;
import ua.com.foxminded.courseproject.exceptions.StudentConflictException;
import ua.com.foxminded.courseproject.service.StudentServiceImpl;
import ua.com.foxminded.courseproject.utils.PageableStudent;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.UUID;

@RestController
public class StudentController extends PersonController<StudentDto, StudentServiceImpl> {

    @Autowired
    public StudentController(StudentServiceImpl studentService) {
        this.service = studentService;
    }

    @Operation(summary = "Get all students.")
    @ApiResponse(responseCode = "200", description = "Students are found.",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PageableStudent.class))})
    @GetMapping(value = "/students")
    @RolesAllowed(value = {Role.ADMIN, Role.TEACHER})
    public ResponseEntity getStudents(@ParameterObject @PageableDefault(page = 0, size = 5) Pageable pageable) {
        return getPersons(pageable);
    }

    @Operation(summary = "Create new student.")
    @ApiResponse(responseCode = "201", description = "Student is created.", content = @Content)
    @PostMapping(value = "/students")
    @RolesAllowed({Role.ADMIN})
    public ResponseEntity createStudent(@Valid StudentDto studentDto) {
        if (service.personExists(studentDto)) {
            throw new StudentConflictException(studentDto);
        }
        service.save(studentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Update student.")
    @ApiResponse(responseCode = "205", description = "Student is updated.", content = @Content)
    @PutMapping(value = "/students/{id}")
    @RolesAllowed({Role.ADMIN})
    public ResponseEntity updateStudent(@Valid StudentDto studentDto) {
        getPersonById(studentDto.getId());
        service.save(studentDto);
        return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
    }

    @Operation(summary = "Get student by id.")
    @ApiResponse(responseCode = "200", description = "Student is found.", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = StudentDto.class))})
    @GetMapping(value = "/students/{id}")
    @RolesAllowed({Role.ADMIN})
    public ResponseEntity getStudent(@PathVariable(name = "id", required = true) UUID id) {
        return getPersonById(id);
    }

    @Operation(summary = "Delete student by id.")
    @ApiResponse(responseCode = "204", description = "Student is deleted.", content = @Content)
    @DeleteMapping(value = "/students/{id}")
    @RolesAllowed({Role.ADMIN})
    public ResponseEntity deleteStudent(@PathVariable(name = "id", required = true) UUID id) {
        return deletePersonById(id);
    }

}
