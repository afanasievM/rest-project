package ua.com.foxminded.courseproject.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Streamable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.courseproject.entity.Teacher;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(value = "classpath:initial_data.sql")
@Transactional
class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository repository;

    @Test
    void findAll_shouldReturnListTeachers() {
        Integer expectedSize = 2;

        List<Teacher> teachers = Streamable.of(repository.findAll()).toList();

        assertEquals(expectedSize, teachers.size());
    }

    @Test
    void findById_shouldReturnTeacher_whenIdExists() {
        UUID id = UUID.fromString("e966f608-4621-11ed-b878-0242ac120002");
        String expectedFirstname = "Yulia";

        Teacher actual = repository.findById(id).get();

        assertEquals(expectedFirstname, actual.getFirstName());
    }

    @Test
    void findById_shouldReturnEmptyOptional_whenIdNotExists() {
        UUID id = UUID.fromString("e966f7c0-4621-11ed-b838-0242ac120002");

        Optional<Teacher> actual = repository.findById(id);

        assertEquals(true, actual.isEmpty());
    }

    @Test
    void save_shouldAddTeacher_whenTeacherNotExists() {
        String testStr = "test";
        Teacher expected = new Teacher();
        expected.setFirstName(testStr);
        expected.setLastName(testStr);
        expected.setDegree(testStr);
        expected.setFirstDay(LocalDate.now());
        expected.setBirthDay(LocalDate.now());
        expected.setRank(testStr);
        expected.setTitle(testStr);
        expected.setSalary(1111);

        repository.save(expected);

        assertEquals(expected, repository.findById(expected.getId()).get());
    }

    @Test
    void save_shouldChangeTeacher_whenTeacherExists() {
        Teacher expected = Streamable.of(repository.findAll()).stream().findAny().get();
        expected.setLastName("test");

        repository.save(expected);

        assertEquals(expected, repository.findById(expected.getId()).get());
    }

    @Test
    void delete_shouldDeleteTeacher_whenTeacherExists() {
        Teacher teacher = Streamable.of(repository.findAll()).stream().findAny().get();
        Integer expectedSize = 1;

        repository.delete(teacher);
        List<Teacher> teachers = Streamable.of(repository.findAll()).stream().toList();

        assertEquals(expectedSize, teachers.size());
    }

    @Test
    void delete_shouldNotDeleteTeachers_whenTeacherNotExists() {
        String testStr = "test";
        Teacher teacher = new Teacher();
        teacher.setFirstName(testStr);
        teacher.setLastName(testStr);
        teacher.setDegree(testStr);
        teacher.setFirstDay(LocalDate.now());
        teacher.setBirthDay(LocalDate.now());
        teacher.setRank(testStr);
        teacher.setTitle(testStr);
        teacher.setSalary(1111);
        Integer expectedSize = 2;

        repository.delete(teacher);
        List<Teacher> teachers = Streamable.of(repository.findAll()).toList();

        assertEquals(expectedSize, teachers.size());
    }

}