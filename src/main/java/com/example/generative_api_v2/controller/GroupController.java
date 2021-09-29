package com.example.generative_api_v2.controller;

import com.example.generative_api_v2.dto.GroupDTO;
import com.example.generative_api_v2.model.Group;
import com.example.generative_api_v2.service.GroupService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public List<Group> getAll() {
        return groupService.getAll();
    }

    @PostMapping
    public Group add(@RequestBody GroupDTO sendedGroup) throws JsonProcessingException {
        return groupService.save(sendedGroup);
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
    public Group updateByyId(@PathVariable int id, @RequestBody GroupDTO updatedGroup) {
        return groupService.updateById(id, updatedGroup);
    }

}
