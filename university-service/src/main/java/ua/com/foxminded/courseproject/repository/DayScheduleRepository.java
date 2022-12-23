package ua.com.foxminded.courseproject.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.courseproject.entity.DaySchedule;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DayScheduleRepository extends CrudRepository<DaySchedule, UUID>{

    @Transactional
    @Query(value = "SELECT * FROM {h-schema}day_schedule WHERE day_number=(:number) AND id IN " +
            "(SELECT day_id FROM {h-schema}weeks_days WHERE week_id = " +
            "(SELECT id FROM {h-schema}weeks WHERE odd=FALSE))", nativeQuery = true)
    Optional<DaySchedule> findDayScheduleByDayNumberFromOddWeek(@Param("number") Integer number);

    @Transactional
    @Query(value = "SELECT * FROM {h-schema}day_schedule WHERE day_number=(:number) AND id IN " +
            "(SELECT day_id FROM {h-schema}weeks_days WHERE week_id = " +
            "(SELECT id FROM {h-schema}weeks WHERE odd=TRUE))", nativeQuery = true)
    Optional<DaySchedule> findDayScheduleByDayNumberFromEvenWeek(@Param("number") Integer number);

}
