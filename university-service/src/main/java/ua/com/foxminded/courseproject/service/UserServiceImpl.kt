package ua.com.foxminded.courseproject.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import ua.com.foxminded.courseproject.exceptions.UserNotFoundException
import ua.com.foxminded.courseproject.repository.UserRepository

@Service
class UserServiceImpl @Autowired constructor(private val repository: UserRepository) : ReactiveUserDetailsService {

    @Throws(UserNotFoundException::class)
    override fun findByUsername(username: String?): Mono<UserDetails> {
        val user = repository.findByUsername(username!!).block() ?: throw UserNotFoundException()
        val authorities: MutableList<GrantedAuthority> = ArrayList()
        authorities.add(SimpleGrantedAuthority(user.role.toString()))
        return User(user.username, user.password, authorities).toMono()
    }
}