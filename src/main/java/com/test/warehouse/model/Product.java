package com.test.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@Table(name = "product")
@Builder
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String name;
    @NotNull
    private Long quantity;
    private Long purchasePrice;
    @Column(name = "purchase_price_with_vat")
    private Long purchasePriceWithVAT;
    @NotNull
    private Long salePrice;
    @Column(name = "sale_price_with_vat")
    private Long salePriceWithVAT;
    @ManyToOne
    private Warehouse warehouse;
    @NotNull
    private String description;
}
