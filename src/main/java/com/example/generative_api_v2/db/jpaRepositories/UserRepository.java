package com.example.generative_api_v2.db.jpaRepositories;

import com.example.generative_api_v2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
