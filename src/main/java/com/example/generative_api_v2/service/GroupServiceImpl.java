package com.example.generative_api_v2.service;

import com.example.generative_api_v2.db.hibernate.GroupHibernateRepository;
import com.example.generative_api_v2.dto.GroupDTO;
import com.example.generative_api_v2.mapper.GroupMapper;
import com.example.generative_api_v2.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupServiceImpl implements GroupService {

    private GroupHibernateRepository groupHibernateRepository;
    private GroupMapper groupMapper;

    public GroupServiceImpl() {
    }

    @Autowired
    public GroupServiceImpl(GroupHibernateRepository groupHibernateRepository, GroupMapper groupMapper) {
        this.groupHibernateRepository = groupHibernateRepository;
        this.groupMapper = groupMapper;
    }

    @Override
    public Group save(GroupDTO groupDTO) {
        Group group = new Group();
        groupMapper.map(group, groupDTO);
        return groupHibernateRepository.add(group);
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
    public Group updateById(int id, GroupDTO groupDTO) {
        Group group = groupHibernateRepository.getById(id);
        groupMapper.map(group, groupDTO);
        groupHibernateRepository.updateById(group);
        return group;
    }
}
