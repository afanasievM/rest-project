package ua.com.foxminded.courseproject.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Streamable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.courseproject.entity.Group;
import ua.com.foxminded.courseproject.entity.Student;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(value = "classpath:initial_data.sql")
@Transactional
class StudentRepositoryTest {

    @Autowired
    private StudentRepository repository;

    @Test
    void findAll_shouldReturnListStudents() {
        Integer expectedSize = 2;

        List<Student> Students = Streamable.of(repository.findAll()).toList();

        assertEquals(expectedSize, Students.size());
    }

    @Test
    void findById_shouldReturnStudent_whenIdExists() {
        UUID id = UUID.fromString("f92afb9e-462a-11ed-b878-0242ac120002");
        String expectedFirstname = "Yura";

        Student actual = repository.findById(id).get();

        assertEquals(expectedFirstname, actual.getFirstName());
    }

    @Test
    void findById_shouldReturnEmptyOptional_whenIdNotExists() {
        UUID id = UUID.fromString("e966f7c0-4621-11ed-b838-0242ac120002");

        Optional<Student> actual = repository.findById(id);

        assertEquals(true, actual.isEmpty());
    }

    @Test
    void save_shouldAddStudent_whenStudentNotExists() {
        String testStr = "test";
        Student expected = new Student();
        Group group = new Group();
        group.setId(UUID.fromString("4937378e-4620-11ed-b878-0242ac120002"));
        group.setName("ET-01");
        expected.setFirstName(testStr);
        expected.setLastName(testStr);
        expected.setGroup(group);
        expected.setBirthDay(LocalDate.now());
        expected.setCourse(4);
        expected.setCaptain(false);

        repository.save(expected);

        assertEquals(expected, repository.findById(expected.getId()).get());
    }

    @Test
    void save_shouldChangeStudent_whenStudentExists() {
        Student expected = Streamable.of(repository.findAll()).stream().findAny().get();
        expected.setLastName("test");

        repository.save(expected);

        assertEquals(expected, repository.findById(expected.getId()).get());
    }

    @Test
    void delete_shouldDeleteStudent_whenStudentExists() {
        Student Student = Streamable.of(repository.findAll()).stream().findAny().get();
        Integer expectedSize = 1;

        repository.delete(Student);
        List<Student> Students = Streamable.of(repository.findAll()).toList();

        assertEquals(expectedSize, Students.size());
    }

    @Test
    void delete_shouldNotDeleteStudents_whenStudentNotExists() {
        String testStr = "test";
        Student student = new Student();
        Group group = new Group();
        group.setId(UUID.fromString("4937378e-4620-11ed-b878-0242ac120002"));
        group.setName("ET-01");
        student.setFirstName(testStr);
        student.setLastName(testStr);
        student.setGroup(group);
        student.setBirthDay(LocalDate.now());
        student.setCourse(4);
        student.setCaptain(false);
        Integer expectedSize = 2;

        repository.delete(student);
        List<Student> Students = Streamable.of(repository.findAll()).toList();

        assertEquals(expectedSize, Students.size());
    }

}