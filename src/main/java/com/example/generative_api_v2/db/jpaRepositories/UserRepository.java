package com.example.generative_api_v2.db.jpaRepositories;

import com.example.generative_api_v2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    User findByUsername(String username);
    @Query("select u from  User u where u.username=?1 and u.password=?2")
    User findByUsernameAndPassword(String username, String password);
}
