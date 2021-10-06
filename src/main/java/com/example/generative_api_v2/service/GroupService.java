package com.example.generative_api_v2.service;


import com.example.generative_api_v2.dto.GroupDTO;
import com.example.generative_api_v2.model.Group;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface GroupService {
    GroupDTO save(GroupDTO group);

    List<GroupDTO> getAll();

    void deleteById(int id);

    GroupDTO getById(int id);

    GroupDTO updateById(int id, GroupDTO group);

}
