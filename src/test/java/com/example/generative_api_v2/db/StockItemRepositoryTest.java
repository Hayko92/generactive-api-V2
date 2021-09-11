package com.example.generative_api_v2.db;

import com.example.generative_api_v2.db.hibernate.GroupHibernateRepository;
import com.example.generative_api_v2.db.hibernate.StockItemHibernateRepository;
import com.example.generative_api_v2.db.jdbc.GroupJDBCRepository;
import com.example.generative_api_v2.db.jdbc.StockItemJDBCRepository;
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
        GroupHibernateRepository.clear();
        StockItemHibernateRepository.clear();
    }

    @Test
    public void addTest() {
        Item item = new Item("test_name", 100, "test_url", "USD");
        StockItemHibernateRepository.save(item);
        Item received = StockItemHibernateRepository.findLastAdded();
        assertEquals(item.getTitle(), received.getTitle());
        assertEquals(item.getCurrency(), received.getCurrency());
    }

    @Test
    public void getAllTest() {
        Item item1 = new Item("test_name", 100, "test_url", "USD");
        Item item2 = new Item("test_name", 100, "test_url", "USD");
        Item item3 = new Item("test_name", 100, "test_url", "USD");
        StockItemHibernateRepository.save(item1);
        StockItemHibernateRepository.save(item2);
        StockItemHibernateRepository.save(item3);
        List<Item> items = StockItemHibernateRepository.getAll();
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
        StockItemHibernateRepository.save(item1);
        StockItemHibernateRepository.save(item2);
        StockItemHibernateRepository.save(item3);
        StockItemHibernateRepository.save(item4);
        StockItemHibernateRepository.save(item5);
        StockItemHibernateRepository.save(item6);
        List<Item> items = StockItemHibernateRepository.getItemsWithPriceFromTo(120, 170);
        assertNotNull(items);
        assertTrue(items
                .stream()
                .allMatch(i -> (i.getPrice() >= 120 && i.getPrice() <= 170)));

    }

    @Test
    public void findItemByIdTest() {
        Item item1 = new Item("1", 100, "test_url", "USD");
        Item item2 = new Item("2", 120, "test_url", "USD");
        StockItemHibernateRepository.save(item1);
        StockItemHibernateRepository.save(item2);
        Item itemFromRepo1 = StockItemHibernateRepository.getById(item1.getId());
        Item itemFromRepo2 = StockItemHibernateRepository.getById(item2.getId());
        assertEquals(item1.getTitle(), itemFromRepo1.getTitle());
        assertEquals(item2.getTitle(), itemFromRepo2.getTitle());
    }

    @Test
    void deleteByIdTest() {
        Item item = new Item("1", 100, "test_url", "USD");
        StockItemHibernateRepository.save(item);
        int generatedId = item.getId();
        assertNotNull(StockItemHibernateRepository.getById(generatedId));
        StockItemHibernateRepository.deleteById(generatedId);
        assertNull(StockItemHibernateRepository.getById(generatedId));

    }

    @Test
    public void updateByIdTest() {
        Item item = new Item("title", 150, "image_url");
        StockItemHibernateRepository.save(item);
        int generatedId = item.getId();
        Item itemDTO = new Item("changed", 100, "changed_image_url");
        StockItemHibernateRepository.updateById(itemDTO);
        Item itemaAfterUpdate = StockItemHibernateRepository.getById(generatedId);
        assertNotEquals(item, itemaAfterUpdate);
        assertEquals(itemaAfterUpdate.getTitle(),itemDTO.getTitle());
        assertEquals(itemaAfterUpdate.getPrice(),itemDTO.getPrice());
        assertEquals(itemaAfterUpdate.getImage_url(),itemDTO.getImage_url());

    }
}
