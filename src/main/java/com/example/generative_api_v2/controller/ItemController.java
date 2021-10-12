package com.example.generative_api_v2.controller;

import com.example.generative_api_v2.dto.GroupDTO;
import com.example.generative_api_v2.dto.ItemDTO;
import com.example.generative_api_v2.service.GroupService;
import com.example.generative_api_v2.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final GroupService groupService;

    @Autowired
    public ItemController(ItemService itemService, GroupService groupService) {
        this.itemService = itemService;
        this.groupService = groupService;
    }

    @PostMapping
    public ItemDTO add(@RequestBody @Valid ItemDTO sendedItem) {
    return itemService.save(sendedItem);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        itemService.deleteById(id);
    }

    @GetMapping("/{id}")
    public ItemDTO getById(@PathVariable int id) {
        return itemService.getById(id);
    }

    @PutMapping("/{id}")
    public ItemDTO updateByyId(@PathVariable int id, @RequestBody ItemDTO updatedItem) {
        return itemService.updateById(id, updatedItem);
    }

    @GetMapping("/search")
    public List<ItemDTO> searchWithMinAndMaxPrices(@RequestParam int priceFrom, @RequestParam int priceTo) {
        return itemService.getItemsWithPriceFromTo(priceFrom, priceTo);
    }

    @GetMapping()
    public List<ItemDTO> getAll(@RequestParam int offset,
                             @RequestParam int limit,
                             @RequestParam(required = false) String sortBy) {
        return itemService.getAll(offset,limit, sortBy);
    }
    @PutMapping("/{id}/{parentId}")
    public ItemDTO updateByyId(@PathVariable int id, @PathVariable int parentId) {
        ItemDTO itemDTO = itemService.getById(id);
        GroupDTO groupDTO = groupService.getById(parentId);
        itemDTO.setParent(groupDTO);
        return itemService.save(itemDTO);
    }

}
