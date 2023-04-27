package ua.com.foxminded.courseproject.entity

import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "teachers")
data class Teacher(

    @Column(name = "degree")
    var degree: String? = null,

    @Column(name = "salary")
    var salary: Int? = null,

    @Column(name = "first_date")
    var firstDay: LocalDate? = null,

    @Column(name = "rank")
    var rank: String? = null,

    @Column(name = "title")
    var title: String? = null
) : Person()