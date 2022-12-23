package ua.com.foxminded.courseproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.courseproject.entity.Student;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends CrudRepository<Student, UUID> {

    Optional<Student> findById(UUID id);

    Page<Student> findAll(Pageable pageable);

    Student save(Student student);

    void delete( Student student);

    Boolean existsStudentByFirstNameAndLastNameAndBirthDay(String firstName, String lastName, LocalDate birthDay);
}
