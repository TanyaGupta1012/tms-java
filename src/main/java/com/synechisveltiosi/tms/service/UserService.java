package com.synechisveltiosi.tms.service;

import com.synechisveltiosi.tms.entity.Users;
import com.synechisveltiosi.tms.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService { // Implement UserDetailsService

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // Correct method name
        Optional<Users> user = userRepository.findByUsername(username);

        if(!user.isPresent()){
            throw new UsernameNotFoundException("User not found");
        }

        Users foundUser = user.get();

        return org.springframework.security.core.userdetails.User
                .withUsername(foundUser.getUsername())
                .password(foundUser.getPassword())
                .roles("USER")
                .build();
    }
}
