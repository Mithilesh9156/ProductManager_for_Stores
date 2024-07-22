package com.boostmytool.BestStore.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.boostmytool.BestStore.models.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

	
}
