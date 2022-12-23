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
import ua.com.foxminded.courseproject.dto.TeacherDto;
import ua.com.foxminded.courseproject.utils.Role;
import ua.com.foxminded.courseproject.service.TeacherServiceImpl;
import ua.com.foxminded.courseproject.utils.PageableTeacher;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.UUID;

@RestController
public class TeacherController extends PersonController<TeacherDto, TeacherServiceImpl> {


    @Autowired
    public TeacherController(TeacherServiceImpl teacherService) {
        this.service = teacherService;
    }

    @Operation(summary = "Get all teachers.")
    @ApiResponse(responseCode = "200", description = "Teachers are found.",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PageableTeacher.class))})
    @GetMapping(value = "/teachers")
    @RolesAllowed({Role.ADMIN})
    public ResponseEntity getTeachers(@ParameterObject @PageableDefault(page = 0, size = 5) Pageable pageable) {
        return getPersons(pageable);
    }

    @Operation(summary = "Create new teacher.")
    @ApiResponse(responseCode = "201", description = "Teacher is created.", content = @Content)
    @PostMapping(value = "/teachers")
    @RolesAllowed({Role.ADMIN})
    public ResponseEntity createTeacher(@Valid TeacherDto teacherDto) {
        service.save(teacherDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Update teacher.")
    @ApiResponse(responseCode = "205", description = "Teacher is updated.", content = @Content)
    @PutMapping(value = "/teachers/{id}")
    @RolesAllowed({Role.ADMIN})
    public ResponseEntity updateTeacher(@Valid TeacherDto teacherDto) {
        getPersonById(teacherDto.getId());
        service.save(teacherDto);
        return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
    }

    @Operation(summary = "Get teacher by id.")
    @ApiResponse(responseCode = "200", description = "Teacher is found.", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = TeacherDto.class))})
    @GetMapping(value = "/teachers/{id}")
    @RolesAllowed({Role.ADMIN})
    public ResponseEntity getTeacher(@PathVariable(name = "id", required = true) UUID id) {
        return getPersonById(id);
    }

    @Operation(summary = "Delete teacher by id.")
    @ApiResponse(responseCode = "204", description = "Teacher is deleted.", content = @Content)
    @DeleteMapping(value = "/teachers/{id}")
    @RolesAllowed({Role.ADMIN})
    public ResponseEntity deleteTeacher(@PathVariable(name = "id", required = true) UUID id) {
        return deletePersonById(id);
    }

}
