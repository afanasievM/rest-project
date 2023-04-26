package ua.com.foxminded.courseproject.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ua.com.foxminded.courseproject.dto.PersonDto;
import ua.com.foxminded.courseproject.service.PersonService;

import java.util.UUID;

public class PersonController<T extends PersonDto, S extends PersonService> {

    S service;

    protected ResponseEntity getPersons(Pageable pageable) {
        return new ResponseEntity(service.findAll(pageable), HttpStatus.OK);
    }

    protected ResponseEntity getPersonById(UUID id) {
        T person = (T) service.findById(id);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    protected ResponseEntity deletePersonById(UUID id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
