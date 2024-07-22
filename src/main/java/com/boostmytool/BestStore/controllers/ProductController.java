package com.boostmytool.BestStore.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.naming.Binding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boostmytool.BestStore.models.Product;
import com.boostmytool.BestStore.models.ProductDto;
import com.boostmytool.BestStore.services.ProductRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	ProductRepository repo;
	
	@GetMapping({"","/"})
	public String showProductList(Model model) {	
		List<Product> products = repo.findAll();
		model.addAttribute("products", products);
		return "products/index.html";
	}
	
	
	@GetMapping("/create")
	public String showCreatePage(Model model) {
		
		ProductDto productDto = new ProductDto();
		model.addAttribute("productDto", productDto);

		return"products/CreateProducts.html";
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/create")
	public String createproduct(@Valid @ModelAttribute ProductDto productdto, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			return "products/CreateProducts";
		}
		
		Product product = new Product();
		product.setName(productdto.getName());
		product.setBrand(productdto.getBrand());
		product.setCategory(productdto.getCategory());
		product.setPrice(productdto.getPrice());
		product.setDescription(productdto.getDescription());
		
		Product pro = repo.save(product);
		
		return "redirect:/products";
	}
	
	@GetMapping("/edit")
	public String showEditPage(@RequestParam int id,Model model) {
		
		try {
			Product product = repo.findById(id).get();
			model.addAttribute("product", product);
			
			ProductDto productDto = new ProductDto();
			productDto.setName(product.getName());
			productDto.setBrand(product.getBrand());
			productDto.setCategory(product.getCategory());
			productDto.setPrice(product.getPrice());
			productDto.setDescription(product.getDescription());
			
		model.addAttribute("productDto", productDto);	
		}
		catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return "redirect:/products";
		}	
		return "products/EditProduct";
	}
	
	
	
	@PostMapping("/edit")
	public String updateProduct(Model model,@RequestParam int id, @Valid @ModelAttribute ProductDto productDto, BindingResult result) {
		
		try {
			Product product = repo.findById(id).get();
			model.addAttribute("product", product);
			
			if(result.hasErrors()) {
				return "products/EditProduct";
			}
			

			product.setName(productDto.getName());
			product.setBrand(productDto.getBrand());
			product.setCategory(productDto.getCategory());
			product.setPrice(productDto.getPrice());
			product.setDescription(productDto.getDescription());
			
			repo.save(product);
		}
		catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
		
		return"redirect:/products";
	}
	
	
	@GetMapping("/delete")
	public String deleteProduct(@RequestParam int id) {
		
		try {
			Product product = repo.findById(id).get();
			repo.deleteById(id);
		}
		catch(Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
		
		return "redirect:/products";
	}
	
}




