package ua.com.foxminded.courseproject.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ua.com.foxminded.courseproject.entity.User

interface UserRepository : MongoRepository<User?, String?> {
    fun findByUsername(username: String?): User?
}