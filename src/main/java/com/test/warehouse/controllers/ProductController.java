package com.test.warehouse.controllers;

import com.test.warehouse.dto.ProductDTO;
import com.test.warehouse.dto.ProductForm;
import com.test.warehouse.dto.ProductResponseDTO;
import com.test.warehouse.exception.GlobalException;
import com.test.warehouse.model.Product;
import com.test.warehouse.service.ProductService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class ProductController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);
    public static final String INDEX = "index";
    private final ProductService service;

    @GetMapping("/product/{name}")
    @ResponseBody
    public ProductResponseDTO getProduct(@PathVariable("name") String name) throws GlobalException {
        return service.getProductByName(name);
    }

    @GetMapping("/product-edit/{id}")
    public String getProduct(@PathVariable("id") Long id, Model model) throws GlobalException {
        ProductForm productForm = service.getProductDTOById(id);
        model.addAttribute("productForm", productForm);
        return "product-edit";
    }

    @PostMapping("/product-edit")
    public String editProduct(@ModelAttribute(value = "productForm") ProductForm productForm) throws GlobalException {
        LOG.info("ALE EDIT");
        service.updateProduct(productForm);
        return INDEX;
    }

    @GetMapping("/product-add")
    public String addProduct(Model model) {
        LOG.info("ALE");
        model.addAttribute("productForm", new ProductForm());
        return "product-add";
    }

    @GetMapping("/product")
    @ResponseBody
    public ProductResponseDTO getProducts() {
        return service.getProducts();
    }

    @PostMapping("/product-add")
    public String addProduct(@Valid @ModelAttribute(value = "productForm") ProductForm productForm) throws GlobalException {
        service.addProduct(productForm);
        return INDEX;
    }

    @DeleteMapping("/product/{id}")
    public String deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
        return INDEX;
    }

    @PutMapping("/product/{warehouseId}")
    public HttpStatus updateProduct(@Valid @RequestBody Product product,
                                    @PathVariable(value = "warehouseId") Long warehouseId) throws GlobalException {
        LOG.info("Updating product: {}, with warehouseId {}", product, warehouseId);
        service.updateProduct(product, warehouseId);
        return HttpStatus.OK;
    }
}
