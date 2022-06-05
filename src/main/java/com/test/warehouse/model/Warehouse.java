package com.test.warehouse.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "warehouse")
@Data
@NoArgsConstructor
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;
    @NotNull
    private String name;

    public Warehouse(String name) {
        this.name = name;
    }
}
