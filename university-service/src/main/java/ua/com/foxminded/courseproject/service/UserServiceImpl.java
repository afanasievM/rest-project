package ua.com.foxminded.courseproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ua.com.foxminded.courseproject.entity.User;
import ua.com.foxminded.courseproject.exceptions.UserNotFoundException;
import ua.com.foxminded.courseproject.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        Optional<User> userOptional = repository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(username);
        }
        User user = userOptional.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
