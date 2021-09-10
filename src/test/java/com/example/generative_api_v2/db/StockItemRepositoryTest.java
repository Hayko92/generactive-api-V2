package com.example.generative_api_v2.db;

import com.example.generative_api_v2.dto.ItemDTO;
import com.example.generative_api_v2.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class StockItemRepositoryTest {
    @BeforeEach
    protected   void clearItemAndGroupTables() {
        try(Connection connection = TestDatabaseConnection.getConnection()) {
           GroupJDBCRepository.clear(connection);
            StockItemJDBCRepository.clear(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addTest() {
        Item item = new Item("test_name", 100, "test_url", "USD");
        StockItemJDBCRepository.add(item);
        Item received = StockItemJDBCRepository.findLastAdded();
        assertEquals(item.getTitle(), received.getTitle());
        assertEquals(item.getCurrency(), received.getCurrency());
    }

    @Test
    public void getAllTest() {
        Item item1 = new Item("test_name", 100, "test_url", "USD");
        Item item2 = new Item("test_name", 100, "test_url", "USD");
        Item item3 = new Item("test_name", 100, "test_url", "USD");
        StockItemJDBCRepository.add(item1);
        StockItemJDBCRepository.add(item2);
        StockItemJDBCRepository.add(item3);
        List<Item> items = StockItemJDBCRepository.getAll();
        assertEquals(3, items.size());
    }

    @Test
    public void getItemsWithPriceFromToTest() {
        Item item1 = new Item("test_name", 100, "test_url", "USD");
        Item item2 = new Item("test_name", 120, "test_url", "USD");
        Item item3 = new Item("test_name", 160, "test_url", "USD");
        Item item4 = new Item("test_name", 170, "test_url", "USD");
        Item item5 = new Item("test_name", 200, "test_url", "USD");
        Item item6 = new Item("test_name", 210, "test_url", "USD");
        StockItemJDBCRepository.add(item1);
        StockItemJDBCRepository.add(item2);
        StockItemJDBCRepository.add(item3);
        StockItemJDBCRepository.add(item4);
        StockItemJDBCRepository.add(item5);
        StockItemJDBCRepository.add(item6);
        List<Item> items = StockItemJDBCRepository.getItemsWithPriceFromTo(120, 170);
        assertNotNull(items);
        assertTrue(items
                .stream()
                .allMatch(i -> (i.getPrice() >= 120 && i.getPrice() <= 170)));

    }

    @Test
    public void findItemByIdTest() {
        Item item1 = new Item("1", 100, "test_url", "USD");
        Item item2 = new Item("2", 120, "test_url", "USD");
        StockItemJDBCRepository.add(item1);
        StockItemJDBCRepository.add(item2);
        Item itemFromRepo1 = StockItemJDBCRepository.findItemById(item1.getId());
        Item itemFromRepo2 = StockItemJDBCRepository.findItemById(item2.getId());
        assertEquals(item1.getTitle(), itemFromRepo1.getTitle());
        assertEquals(item2.getTitle(), itemFromRepo2.getTitle());
    }

    @Test
    void deleteByIdTest() {
        Item item = new Item("1", 100, "test_url", "USD");
        StockItemJDBCRepository.add(item);
        int generatedId = item.getId();
        assertNotNull(StockItemJDBCRepository.findItemById(generatedId));
        StockItemJDBCRepository.deleteById(generatedId);
        assertNull(StockItemJDBCRepository.findItemById(generatedId));

    }

    @Test
    public void updateByIdTest() {
        Item item = new Item("title", 150, "image_url");
        StockItemJDBCRepository.add(item);
        int generatedId = item.getId();
        ItemDTO itemDTO = new ItemDTO("changed", 100, "changed_image_url");
        StockItemJDBCRepository.updateById(item.getId(), itemDTO);
        Item itemaAfterUpdate = StockItemJDBCRepository.findItemById(generatedId);
        assertNotEquals(item, itemaAfterUpdate);
        assertEquals(itemaAfterUpdate.getTitle(),itemDTO.getTitle());
        assertEquals(itemaAfterUpdate.getPrice(),itemDTO.getPrice());
        assertEquals(itemaAfterUpdate.getImage_url(),itemDTO.getImage_url());

    }
}
