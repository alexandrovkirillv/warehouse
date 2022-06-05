package com.test.warehouse.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductResponseDTO {
    private List<ProductDTO> products;
}
