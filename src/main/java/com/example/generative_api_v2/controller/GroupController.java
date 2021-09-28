package com.example.generative_api_v2.controller;

import com.example.generative_api_v2.dto.GroupDTO;
import com.example.generative_api_v2.model.Group;
import com.example.generative_api_v2.service.GroupService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Group> getAll() {
        return groupService.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Group add(@RequestBody String sendedGroup) throws JsonProcessingException {
        Group group = new ObjectMapper().readValue(sendedGroup, Group.class);
        groupService.save(group);
        return group;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        groupService.deleteById(id);
    }

    @GetMapping("/{id}")
    public Group getbyId(@PathVariable int id) {
        return groupService.getById(id);
    }

    @PutMapping("/{id}")
    public Group updateByyId(@PathVariable int id, @RequestBody String updatedGroup) throws JsonProcessingException {
        Group group = groupService.getById(id);
        GroupDTO groupDTO = new ObjectMapper().readValue(updatedGroup, GroupDTO.class);
        group.setTitle(groupDTO.getTitle());
        group.setParent(groupDTO.getParent());
        group.setItems(groupDTO.getItems());
        group.setGroups(groupDTO.getGroups());
        groupService.updateById(group);
        return group;
    }

}
