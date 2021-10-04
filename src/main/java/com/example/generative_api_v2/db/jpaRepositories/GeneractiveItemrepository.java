package com.example.generative_api_v2.db.jpaRepositories;

import com.example.generative_api_v2.model.Generative;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GeneractiveItemrepository extends CrudRepository<Generative, Integer> {

    List<Generative> findByPriceBetween(int from, int to);

    List<Generative> findAll();

    @Query("select g from Generative  g where g.id=?1")
    Generative findById(int id);

}
