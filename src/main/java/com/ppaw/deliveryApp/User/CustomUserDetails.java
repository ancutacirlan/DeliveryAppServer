package com.ppaw.deliveryApp.User;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetails implements UserDetailsService {
    private final UserRepository userRepository;
    public CustomUserDetails(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override   // this function is uses spring loadUserByUsername method that retrieves object from db and returns into spring security provided user.
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email);
        if(user!=null){
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                    List.of(new SimpleGrantedAuthority(user.getRole().name())));
        }
        else{
            throw new UsernameNotFoundException("Invalid email or password");
        }
    }
}