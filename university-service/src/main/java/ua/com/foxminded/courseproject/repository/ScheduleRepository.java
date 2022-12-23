package ua.com.foxminded.courseproject.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.courseproject.entity.Schedule;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, UUID> {
    Optional<Schedule> findById(UUID id);

    List<Schedule> findAll();
}
