package ua.com.foxminded.courseproject.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.courseproject.dto.ScheduleDto;
import ua.com.foxminded.courseproject.entity.Schedule;

import java.sql.Timestamp;

@Component
public class ScheduleMapper implements Mapper<ScheduleDto, Schedule> {

    private WeekScheduleMapper weekScheduleMapper;

    @Autowired
    public ScheduleMapper(WeekScheduleMapper weekScheduleMapper) {
        this.weekScheduleMapper = weekScheduleMapper;
    }

    @Override
    public ScheduleDto toDto(Schedule entity) {
        if (entity == null) {
            return null;
        }
        ScheduleDto dto = new ScheduleDto();
        dto.setId(entity.getId());
        dto.setEndDate(entity.getEndDate().toLocalDateTime());
        dto.setStartDate(entity.getStartDate().toLocalDateTime());
        dto.setWeeks(entity.getWeeks().stream().map(weekScheduleMapper::toDto).toList());
        return dto;
    }

    @Override
    public Schedule toEntity(ScheduleDto dto) {
        if (dto == null) {
            return null;
        }
        Schedule entity = new Schedule();
        entity.setId(dto.getId());
        entity.setEndDate(Timestamp.valueOf(dto.getEndDate()));
        entity.setEndDate(Timestamp.valueOf(dto.getStartDate()));
        entity.setWeeks(dto.getWeeks().stream().map(weekScheduleMapper::toEntity).toList());
        return entity;
    }
}
