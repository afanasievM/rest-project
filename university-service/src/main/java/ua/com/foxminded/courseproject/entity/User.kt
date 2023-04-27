package ua.com.foxminded.courseproject.entity

import ua.com.foxminded.courseproject.enums.Role
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @Column(name = "username", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    var username: String? = null,

    @Column(name = "password")
    var password: String? = null,

    @OneToOne
    @JoinColumn(name = "person_id")
    var person: Person? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    var role: Role? = null
)
