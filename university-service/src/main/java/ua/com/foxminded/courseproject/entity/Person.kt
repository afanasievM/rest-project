package ua.com.foxminded.courseproject.entity

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class Person(
    @JvmField
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    var id: UUID? = null,

    @JvmField
    @Column(name = "firstname")
    var firstName: String? = null,

    @JvmField
    @Column(name = "lastname")
    var lastName: String? = null,

    @JvmField
    @Column(name = "birthday")
    var birthDay: LocalDate? = null
)
