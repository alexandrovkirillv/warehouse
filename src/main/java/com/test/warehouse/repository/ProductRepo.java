package com.test.warehouse.repository;

import com.test.warehouse.model.Product;
import com.test.warehouse.dto.ReportDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query(value = "select new com.test.warehouse.dto.ReportDTO(" +
            "w.name, " +
            "count (p) , " +
            "sum(p.quantity)," +
            "sum(p.purchasePrice)," +
            "sum(p.salePrice)," +
            "sum(p.purchasePriceWithVAT)," +
            "sum(p.salePriceWithVAT)) " +
            "from Product p " +
            "left join Warehouse w on w.id = p.warehouse.id " +
            "group by w.name")
    List<ReportDTO> getReport();

    @Query(value = "select * from product where name like :name", nativeQuery = true)
    List<Product> findByNameLike(String name);

}
