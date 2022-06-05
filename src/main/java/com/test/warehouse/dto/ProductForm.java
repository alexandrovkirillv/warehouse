package com.test.warehouse.dto;

import com.test.warehouse.model.Product;
import com.test.warehouse.model.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductForm {
    private Long id;
    private String name;
    private Long quantity;
    private Long warehouseId;
    private String description;
    private Long price;

    public static ProductForm transformToProductForm(Product product) {
        return ProductForm.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getSalePrice())
                .quantity(product.getQuantity())
                .warehouseId(product.getWarehouse().getId())
                .build();
    }

    public static Product updateProduct(ProductForm productForm, Product product, Warehouse warehouse) {
        product.setName(productForm.getName());
        product.setDescription(productForm.getDescription());
        product.setWarehouse(warehouse);
        product.setSalePrice(productForm.getPrice());
        product.setQuantity(productForm.getQuantity());
        return product;
    }
}
