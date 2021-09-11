package com.example.generative_api_v2.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Table(name = "configuration")

public class Configuration {

    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "conf_id_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private int id;

    @Enumerated(EnumType.STRING)
    private Resolution resolution;

    @OneToOne(mappedBy = "configuration")
    private Item item;

    public Configuration() {
    }

    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    public Resolution getResolution() {
        return resolution;
    }

    public Configuration(Resolution resolution) {
        this.resolution = resolution;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
     @Transient
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
