package com.example.generative_api_v2;

import com.example.generative_api_v2.conf.ApplicationContext;
import com.example.generative_api_v2.db.hibernate.GroupHibernateRepository;
import com.example.generative_api_v2.db.hibernate.StockItemHibernateRepository;
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
//        groupHibernateRepository.clear();
//        StockItemHibernateRepository.clear();
        itemService = ApplicationContext.context.getBean("stockItemServiceImpl", StockItemServiceImpl.class);
        stockItemHibernateRepository = ApplicationContext.context.getBean("stockItemHibernateRepository", StockItemHibernateRepository.class);
    }

    @Test
    public void addTest() {
        Item item = new Item("test_name", 100, "test_url", "USD");
        itemService.save(item);
        Item received = stockItemHibernateRepository.findLastAdded();
        assertEquals(item.getTitle(), received.getTitle());
        assertEquals(item.getCurrency(), received.getCurrency());
    }

    @Test
    public void getAllTest() {
        Item item1 = new Item("test_name", 100, "test_url", "USD");
        Item item2 = new Item("test_name", 100, "test_url", "USD");
        Item item3 = new Item("test_name", 100, "test_url", "USD");
        itemService.save(item1);
        itemService.save(item2);
        itemService.save(item3);
        List<Item> items = itemService.getAll();
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
        Item item1 = new Item("1", 100, "test_url", "USD");
        Item item2 = new Item("2", 120, "test_url", "USD");
        itemService.save(item1);
        itemService.save(item2);
        Item itemFromRepo1 = itemService.getById(item1.getId());
        Item itemFromRepo2 = itemService.getById(item2.getId());
        assertEquals(item1.getTitle(), itemFromRepo1.getTitle());
        assertEquals(item2.getTitle(), itemFromRepo2.getTitle());
    }

    @Test
    void deleteByIdTest() {
        Item item = new Item("1", 100, "test_url", "USD");
        itemService.save(item);
        int generatedId = item.getId();
        assertNotNull(itemService.getById(generatedId));
        itemService.deleteById(generatedId);
        assertNull(itemService.getById(generatedId));

    }

    @Test
    public void updateByIdTest() {
        Item item = new Item("title", 150, "image_url");
        itemService.save(item);
        int generatedId = item.getId();
        Item itemDTO = new Item("changed", 100, "changed_image_url");
        itemDTO.setId(generatedId);
        itemService.updateById(itemDTO);
        Item itemaAfterUpdate = itemService.getById(generatedId);
        assertNotEquals(item, itemaAfterUpdate);
        assertEquals(itemaAfterUpdate.getTitle(), itemDTO.getTitle());
        assertEquals(itemaAfterUpdate.getPrice(), itemDTO.getPrice());
        assertEquals(itemaAfterUpdate.getImage_url(), itemDTO.getImage_url());

    }
}
