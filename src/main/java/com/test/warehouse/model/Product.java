package com.test.warehouse.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer quality;
    private Long purchasePrice;
    @Column(name = "purchase_price_with_vat")
    private Long purchasePriceWithVAT;
    private Long salePrice;
    @Column(name = "sale_price_with_vat")
    private Long salePriceWithVAT;
    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;


    public Product(String name, Integer quality, Long purchasePrice, Long purchasePriceWithVAT, Long salePrice,
                   Long salePriceWithVAT) {
        this.name = name;
        this.quality = quality;
        this.purchasePrice = purchasePrice;
        this.purchasePriceWithVAT = purchasePriceWithVAT;
        this.salePrice = salePrice;
        this.salePriceWithVAT = salePriceWithVAT;
    }


}
