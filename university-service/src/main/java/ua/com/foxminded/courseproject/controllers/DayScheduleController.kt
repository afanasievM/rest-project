package ua.com.foxminded.courseproject.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.com.foxminded.courseproject.utils.Role;
import ua.com.foxminded.courseproject.service.DayScheduleService;
import ua.com.foxminded.courseproject.utils.ScheduleMap;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDate;
import java.util.UUID;

@RestController
public class DayScheduleController {

    private DayScheduleService service;

    @Autowired
    public DayScheduleController(DayScheduleService service) {
        this.service = service;
    }

    @Operation(summary = "Get schedule for teacher.")
    @ApiResponse(responseCode = "200", description = "Schedule is found.", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleMap.class))})
    @GetMapping(value = "/teachers/{id}/schedule")
    @RolesAllowed({Role.ADMIN, Role.TEACHER})
    public ResponseEntity getTeacherSchedule(
            @PathVariable(name = "id", required = true) UUID id,
            @RequestParam(name = "startdate", required = true)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "enddate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        ResponseEntity result = null;
        if (endDate != null && endDate.isAfter(startDate)) {
            result = new ResponseEntity(service.getTeacherDaysSchedule(startDate, endDate, id), HttpStatus.OK);
        } else {
            result = new ResponseEntity(service.getTeacherOneDaySchedule(startDate, id), HttpStatus.OK);
        }
        return result;
    }

    @Operation(summary = "Get schedule for student.")
    @ApiResponse(responseCode = "200", description = "Schedule is found.", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleMap.class))})
    @GetMapping(value = "/students/{id}/schedule")
    public ResponseEntity getStudentSchedule(
            @PathVariable(name = "id", required = true) UUID id,
            @RequestParam(name = "startdate", required = true)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "enddate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        ResponseEntity result = null;
        if (endDate != null && endDate.isAfter(startDate)) {
            result = new ResponseEntity(service.getStudentDaysSchedule(startDate, endDate, id), HttpStatus.OK);
        } else {
            result = new ResponseEntity(service.getStudentOneDaySchedule(startDate, id), HttpStatus.OK);
        }
        return result;
    }
}
