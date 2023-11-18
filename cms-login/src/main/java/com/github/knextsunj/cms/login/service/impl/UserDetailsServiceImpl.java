package com.github.knextsunj.cms.login.service.impl;

import com.github.knextsunj.cms.login.domain.User;
import com.github.knextsunj.cms.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        List<User> userList =repository.findAll();
        User user = repository.findByUsername(username);

        org.springframework.security.core.userdetails.User.UserBuilder builder = null;
        if (Objects.nonNull(user)) {
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(user.getPassword());
            builder.roles(user.getRole());
        } else {
            throw new UsernameNotFoundException("User not found.");
        }

        return builder.build();
    }
}
