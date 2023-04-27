package ua.com.foxminded.courseproject.entity

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "schedule")
data class Schedule(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    var id: UUID? = null,

    @ManyToMany
    @JoinTable(
        name = "schedule_weeks",
        joinColumns = [JoinColumn(name = "schedule_id")],
        inverseJoinColumns = [JoinColumn(name = "week_id")]
    )
    var weeks: MutableList<WeekSchedule> = mutableListOf(),

    @Column(name = "start_time")
    var startDate: Timestamp? = null,

    @Column(name = "end_time")
    var endDate: Timestamp? = null
)