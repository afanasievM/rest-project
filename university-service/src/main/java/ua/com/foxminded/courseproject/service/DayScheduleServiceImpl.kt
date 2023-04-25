package ua.com.foxminded.courseproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.foxminded.courseproject.dto.DayScheduleDto;
import ua.com.foxminded.courseproject.dto.LessonDto;
import ua.com.foxminded.courseproject.dto.PersonDto;
import ua.com.foxminded.courseproject.dto.StudentDto;
import ua.com.foxminded.courseproject.dto.TeacherDto;
import ua.com.foxminded.courseproject.entity.DaySchedule;
import ua.com.foxminded.courseproject.mapper.DayScheduleMapper;
import ua.com.foxminded.courseproject.mapper.OptionalMapper;
import ua.com.foxminded.courseproject.repository.DayScheduleRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class DayScheduleServiceImpl implements DayScheduleService {

    private OptionalMapper mapper;
    private DayScheduleRepository repository;
    private TeacherServiceImpl teacherService;
    private StudentServiceImpl studentService;

    @Autowired
    public DayScheduleServiceImpl(DayScheduleMapper mapper, DayScheduleRepository repository,
                                  TeacherServiceImpl teacherService, StudentServiceImpl studentService) {
        this.mapper = mapper;
        this.repository = repository;
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    @Override
    public Map<LocalDate, DayScheduleDto> getStudentDaysSchedule(LocalDate startDay, LocalDate endDay, UUID id) {
        return getDaysSchedule(startDay, endDay, studentService.findById(id));
    }

    @Override
    public Map<LocalDate, DayScheduleDto> getTeacherDaysSchedule(LocalDate startDay, LocalDate endDay, UUID id) {
        return getDaysSchedule(startDay, endDay, teacherService.findById(id));
    }

    @Override
    public DayScheduleDto getStudentOneDaySchedule(LocalDate date, UUID id) {
        return getPersonDaySchedule(date, studentService.findById(id));
    }

    @Override
    public DayScheduleDto getTeacherOneDaySchedule(LocalDate date, UUID id) {
        return getPersonDaySchedule(date, teacherService.findById(id));
    }

    private Map<LocalDate, DayScheduleDto> getDaysSchedule(LocalDate startDay, LocalDate endDay, PersonDto personDto) {
        Map<LocalDate, DayScheduleDto> result = new HashMap<>();
        long dayDifference = ChronoUnit.DAYS.between(startDay, endDay);
        for (int i = 0; i <= dayDifference; i++) {
            DayScheduleDto dayScheduleDto = getPersonDaySchedule(startDay.plusDays(i), personDto);
            result.put(startDay.plusDays(i), dayScheduleDto);
        }
        return result;
    }

    private Optional<DayScheduleDto> getDaySchedule(LocalDate date) {
        Integer weekNumber = date.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        Integer dayNumber = date.getDayOfWeek().getValue();
        Optional<DaySchedule> daySchedule = null;
        if (weekNumber % 2 == 0) {
            daySchedule = repository.findDayScheduleByDayNumberFromOddWeek(dayNumber);
        } else {
            daySchedule = repository.findDayScheduleByDayNumberFromEvenWeek(dayNumber);
        }
        return mapper.toOptionalDto(daySchedule);
    }

    private DayScheduleDto getPersonDaySchedule(LocalDate date, PersonDto personDto) {
        DayScheduleDto dayScheduleDto = getDaySchedule(date).get();
        if (personDto instanceof StudentDto) {
            return getStudentDaySchedule(dayScheduleDto, (StudentDto) personDto);
        } else {
            return getTeacherDaySchedule(dayScheduleDto, (TeacherDto) personDto);
        }
    }

    private DayScheduleDto getStudentDaySchedule(DayScheduleDto dayScheduleDto, StudentDto studentDto) {
        dayScheduleDto.setLessons(filterStudentLessons(dayScheduleDto.getLessons(), studentDto));
        return dayScheduleDto;
    }

    private DayScheduleDto getTeacherDaySchedule(DayScheduleDto dayScheduleDto, TeacherDto teacherDto) {
        dayScheduleDto.setLessons(filterTeacherLessons(dayScheduleDto.getLessons(), teacherDto));
        return dayScheduleDto;
    }

    private List<LessonDto> filterStudentLessons(List<LessonDto> lessons, StudentDto studentDto) {
        return lessons.stream()
                .filter(l -> l.getGroups().contains(studentDto.getGroup()))
                .toList();
    }

    private List<LessonDto> filterTeacherLessons(List<LessonDto> lessons, TeacherDto teacherDto) {
        return lessons.stream()
                .filter(l -> l.getTeacher().equals(teacherDto))
                .toList();
    }


}


