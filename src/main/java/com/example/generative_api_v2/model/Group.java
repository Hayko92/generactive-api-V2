package com.example.generative_api_v2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "group_")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title")
    private String title;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent")
    @JsonBackReference
    private Group parent;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent")
    private List<Item> items;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent")
    @JsonManagedReference
    private List<Group> groups;

    public Group() {
        this.title = "";
        this.items = new ArrayList<>();
        this.groups = new ArrayList<>();
    }

    public Group(String title) {
        this.title = title;
        this.items = new ArrayList<>();
        this.groups = new ArrayList<>();
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addItem(Item item) {
        this.items.add(item);
        item.setParent(this);
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void addGroup(Group group) {
        this.groups.add(group);
        group.setParent(this.parent);
    }

    public List<Item> getItems() {
        return items;
    }

    public Integer getId() {
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

    @Override
    public String toString() {
        return "model.Group{" +
                "id=" + id +
                ", title='" + title + "}";
    }

    public static Group buildNewGroup(String title) {
        return new Group(title);
    }

    public void print(int level) {
        System.out.printf("GROUP - id: {%d} {%s}%n", id, title);
        printSubGroups(++level);
        printItems(level);
    }

    private void printSubGroups(int level) {
        String subLevelPrefix = "  ".repeat(level);
        for (Group group : groups) {
            System.out.print(subLevelPrefix);
            group.print(level);
        }
    }

    private void printItems(int level) {
        String subLevelPrefix = "  ".repeat(level);
        for (Item item : items) {
            System.out.print(subLevelPrefix);
            item.print();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id == group.id && Objects.equals(title, group.title) && Objects.equals(parent, group.parent) && Objects.equals(items, group.items) && Objects.equals(groups, group.groups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, parent, items, groups);
    }

}
