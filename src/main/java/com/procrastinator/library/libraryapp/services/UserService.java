package com.procrastinator.library.libraryapp.services;

import com.procrastinator.library.libraryapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    /* UserDetailsService class is just a type of functionality provided by Spring Security.
    * So that we can decide on our own from where to fetch the data.
    * And the matching part of passwords being match is handled in UserDetailsService.
    * We just have to write logic of how are gonna retrieve the data. */

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        /* We can write logic to retrieve data from Cache/DB or any other Data Source.
        * e.g. DataSource : Redis,MongoDB,MySQL,Excel,etc */
        return userRepository.findByname(s);
    }
}
