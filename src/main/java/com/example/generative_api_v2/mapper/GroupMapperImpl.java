package com.example.generative_api_v2.mapper;

import com.example.generative_api_v2.dto.GroupDTO;
import com.example.generative_api_v2.model.Group;
import org.springframework.stereotype.Component;

@Component
public class GroupMapperImpl implements GroupMapper{
    @Override
    public Group map(Group group, GroupDTO groupDTO) {
        group.setTitle(groupDTO.getTitle());
        group.setParent(groupDTO.getParent());
        group.setItems(groupDTO.getItems());
        group.setGroups(groupDTO.getGroups());
        return group;
    }
}
