package ua.com.foxminded.courseproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.com.foxminded.courseproject.dto.TeacherDto;
import ua.com.foxminded.courseproject.exceptions.TeacherConflictException;
import ua.com.foxminded.courseproject.exceptions.TeacherNotFoundException;
import ua.com.foxminded.courseproject.mapper.TeacherMapper;
import ua.com.foxminded.courseproject.repository.TeacherRepository;

import java.util.UUID;

@Service
public class TeacherServiceImpl implements PersonService<TeacherDto> {
    private TeacherMapper mapper;
    private TeacherRepository repository;

    @Autowired
    public TeacherServiceImpl(TeacherMapper mapper, TeacherRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public TeacherDto findById(UUID id) {
        return mapper.toDto(repository.findById(id).orElseThrow(() -> new TeacherNotFoundException(id)));
    }

    @Override
    public Page<TeacherDto> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public TeacherDto save(TeacherDto teacher) {
        if (personExists(teacher)) {
            throw new TeacherConflictException(teacher);
        }
        return mapper.toDto(repository.save(mapper.toEntity(teacher)));
    }

    @Override
    public void delete(UUID id) {
        repository.delete(repository.findById(id).get());
    }

    @Override
    public Boolean personExists(TeacherDto personDto) {
        return repository.existsTeacherByFirstNameAndLastNameAndBirthDay(personDto.getFirstName(),
                personDto.getLastName(), personDto.getBirthDay());
    }
}
