package com.example.generative_api_v2.mapper;

import com.example.generative_api_v2.dto.GroupDTO;
import com.example.generative_api_v2.model.Group;

public interface GroupMapper {
    Group mapToGroup( GroupDTO groupDTO);
    GroupDTO mapToGroupDTO(Group group);
}
