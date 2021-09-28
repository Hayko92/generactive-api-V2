package com.example.generative_api_v2.service;


import com.example.generative_api_v2.model.Group;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface GroupService {
    void save(Group group);

    List<Group> getAll();

    void deleteById(int id);

    Group getById(int id);

    void updateById(Group group);

}
