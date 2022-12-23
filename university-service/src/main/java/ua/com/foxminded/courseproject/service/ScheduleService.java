package ua.com.foxminded.courseproject.service;

import ua.com.foxminded.courseproject.dto.ScheduleDto;

import java.util.List;
import java.util.UUID;

public interface ScheduleService {
    public ScheduleDto findById(UUID id);

    public List<ScheduleDto> findAll();
}
