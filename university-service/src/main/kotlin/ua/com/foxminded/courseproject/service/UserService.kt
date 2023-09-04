package ua.com.foxminded.courseproject.service

import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import reactor.core.publisher.Mono

interface UserService : ReactiveUserDetailsService {
    override fun findByUsername(username: String?): Mono<UserDetails>
}
