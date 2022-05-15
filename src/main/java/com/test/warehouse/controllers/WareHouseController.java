package com.test.warehouse.controllers;

import com.test.warehouse.dto.ReportDTO;
import com.test.warehouse.exception.GlobalException;
import com.test.warehouse.model.Product;
import com.test.warehouse.model.Warehouse;
import com.test.warehouse.service.WarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class WareHouseController {

    private static final Logger LOG = LoggerFactory.getLogger(WareHouseController.class);
    private final WarehouseService service;

    public WareHouseController(WarehouseService service) {
        this.service = service;
    }

    @PostMapping("/warehouse/add")
    public HttpStatus addWarehouse(@Valid @RequestBody Warehouse warehouse) {
        LOG.info("Adding warehouse: {}", warehouse);
        service.addWarehouse(warehouse);
        return HttpStatus.OK;
    }

    @PostMapping("product/{warehouseId}/add")
    public HttpStatus addProduct(@Valid @RequestBody Product product,
                                 @PathVariable(value = "warehouseId") Long warehouseId) throws GlobalException {
        LOG.info("Adding product: {}, with warehouseId {}", product, warehouseId);
        service.addProduct(product, warehouseId);
        return HttpStatus.OK;
    }

    @GetMapping("/report")
    public List<ReportDTO> getReport() {
        List<ReportDTO> report = service.getReport();
        LOG.info("Report: {}", report);
        return report;
    }

}
