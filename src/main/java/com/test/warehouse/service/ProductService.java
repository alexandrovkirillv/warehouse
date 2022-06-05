package com.test.warehouse.service;

import com.test.warehouse.controllers.ProductController;
import com.test.warehouse.dto.ProductDTO;
import com.test.warehouse.dto.ProductForm;
import com.test.warehouse.dto.ProductResponseDTO;
import com.test.warehouse.exception.GlobalException;
import com.test.warehouse.model.Product;
import com.test.warehouse.model.Warehouse;
import com.test.warehouse.repository.ProductRepo;
import com.test.warehouse.repository.WarehouseRepo;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);
    private final WarehouseRepo warehouseRepo;
    private final ProductRepo productRepo;

    public ProductResponseDTO getProductById(Long id) throws GlobalException {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new GlobalException("ProductId: invalid parameter"));
        ProductDTO productDTO = ProductDTO.transformProductToDTO(product);
        return ProductResponseDTO.builder()
                .products(Collections.singletonList(productDTO))
                .build();
    }

    public ProductForm getProductDTOById(Long id) throws GlobalException {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new GlobalException("ProductId: invalid parameter"));
        return ProductForm.transformToProductForm(product);
    }

    public ProductResponseDTO getProductByName(String name) {
        List<Product> products = productRepo.findByNameLike("%" + name + "%");
        if (products == null) {
            return ProductResponseDTO.builder()
                    .build();
        }
        String nameOfProducts = products.get(0).getName();
        long allQuantity = ProductDTO.getAllQuantity(products, nameOfProducts);
        List<ProductDTO> productDTOS = products.stream()
                .map(ProductDTO::transformProductToDTO)
                .peek(productDTO -> productDTO.setQuantityAll(allQuantity))
                .collect(Collectors.toList());
        LOG.info("products: {}", productDTOS);
        return ProductResponseDTO.builder()
                .products(productDTOS)
                .build();
    }

    public void updateProduct(ProductForm productForm) throws GlobalException {
        Product product = productRepo.findById(productForm.getId())
                .orElseThrow(() -> new GlobalException("ProductId: invalid parameter"));
        Warehouse warehouse = warehouseRepo.findById(productForm.getWarehouseId()).orElseThrow(
                () -> new GlobalException("WarehouseId: invalid parameter"));
        Product updatedProduct = ProductForm.updateProduct(productForm, product, warehouse);
        productRepo.save(updatedProduct);
    }

    public void addProduct(ProductForm productForm) throws GlobalException {
        LOG.info("ProductForm add: {}", productForm);
        Long warehouseId = productForm.getWarehouseId();
        if (warehouseId == null) {
            throw new GlobalException("WarehouseId: invalid parameter");
        }
        Warehouse warehouse = warehouseRepo.findById(warehouseId).orElseThrow(
                () -> new GlobalException("WarehouseId: invalid parameter"));

        Product product = Product.builder()
                .name(productForm.getName())
                .quantity(productForm.getQuantity())
                .description(productForm.getDescription())
                .warehouse(warehouse)
                .salePrice(productForm.getPrice())
                .build();
        product.setWarehouse(warehouse);
        productRepo.save(product);
    }

    public void updateProduct(Product product, Long warehouseId) throws GlobalException {
        Warehouse warehouse = warehouseRepo.findById(warehouseId).orElseThrow(
                () -> new GlobalException("WarehouseId: invalid parameter: " + warehouseId));
        product.setWarehouse(warehouse);
        productRepo.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepo.getById(id);
        LOG.info("Deleted: {}", product);
        productRepo.delete(product);
    }

    public ProductResponseDTO getProducts() {
        List<Product> products = productRepo.findAll();
        LOG.info("get products {}", products);
        Map<String, Long> quantityByName = ProductDTO.getQuantityByName(products);

        List<ProductDTO> productDTOS = products.stream()
                .map(ProductDTO::transformProductToDTO)
                .peek(productDTO -> productDTO.setQuantityAll(quantityByName.get(productDTO.getName())))
                .collect(Collectors.toList());
        return ProductResponseDTO.builder()
                .products(productDTOS)
                .build();
    }
}
