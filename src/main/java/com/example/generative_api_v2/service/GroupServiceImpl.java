package com.example.generative_api_v2.service;

import com.example.generative_api_v2.db.hibernate.GroupHibernateRepository;
import com.example.generative_api_v2.db.jpaRepositories.GroupRepository;
import com.example.generative_api_v2.dto.GroupDTO;
import com.example.generative_api_v2.mapper.GroupMapper;
import com.example.generative_api_v2.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class GroupServiceImpl implements GroupService {

    private GroupRepository groupRepository;
    private GroupMapper groupMapper;

    public GroupServiceImpl() {
    }


    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }

    @Transactional
    @Override
    public Group save(GroupDTO groupDTO) {
        Group group = new Group();
        groupMapper.map(group, groupDTO);
        return groupRepository.save(group);
    }

    @Transactional
    @Override
    public List<Group> getAll() {
        return groupRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        groupRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Group getById(int id) {
        Optional<Group> finded = groupRepository.findById(id);
        return finded.orElse(null);
    }

    @Transactional
    @Override
    public Group updateById(int id, GroupDTO groupDTO) {
        Optional<Group> finded = groupRepository.findById(id);
        if (finded.isPresent()) {
            groupMapper.map(finded.get(), groupDTO);
            groupRepository.save(finded.get());
            return finded.get();
        }

        return null;
    }
}
