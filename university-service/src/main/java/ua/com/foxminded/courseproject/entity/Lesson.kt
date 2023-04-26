package ua.com.foxminded.courseproject.entity

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import java.time.LocalTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "lessons")
data class Lesson(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    var id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject")
    var subject: Subject? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom")
    var classRoom: ClassRoom? = null,

    @Column(name = "number")
    var number: Int? = null,

    @Column(name = "start_time")
    var startTime: LocalTime? = null,

    @Column(name = "end_time")
    var endTime: LocalTime? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher")
    var teacher: Teacher? = null,

    @ManyToMany
    @JoinTable(
        name = "lessons_groups",
        joinColumns = [JoinColumn(name = "lesson_id")],
        inverseJoinColumns = [JoinColumn(name = "group_id")]
    )
    var groups: MutableList<Group> = mutableListOf()
)
