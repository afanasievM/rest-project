package ua.com.foxminded.courseproject.entity

import javax.persistence.*

@Entity
@Table(name = "students")
data class Student  (
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    var group: Group? = null,

    @Column(name = "course")
    var course: Int? = null,

    @Column(name = "is_captain")
    var captain: Boolean? = null
) : Person()
