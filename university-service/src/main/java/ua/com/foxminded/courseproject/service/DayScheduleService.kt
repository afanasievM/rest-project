package ua.com.foxminded.courseproject.service;

import ua.com.foxminded.courseproject.dto.DayScheduleDto;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public interface DayScheduleService {

    Map<LocalDate, DayScheduleDto> getStudentDaysSchedule(LocalDate startDay, LocalDate endDay, UUID id);

    Map<LocalDate, DayScheduleDto> getTeacherDaysSchedule(LocalDate startDay, LocalDate endDay, UUID id);

    DayScheduleDto getStudentOneDaySchedule(LocalDate date, UUID id);

    DayScheduleDto getTeacherOneDaySchedule(LocalDate date, UUID id);

}
