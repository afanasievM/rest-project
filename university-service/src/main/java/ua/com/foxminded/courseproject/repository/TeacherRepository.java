package ua.com.foxminded.courseproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.courseproject.entity.Teacher;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeacherRepository extends CrudRepository<Teacher, UUID> {
    Page<Teacher> findAll(Pageable pageable);

    Optional<Teacher> findById(UUID id);

    Teacher save(Teacher teacher);

    void delete(Teacher teacher);

    Boolean existsTeacherByFirstNameAndLastNameAndBirthDay(String firstName, String lastName, LocalDate birthDay);
}
