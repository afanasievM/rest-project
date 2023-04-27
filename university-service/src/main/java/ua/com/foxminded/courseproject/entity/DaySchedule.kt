package ua.com.foxminded.courseproject.entity

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "day_schedule")
data class DaySchedule(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    var id: UUID? = null,

    @ManyToMany
    @JoinTable(
        name = "days_lessons",
        joinColumns = [JoinColumn(name = "day_id")],
        inverseJoinColumns = [JoinColumn(name = "lesson_id")]
    )
    var lessons: MutableList<Lesson> = mutableListOf(),

    @Column(name = "day_number")
    var dayNumber: Int? = null

)
