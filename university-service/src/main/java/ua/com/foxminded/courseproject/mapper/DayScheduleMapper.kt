package ua.com.foxminded.courseproject.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.courseproject.dto.DayScheduleDto;
import ua.com.foxminded.courseproject.entity.DaySchedule;

@Component
public class DayScheduleMapper implements OptionalMapper<DayScheduleDto, DaySchedule> {

    private LessonMapper lessonMapper;

    @Autowired
    public DayScheduleMapper(LessonMapper lessonMapper) {
        this.lessonMapper = lessonMapper;
    }

    @Override
    public DayScheduleDto toDto(DaySchedule entity) {
        if (entity == null) {
            return null;
        }
        DayScheduleDto dto = new DayScheduleDto();
        dto.setId(entity.getId());
        dto.setDayNumber(entity.getDayNumber());
        dto.setLessons(entity.getLessons().stream().map(lessonMapper::toDto).toList());
        return dto;
    }

    @Override
    public DaySchedule toEntity(DayScheduleDto dto) {
        if (dto == null) {
            return null;
        }
        DaySchedule entity = new DaySchedule();
        entity.setId(dto.getId());
        entity.setDayNumber(dto.getDayNumber());
        entity.setLessons(dto.getLessons().stream().map(lessonMapper::toEntity).toList());
        return entity;
    }
}
