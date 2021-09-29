package com.example.generative_api_v2.db;

import com.example.generative_api_v2.conf.ApplicationContext;
import com.example.generative_api_v2.db.hibernate.GroupHibernateRepository;
import com.example.generative_api_v2.db.hibernate.StockItemHibernateRepository;
import com.example.generative_api_v2.dto.ItemDTO;
import com.example.generative_api_v2.model.Item;
import com.example.generative_api_v2.service.StockItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class StockItemRepositoryTest {

    private StockItemServiceImpl itemService;
    private StockItemHibernateRepository stockItemHibernateRepository;

    @BeforeEach
    protected void clearItemAndGroupTables() {
        GroupHibernateRepository groupHibernateRepository = ApplicationContext.context.getBean("groupHibernateRepository", GroupHibernateRepository.class);
        groupHibernateRepository.clear();
        StockItemHibernateRepository.clear();
        itemService = ApplicationContext.context.getBean("stockItemServiceImpl", StockItemServiceImpl.class);
        stockItemHibernateRepository = ApplicationContext.context.getBean("stockItemHibernateRepository", StockItemHibernateRepository.class);
    }

    @Test
    public void addTest() {
        ItemDTO item = new ItemDTO("test_name", 100, "test_url", "USD");
        itemService.save(item);
        Item received = stockItemHibernateRepository.findLastAdded();
        assertEquals(item.getTitle(), received.getTitle());
        assertEquals(item.getCurrency(), received.getCurrency());
    }

    @Test
    public void getAllTest() {
        ItemDTO item1 = new ItemDTO("test_name", 100, "test_url", "USD");
        ItemDTO item2 = new ItemDTO("test_name", 100, "test_url", "USD");
        ItemDTO item3 = new ItemDTO("test_name", 100, "test_url", "USD");
        itemService.save(item1);
        itemService.save(item2);
        itemService.save(item3);
        List<Item> items = itemService.getAll();
        assertEquals(3, items.size());
    }

    @Test
    public void getItemsWithPriceFromToTest() {
        ItemDTO item1 = new ItemDTO("test_name", 100, "test_url", "USD");
        ItemDTO item2 = new ItemDTO("test_name", 120, "test_url", "USD");
        ItemDTO item3 = new ItemDTO("test_name", 160, "test_url", "USD");
        ItemDTO item4 = new ItemDTO("test_name", 170, "test_url", "USD");
        ItemDTO item5 = new ItemDTO("test_name", 200, "test_url", "USD");
        ItemDTO item6 = new ItemDTO("test_name", 210, "test_url", "USD");
        itemService.save(item1);
        itemService.save(item2);
        itemService.save(item3);
        itemService.save(item4);
        itemService.save(item5);
        itemService.save(item6);
        List<Item> items = itemService.getItemsWithPriceFromTo(120, 170);
        assertNotNull(items);
        assertTrue(items
                .stream()
                .allMatch(i -> (i.getPrice() >= 120 && i.getPrice() <= 170)));

    }

    @Test
    public void findItemByIdTest() {
        ItemDTO itemDTO1 = new ItemDTO("1", 100, "test_url", "USD");
        ItemDTO itemDTO2 = new ItemDTO("2", 120, "test_url", "USD");
        Item item1 = itemService.save(itemDTO1);
        Item item2 = itemService.save(itemDTO2);
        Item itemFromRepo1 = itemService.getById(item1.getId());
        Item itemFromRepo2 = itemService.getById(item2.getId());
        assertEquals(item1.getTitle(), itemFromRepo1.getTitle());
        assertEquals(item2.getTitle(), itemFromRepo2.getTitle());
    }

    @Test
    void deleteByIdTest() {
        ItemDTO item = new ItemDTO("1", 100, "test_url", "USD");
       Item generatedItem = itemService.save(item);
        int generatedId = generatedItem.getId();
        assertNotNull(itemService.getById(generatedId));
        itemService.deleteById(generatedId);
        assertNull(itemService.getById(generatedId));

    }

    @Test
    public void updateByIdTest() {
        ItemDTO item = new ItemDTO("title", 150, "image_url");
        Item savedItem =   itemService.save(item);
        int generatedId = savedItem.getId();
        ItemDTO itemDTO = new ItemDTO("changed", 100, "changed_image_url");
        itemDTO.setId(generatedId);
        itemService.updateById(generatedId, itemDTO);
        Item itemaAfterUpdate = itemService.getById(generatedId);
        assertNotEquals(item, itemaAfterUpdate);
        assertEquals(itemaAfterUpdate.getTitle(), itemDTO.getTitle());
        assertEquals(itemaAfterUpdate.getPrice(), itemDTO.getPrice());
        assertEquals(itemaAfterUpdate.getImage_url(), itemDTO.getImage_url());

    }
}
