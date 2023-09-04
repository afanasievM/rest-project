package ua.com.foxminded.courseproject.repository

import org.springframework.data.repository.reactive.ReactiveSortingRepository
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.entity.User

interface UserRepository : ReactiveSortingRepository<User, String> {
    fun findByUsername(username: String): Mono<User>
}
