package ua.com.foxminded.courseproject.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Streamable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.courseproject.entity.Schedule;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(value = "classpath:initial_data.sql")
@Transactional
class ScheduleRepositoryTest {

    @Autowired
    private ScheduleRepository repository;

    @Test
    void findAll_shouldReturnListDays() {
        Integer expectedSize = 1;

        List<Schedule> schedules = Streamable.of(repository.findAll()).toList();

        assertEquals(expectedSize, schedules.size());
    }

    @Test
    void findById_shouldReturnDay_whenIdExists() {
        UUID id = UUID.fromString("b5fd8224-47ba-11ed-b878-0242ac120002");
        Timestamp expectedTime = Timestamp.valueOf("2022-09-01 08:00:00");

        Schedule actual = repository.findById(id).get();

        assertEquals(expectedTime, actual.getStartDate());
    }


}