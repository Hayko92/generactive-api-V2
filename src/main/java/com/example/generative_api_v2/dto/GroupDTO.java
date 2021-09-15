package com.example.generative_api_v2.dto;

import com.example.generative_api_v2.model.Group;
import com.example.generative_api_v2.model.Item;

import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

public class GroupDTO {
    private   int id;
    private String title;
    @ManyToOne
    private Group parent;
    private final List<Item> items;
    private final List<Group> groups;

    public GroupDTO() {
        this.title = "";
        this.items = new ArrayList<>();
        this.groups = new ArrayList<>();
    }

    public GroupDTO(String title) {
        this.title = title;
        this.items = new ArrayList<>();
        this.groups = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public List<Group> getGroups() {
        return groups;
    }

public void setTitle(String title) {
        this.title = title;
}

    public List<Item> getItems() {
        return items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setParent(Group parent) {
        this.parent = parent;
    }

    public Group getParent() {
        return parent;
    }
}
