package com.example.generative_api_v2.util;

import com.example.generative_api_v2.db.jdbc.Storage;
import com.example.generative_api_v2.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

public final class UserInputUtil {
    public static void createGroup(BufferedReader bf) throws IOException {
        while (true) {
            System.out.println("Please Enter name of group");
            String title = bf.readLine();
            if (title.equalsIgnoreCase("exit")) break;
            UserInputUtil.createSubgroup(title, bf);

        }
    }

    public static void createSubgroup(String title, BufferedReader bf) throws IOException {
        while (true) {
            System.out.println("Please Enter" +
                    "\n 1. ID of parent" +
                    "\n 2. Enter to create group as root level group" +
                    "\n 3. Continue to fill items in group");
            String command = bf.readLine();
            if (command.equalsIgnoreCase("exit")) break;
            if (command.equalsIgnoreCase("enter")) {
                Storage.addGroup(Group.buildNewGroup(title));
                break;
            } else if (command.equalsIgnoreCase("continue")) {
                Storage.addGroup(Group.buildNewGroup(title));
                createItem(bf);
                break;
            } else {
                try {
                    int parentID = Integer.parseInt(command);
                    Optional<Group> parentGroup = Storage.getGroupById(parentID);

                    if (parentGroup.isEmpty()) System.out.println("Wrong Id");
                    else {
                        Group group = Group.buildNewGroup(title);
                        Storage.addGroup(group);
                        parentGroup.get().addGroup(group);
                    }
                } catch (Exception e) {
                    System.out.println("Something went wrong...");
                    break;
                }
            }
        }
    }

    public static void createItem(BufferedReader bf) {
        while (true) {
            try {
                System.out.println("Please type title of item");
                String title = bf.readLine();
                if (title.equalsIgnoreCase("exit")) break;
                System.out.println("Please type price of item");
                int price = Integer.parseInt(bf.readLine());
                System.out.println("Please type image_URL of item");
                String imageUrl = bf.readLine();
                System.out.println("please enter type of item\n" +
                        "1. Stock" +
                        "\n2. Generative");
                String type = bf.readLine();
                double complexity = 0;
                if (type.equalsIgnoreCase("Generative")) {
                    System.out.println("Please enter complexity from 1 to 2;");
                    String compl = bf.readLine();
                    complexity = Double.parseDouble(compl);
                }
                System.out.println("please enter type of Resolution\n" +
                        "1. HD" +
                        "\n2. FHD"
                        + "\n3.FourK  ");
                String resolution = bf.readLine();
                Resolution resolution1 = Resolution.valueOf(resolution);

                System.out.println("Please type currency of item");
                String currency = bf.readLine();
                System.out.println("Please type id of group");
                int groupId = Integer.parseInt(bf.readLine());
                Optional<Group> parent = Storage.getGroupById(groupId);
                if (parent.isPresent()) {
                   // Storage.addItem(type, title, price, imageUrl, complexity, currency);
                    parent.get().addItem(Storage.getLastItem().get());
                }
            } catch (Exception e) {
                System.out.println("Something went wrong.. we are sorry");
                break;
            }
        }
    }

    public static void createBasket(BufferedReader bf) throws IOException {
        System.out.println("Please enter ID of items you want to add in basket or type exit");
        System.out.println("available items: " + Storage.getItemList());
        String command = bf.readLine();
        Basket basket = new Basket();
        while (!command.equalsIgnoreCase("exit")) {
            try {
                int id = Integer.parseInt(command);
                Optional<Item> item = Storage.getItemById(id);
                item.ifPresent(value -> basket.getItems().add(value));
                System.out.println("Item is added successfully... type next ID or type exit");
                command = bf.readLine();
            } catch (Exception e) {
                System.out.println("Something went wrong...we are sorry");
            }
        }
      //  basket.printPrice( );
    }

    private UserInputUtil() {
    }
}
