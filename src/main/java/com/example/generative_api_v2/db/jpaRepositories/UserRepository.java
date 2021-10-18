package com.example.generative_api_v2.db.jpaRepositories;

import com.example.generative_api_v2.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

    User save(User u);
}
