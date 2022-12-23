package ua.com.foxminded.courseproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import ua.com.foxminded.courseproject.dto.ScheduleDto;
import ua.com.foxminded.courseproject.mapper.ScheduleMapper;
import ua.com.foxminded.courseproject.repository.ScheduleRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    private ScheduleMapper mapper;
    private ScheduleRepository repository;

    @Autowired
    public ScheduleServiceImpl(ScheduleMapper mapper, ScheduleRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public ScheduleDto findById(UUID id) {
        return mapper.toDto(repository.findById(id).get());
    }

    @Override
    public List<ScheduleDto> findAll() {
        return Streamable.of(repository.findAll()).map(mapper::toDto).toList();
    }
}
