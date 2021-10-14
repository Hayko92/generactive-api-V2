package com.example.generative_api_v2.service;

import com.example.generative_api_v2.db.jpaRepositories.GroupRepository;
import com.example.generative_api_v2.dto.GroupDTO;
import com.example.generative_api_v2.mapper.GroupMapper;
import com.example.generative_api_v2.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.transaction.Transactional;
import java.util.Date;
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
       // groupDTO = setCreatingDate(groupDTO);
     //   groupDTO = setCreatingUserName(groupDTO);
        Group group = groupMapper.mapToGroup(groupDTO, new Group());
        group = groupRepository.save(group);
        return groupMapper.mapToGroupDTO(group);
    }

    @Transactional
    @Override
    public List<GroupDTO> getAll() {
        return groupRepository.findAll()
                .stream()
                .map(g -> groupMapper.mapToGroupDTO(g))
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
            Group group = finded.get();
            groupDTO.setId(id);
           // groupDTO = setUpdatingDate(groupDTO);
            group = groupMapper.mapToGroup(groupDTO, group);
            group = groupRepository.save(group);
            return groupMapper.mapToGroupDTO(group);
        }
        return null;
    }
//
//    @Override
//    @PrePersist
//    public GroupDTO setCreatingDate(GroupDTO group) {
//        group.setCreatedAt(new Date());
//        return group;
//    }
//
//    @Override
//    public GroupDTO setUpdatingDate(GroupDTO group) {
//        group.setUpdatedAt(new Date());
//        return group;
//    }
//
//    @Override
//    public GroupDTO setCreatingUserName(GroupDTO group) {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String name = userDetails.getUsername();
//        group.setCreatedBy(name);
//        return group;
//    }

}
