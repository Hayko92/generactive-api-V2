package com.example.generative_api_v2.configuration.userService;

import com.example.generative_api_v2.db.jpaRepositories.UserRepository;
import com.example.generative_api_v2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
   @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepository.findByUsername(username);
       if(user==null) throw new UsernameNotFoundException("USER NOT FOUND");
        return new MyUserPrincipal(user);
    }
}
