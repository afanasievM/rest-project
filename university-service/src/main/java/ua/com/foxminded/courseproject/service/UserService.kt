package ua.com.foxminded.courseproject.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails
}