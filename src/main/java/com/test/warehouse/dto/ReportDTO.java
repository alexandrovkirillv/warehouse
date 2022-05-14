package com.test.warehouse.dto;

import lombok.Data;

@Data
public class ReportDTO {
    private final String warehouseName;
    private final Long totalRangeOfProduct;
    private final Long totalProductQuantity;
    private final Long totalProductValueByPurchasePrice;
    private final Long totalProductValueBySalePrice;
    private final Long totalProductValueByPurchasePriceWithVAT;
    private final Long totalProductValueBySalePriceWithVAT;
}
