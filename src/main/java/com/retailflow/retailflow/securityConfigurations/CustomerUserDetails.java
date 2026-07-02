package com.retailflow.retailflow.securityConfigurations;

import com.retailflow.retailflow.exceptions.UserNotFoundException;
import com.retailflow.retailflow.model.User;
import com.retailflow.retailflow.repositery.UserRepositery;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetails implements UserDetailsService {
    private final UserRepositery userRepositery;

    public CustomerUserDetails(UserRepositery userRepositery) {
        this.userRepositery = userRepositery;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepositery.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }

}
