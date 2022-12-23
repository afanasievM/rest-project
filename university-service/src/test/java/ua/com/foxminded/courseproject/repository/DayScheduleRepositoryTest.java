package ua.com.foxminded.courseproject.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Streamable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.courseproject.entity.DaySchedule;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(value = "classpath:initial_data.sql")
@Transactional
class DayScheduleRepositoryTest {

    @Autowired
    private DayScheduleRepository repository;

    @Test
    void findAll_shouldReturnListDays() {
        Integer expectedSize = 14;

        List<DaySchedule> days = Streamable.of(repository.findAll()).toList();

        assertEquals(expectedSize, days.size());
    }

    @Test
    void findById_shouldReturnDay_whenIdExists() {
        UUID id = UUID.fromString("949694f0-4633-11ed-b878-0242ac120002");
        Integer expectedNumber = 1;

        DaySchedule actual = repository.findById(id).get();

        assertEquals(expectedNumber, actual.getDayNumber());
    }

    @Test
    void findById_shouldReturnEmptyOptional_whenIdNotExists() {
        UUID id = UUID.fromString("e966f7c0-4621-11ed-b838-0242ac120002");

        Optional<DaySchedule> actual = repository.findById(id);

        assertEquals(true, actual.isEmpty());
    }

    @Test
    void findDayScheduleByDayNumberFromOddWeek_shouldReturnDay_whenNumberExistsAndWeekOdd() {
        UUID idExpected = UUID.fromString("949694f0-4633-11ed-b878-0242ac120002");
        DaySchedule expected = repository.findById(idExpected).get();
        Integer number = 1;

        DaySchedule actual = repository.findDayScheduleByDayNumberFromOddWeek(number).get();

        assertEquals(expected, actual);
    }

    @Test
    void findDayScheduleByDayNumberFromEvenWeek_shouldReturnDay_whenNumberExistsAndWeekEven() {
        UUID idExpected = UUID.fromString("9496a616-4633-11ed-b878-0242ac120002");
        DaySchedule expected = repository.findById(idExpected).get();
        Integer number = 1;

        DaySchedule actual = repository.findDayScheduleByDayNumberFromEvenWeek(number).get();

        assertEquals(expected, actual);
    }

    @Test
    void findDayScheduleByDayNumberFromOddWeek_shouldReturnEmptyOptional_whenNumberNotExistsAndWeekOdd() {
        Integer number = 10;

        Optional<DaySchedule> actual = repository.findDayScheduleByDayNumberFromOddWeek(number);

        assertEquals(Optional.empty(), actual);
    }

    @Test
    void findDayScheduleByDayNumberFromEvenWeek_shouldReturnEmptyOptional_whenNumberNotExistsAndWeekEven() {
        Integer number = 10;

        Optional<DaySchedule> actual = repository.findDayScheduleByDayNumberFromOddWeek(number);

        assertEquals(Optional.empty(), actual);
    }

}