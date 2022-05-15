package com.test.warehouse.service;

import com.test.warehouse.dto.ReportDTO;
import com.test.warehouse.exception.GlobalException;
import com.test.warehouse.model.Product;
import com.test.warehouse.model.Warehouse;
import com.test.warehouse.repository.ProductRepo;
import com.test.warehouse.repository.WarehouseRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service using for warehouse logic, which save data and build reports
 *
 * @author Kirill Alexandrov
 */

@Service
@Transactional
public class WarehouseService {

    private final WarehouseRepo warehouseRepo;
    private final ProductRepo productRepo;

    public WarehouseService(WarehouseRepo warehouseRepo, ProductRepo productRepo) {
        this.warehouseRepo = warehouseRepo;
        this.productRepo = productRepo;
    }

    public void addWarehouse(Warehouse warehouse) {
        warehouseRepo.save(warehouse);
    }

    public void addProduct(Product product, Long warehouseId) throws GlobalException {

        Warehouse warehouse = warehouseRepo.findById(warehouseId).orElseThrow(
                () -> new GlobalException("WarehouseId: invalid parameter"));

        product.setWarehouse(warehouse);
        productRepo.save(product);
    }

    public List<ReportDTO> getReport() {
        return productRepo.getReport();
    }
}
