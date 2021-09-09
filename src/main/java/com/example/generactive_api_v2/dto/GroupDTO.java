package com.example.generactive_api_v2.dto;

import com.example.generactive_api_v2.model.Group;
import com.example.generactive_api_v2.model.Item;

import java.util.ArrayList;
import java.util.List;

public class GroupDTO {
    private   int id;
    private String title;
    private int parent;
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

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getParent() {
        return parent;
    }
}
