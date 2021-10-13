package com.example.generative_api_v2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GroupDTO {
    private int id;
    private String title;
    private int parentID;
    @JsonIgnore
    private GroupDTO parent;
    private   List<ItemDTO> items;
    private   List<GroupDTO> groups;
     private String createdBy;
     @JsonFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
     @JsonFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }

    public void setGroups(List<GroupDTO> groups) {
        this.groups = groups;
    }

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

    public List<GroupDTO> getGroups() {
        return groups;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setParent(GroupDTO parent) {
        this.parent = parent;
        this.parentID =parent.id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public GroupDTO getParent() {
        return parent;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }


    public boolean containing(int groupID) {
        boolean res = false;
        for(GroupDTO groupDTO: groups) {
            if(groupDTO.parentID==groupID) return true;
            res = groupDTO.containing(id);
            if(res) break; 
        } return res;

    }
}
