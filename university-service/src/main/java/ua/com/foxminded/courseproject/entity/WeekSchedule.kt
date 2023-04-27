package ua.com.foxminded.courseproject.entity

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "weeks")
data class WeekSchedule(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    var id: UUID? = null,

    @ManyToMany
    @JoinTable(
        name = "weeks_days",
        joinColumns = [JoinColumn(name = "week_id")],
        inverseJoinColumns = [JoinColumn(name = "day_id")]
    )
    var daysSchedule: MutableList<DaySchedule> = mutableListOf(),

    @Column(name = "odd")
    var isOdd: Boolean? = null
)
