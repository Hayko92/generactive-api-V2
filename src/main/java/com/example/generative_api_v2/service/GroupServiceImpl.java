package com.example.generative_api_v2.service;

import com.example.generative_api_v2.db.jpaRepositories.GroupRepository;
import com.example.generative_api_v2.dto.GroupDTO;
import com.example.generative_api_v2.mapper.GroupMapper;
import com.example.generative_api_v2.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public GroupDTO save(GroupDTO groupDTO) {
        Group group = groupMapper.mapToGroup(groupDTO);
        group = groupRepository.save(group);
        return groupMapper.mapToGroupDTO(group);
    }

    @Transactional
    @Override
    public List<GroupDTO> getAll() {
        return groupRepository.findAll()
                .stream()
                .map(g->groupMapper.mapToGroupDTO(g))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        groupRepository.deleteById(id);
    }

    @Transactional
    @Override
    public GroupDTO getById(int id) {
        Optional<Group> finded = groupRepository.findById(id);
        return finded.map(group -> groupMapper.mapToGroupDTO(group)).orElse(null);
    }

    @Transactional
    @Override
    public GroupDTO updateById(int id, GroupDTO groupDTO) {
        Optional<Group> finded = groupRepository.findById(id);
        if (finded.isPresent()) {
            Group group =   groupMapper.mapToGroup( groupDTO);
            groupRepository.save(finded.get());
            return groupMapper.mapToGroupDTO(finded.get());
        }

        return null;
    }

}
