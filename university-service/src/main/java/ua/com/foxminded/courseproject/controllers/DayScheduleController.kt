package ua.com.foxminded.courseproject.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ua.com.foxminded.courseproject.service.DayScheduleService
import ua.com.foxminded.courseproject.utils.Role
import ua.com.foxminded.courseproject.utils.ScheduleMap
import java.time.LocalDate
import java.util.*
import javax.annotation.security.RolesAllowed

@RestController
class DayScheduleController @Autowired constructor(private val service: DayScheduleService) {
    @Operation(summary = "Get schedule for teacher.")
    @ApiResponse(
        responseCode = "200",
        description = "Schedule is found.",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ScheduleMap::class))]
    )
    @GetMapping(value = ["/teachers/{id}/schedule"])
    @RolesAllowed(
        Role.ADMIN, Role.TEACHER
    )
    fun getTeacherSchedule(
        @PathVariable(name = "id", required = true) id: UUID,
        @RequestParam(name = "startdate", required = true)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) startDate: LocalDate,
        @RequestParam(name = "enddate", required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) endDate: LocalDate?
    ): ResponseEntity<*> {
        return if (endDate != null && endDate.isAfter(startDate)) {
            ResponseEntity(service.getTeacherDaysSchedule(startDate, endDate, id), HttpStatus.OK)
        } else {
            ResponseEntity(service.getTeacherOneDaySchedule(startDate, id), HttpStatus.OK)
        }
    }

    @Operation(summary = "Get schedule for student.")
    @ApiResponse(
        responseCode = "200",
        description = "Schedule is found.",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ScheduleMap::class))]
    )
    @GetMapping(value = ["/students/{id}/schedule"])
    fun getStudentSchedule(
        @PathVariable(name = "id", required = true) id: UUID,
        @RequestParam(name = "startdate", required = true)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) startDate: LocalDate,
        @RequestParam(name = "enddate", required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) endDate: LocalDate?
    ): ResponseEntity<*> {
        return if (endDate != null && endDate.isAfter(startDate)) {
            ResponseEntity(service.getStudentDaysSchedule(startDate, endDate, id), HttpStatus.OK)
        } else {
            ResponseEntity(service.getStudentOneDaySchedule(startDate, id), HttpStatus.OK)
        }

    }
}