package com.example.generative_api_v2.service;

import com.example.generative_api_v2.db.hibernate.GroupHibernateRepository;
import com.example.generative_api_v2.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupServiceImpl implements GroupService {

    private GroupHibernateRepository groupHibernateRepository;

    public GroupServiceImpl() {
    }

    @Autowired
    public GroupServiceImpl(GroupHibernateRepository groupHibernateRepository) {
        this.groupHibernateRepository = groupHibernateRepository;
    }

    @Override
    public void save(Group group) {
        groupHibernateRepository.add(group);
    }

    @Override
    public List<Group> getAll() {
        return groupHibernateRepository.getAll();
    }

    @Override
    public void deleteById(int id) {
        groupHibernateRepository.removeById(id);
    }

    @Override
    public Group getById(int id) {
        return groupHibernateRepository.getById(id);
    }

    @Override
    public void updateById(Group group) {
        groupHibernateRepository.updateById(group);
    }
}
