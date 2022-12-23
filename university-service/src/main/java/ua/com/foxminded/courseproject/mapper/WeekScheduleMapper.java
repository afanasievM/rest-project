package ua.com.foxminded.courseproject.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.courseproject.dto.WeekScheduleDto;
import ua.com.foxminded.courseproject.entity.WeekSchedule;

@Component
public class WeekScheduleMapper implements Mapper<WeekScheduleDto, WeekSchedule> {

    private DayScheduleMapper dayScheduleMapper;

    @Autowired
    public WeekScheduleMapper(DayScheduleMapper dayScheduleMapper) {
        this.dayScheduleMapper = dayScheduleMapper;
    }

    @Override
    public WeekScheduleDto toDto(WeekSchedule entity) {
        if (entity == null) {
            return null;
        }
        WeekScheduleDto dto = new WeekScheduleDto();
        dto.setId(entity.getId());
        dto.setDaysSchedule(entity.getDaysSchedule().stream().map(dayScheduleMapper::toDto).toList());
        dto.setIsOdd(entity.getIsOdd());
        return dto;
    }

    @Override
    public WeekSchedule toEntity(WeekScheduleDto dto) {
        if (dto == null) {
            return null;
        }
        WeekSchedule entity = new WeekSchedule();
        entity.setId(dto.getId());
        entity.setDaysSchedule(dto.getDaysSchedule().stream().map(dayScheduleMapper::toEntity).toList());
        entity.setIsOdd(dto.getIsOdd());
        return entity;
    }
}
