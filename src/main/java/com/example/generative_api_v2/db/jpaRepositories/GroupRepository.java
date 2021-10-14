package com.example.generative_api_v2.db.jpaRepositories;

import com.example.generative_api_v2.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    List<Group> findAll();
    Group getById(Integer id);

}
