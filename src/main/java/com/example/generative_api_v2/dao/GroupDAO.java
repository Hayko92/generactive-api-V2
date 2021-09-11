package com.example.generative_api_v2.dao;

import com.example.generative_api_v2.model.Group;

import java.util.List;

public interface GroupDAO {
    void add(Group group);
    List<Group> getAll();
    Group getById(int id);
    void deleteById(int id);
    void  updateById( Group group);
}
