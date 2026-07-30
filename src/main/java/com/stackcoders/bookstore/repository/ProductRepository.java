package com.stackcoders.bookstore.repository;

import com.stackcoders.bookstore.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @EntityGraph(attributePaths = {"category", "images"})
    List<Product> findAll();

    @EntityGraph(attributePaths = {"category", "images"})
    List<Product> findByCategory_CategoryId(Long categoryId);

    @EntityGraph(attributePaths = {"category", "images"})
    @Query("""
            select distinct p from Product p
            join p.category c
            left join p.images i
            where lower(p.name) like lower(concat('%', :keyword, '%'))
               or lower(p.author) like lower(concat('%', :keyword, '%'))
               or lower(c.categoryName) like lower(concat('%', :keyword, '%'))
            """)
    List<Product> search(@Param("keyword") String keyword);
}
