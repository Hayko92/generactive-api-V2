package com.example.generactive_api_v2.db;


import com.example.generactive_api_v2.model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public final class Storage {
    private static final List<Group> GROUP_LIST = new ArrayList<>();
    private static final List<Item> ITEM_LIST = new ArrayList<>();

    private Storage() {
    }

    public static void addGroup(Group group) {
        GROUP_LIST.add(group);
    }

    public static void addItem(String type, String title, int price, String image_URL, Configuration configuration, double complexity, String currency) throws Exception {
        if (type.equalsIgnoreCase("Stock")) {
            ITEM_LIST.add(new Stock(title, price, image_URL, configuration, currency));
        } else if (type.equalsIgnoreCase("Generative"))
            ITEM_LIST.add(new Generative(title, price, image_URL, configuration, complexity, currency));
        else throw new Exception("Wrong type");
    }

    public static void addItem(Item item) {
        ITEM_LIST.add(item);
    }

    public static Optional<Group> getGroupById(int id) {
        if (id >= 0 && id < GROUP_LIST.size()) return findGroupById(id);
        else return Optional.empty();
    }

    public static Optional<Item> getItemById(int id) {
        if (id >= 0 && id < ITEM_LIST.size()) return findItemById(id);
        else return Optional.empty();
    }

    public static Optional<Item> getLastItem() {
        if (GROUP_LIST.size() > 0) return getItemById(ITEM_LIST.size() - 1);
        else return null;
    }


    public static List<Group> getGroupList() {
        return GROUP_LIST;
    }

    public static List<Item> getItemList() {
        return ITEM_LIST;
    }

    public static Optional<Item> findItemById(int id) {
        return getItemList()
                .stream()
                .filter(el -> el.getId() == id)
                .findAny();
    }

    public static Optional<Item> findItemByName(String name) {
        return getItemList()
                .stream()
                .filter(el -> el.getTitle().equals(name))
                .findAny();
    }

    public static Optional<Group> findGroupById(int id) {
        return getGroupList()
                .stream()
                .filter(el -> el.getId() == id)
                .findAny();


    }

    public static Group findGroupByName(String name) {
        return getGroupList()
                .stream()
                .filter(el -> el.getTitle().equals(name))
                .findAny().orElse(null);

    }

    public static List<Group> findSubGroupsByParent(Group parent) {
        return getGroupList()
                .stream()
                .filter(el -> el.getParent().equals(parent))
                .collect(Collectors.toList());
    }

    public static List<Item> findHighestPricedItems() {
        int highestPrice = getItemList()
                .stream()
                .max(Comparator.comparingInt(Item::getPrice))
                .get().getPrice();

        return getItemList()
                .stream()
                .filter(el -> el.getPrice() == highestPrice)
                .collect(Collectors.toList());
    }

    public static List<Item> findAllItemsByPrice(int price) {
        return getItemList()
                .stream().filter(el -> el.getPrice() == price)
                .collect(Collectors.toList());
    }

    public static List<Group> findSubGroupsByParentId(int parentId) {
        return getGroupList()
                .stream()
                .filter(el -> el.getId() == parentId)
                .map(Group::getGroups)
                .findAny()
                .orElse(null);
    }

    public static List<Item> getItemsWithPriceFromTo(int from, int to) {
        List<Item> result = Storage.getItemList()
                .stream()
                .filter(el -> el.getPrice() > from && el.getPrice() < to)
                .collect(Collectors.toList());
        return result;
    }
}

