package com.example.generative_api_v2.mapper;

import com.example.generative_api_v2.dto.GroupDTO;
import com.example.generative_api_v2.model.Group;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GroupMapperImpl implements GroupMapper {
    private final ItemMapper itemMapper = new ItemMapperImpl(this);

    public GroupMapperImpl() {

    }

    @Override
    public Group mapToGroup(GroupDTO groupDTO) {
        Group group = new Group();
        group.setId(groupDTO.getId());
        group.setTitle(groupDTO.getTitle());
        group.setItems(groupDTO.getItems()
                .stream()
                .map(itemMapper::mapToItem)
                .collect(Collectors.toList()));

        group.setGroups(groupDTO.getGroups()
                .stream()
                .map(this::mapToGroup)
                .collect(Collectors.toList()));
        if (groupDTO.getParent() != null) group.setParent(mapToGroup(groupDTO.getParent()));

        return group;
    }

    @Override
    public GroupDTO mapToGroupDTO(Group group) {
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setId(group.getId());
        groupDTO.setTitle(group.getTitle());
        if (group.getParent() != null) {
            groupDTO.setParentID(group.getParent().getId());
        }
        groupDTO.setGroups(group.getGroups()
                .stream()
                .map(this::mapToGroupDTO)
                .collect(Collectors.toList()));

        groupDTO.setItems(group.getItems()
                .stream()
                .map(itemMapper::mapToItemDTO)
                .collect(Collectors.toList()));
        return groupDTO;
    }
}
