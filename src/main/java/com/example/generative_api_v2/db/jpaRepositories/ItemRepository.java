package com.example.generative_api_v2.db.jpaRepositories;

import com.example.generative_api_v2.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item,Integer> {
    List<Item>  findByPriceBetween(int from, int to);
     List<Item> findAll();

}
