package com.test.warehouse.dto;

import com.test.warehouse.model.Product;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private Long quantity;
    private Long quantityAll;
    private String description;
    private String unit;
    private Long price;
    private String warehouseName;

    public static ProductDTO transformProductToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .quantity(product.getQuantity())
                .description(product.getDescription())
                .price(product.getSalePrice())
                .warehouseName(product.getWarehouse().getName())
                .build();
    }

    public static long getAllQuantity(List<Product> products, String filterName) {
        return products.stream()
                .filter(product -> product.getName().equals(filterName))
                .map(Product::getQuantity)
                .reduce(0L, Long::sum);
    }

    public static Map<String,Long> getQuantityByName(List<Product> products){
        Map<String, Long> nameAllQuantityMap = new HashMap<>();

        for (Product product : products) {
            String name = product.getName();
            nameAllQuantityMap.put(name, getAllQuantity(products, name));
        }
        return nameAllQuantityMap;
    }
}