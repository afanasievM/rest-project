package ua.com.foxminded.courseproject.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ua.com.foxminded.courseproject.entity.User

@Repository
interface UserRepository : CrudRepository<User?, String?> {
    fun findByUsername(username: String?): User?
}