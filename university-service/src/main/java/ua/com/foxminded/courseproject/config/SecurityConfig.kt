package ua.com.foxminded.courseproject.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import ua.com.foxminded.courseproject.service.UserServiceImpl

@Configuration
@EnableGlobalMethodSecurity(jsr250Enabled = true)
open class SecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    private val userService: UserServiceImpl? = null
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable().authorizeRequests()
            .anyRequest().authenticated()
            .and().httpBasic()
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userService).passwordEncoder(encoder())
    }

    @Bean
    open fun encoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
