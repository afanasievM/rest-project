package ua.com.foxminded.courseproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.com.foxminded.courseproject.dto.StudentDto;
import ua.com.foxminded.courseproject.exceptions.StudentNotFoundException;
import ua.com.foxminded.courseproject.mapper.StudentMapper;
import ua.com.foxminded.courseproject.repository.StudentRepository;

import java.util.UUID;

@Service
public class StudentServiceImpl implements PersonService<StudentDto> {
    private StudentMapper mapper;
    private StudentRepository repository;

    @Autowired
    public StudentServiceImpl(StudentMapper mapper, StudentRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public StudentDto findById(UUID id) {
        return mapper.toDto(repository.findById(id).orElseThrow(() -> new StudentNotFoundException(id)));
    }

    @Override
    public Page<StudentDto> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public StudentDto save(StudentDto student) {
        return mapper.toDto(repository.save(mapper.toEntity(student)));
    }

    @Override
    public void delete(UUID id) {
        repository.delete(repository.findById(id).get());
    }

    @Override
    public Boolean personExists(StudentDto personDto) {
        return repository.existsStudentByFirstNameAndLastNameAndBirthDay(personDto.getFirstName(),
                personDto.getLastName(), personDto.getBirthDay());
    }


}
