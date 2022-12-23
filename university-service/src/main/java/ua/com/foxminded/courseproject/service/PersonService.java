package ua.com.foxminded.courseproject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.foxminded.courseproject.dto.PersonDto;

import java.util.UUID;

public interface PersonService<T extends PersonDto> {
    public T findById(UUID id);

    public Page<T> findAll(Pageable pageable);

    public T save(T dto);

    public void delete(UUID id);

    public Boolean personExists(T personDto);

}
