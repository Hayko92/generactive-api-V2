package com.example.generative_api_v2.configuration.userService;

import com.example.generative_api_v2.db.jpaRepositories.RoleRepository;
import com.example.generative_api_v2.db.jpaRepositories.UserRepository;
import com.example.generative_api_v2.model.Authority;
import com.example.generative_api_v2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public MyUserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("USER NOT FOUND");
        return new MyUserPrincipal(user);
    }

    public User saveUser(User userEntity) {
        Authority userRole = roleRepository.findByAuthority("USER");
        Set<Authority> roles = Set.of(userRole);
        userEntity.setRoles(roles);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }

    public User findByLoginAndPassword(String login, String password) {
        User userEntity = userRepository.findByUsername(login);
        if (userEntity != null) {
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                return userEntity;
            }
        }
        return null;
    }

}
