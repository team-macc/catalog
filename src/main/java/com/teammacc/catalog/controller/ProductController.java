package com.teammacc.catalog.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teammacc.catalog.data.vo.ProductVO;
import com.teammacc.catalog.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	private final ProductService productService;
	private final PagedResourcesAssembler<ProductVO> assembler;
	
	@Autowired
	public ProductController(ProductService productService, PagedResourcesAssembler<ProductVO> assembler) {
		this.productService = productService;
		this.assembler = assembler;
	}
	
	@GetMapping(value = "/{id}", produces = {"application/json","application/xml","application/x-yaml"})
	public ProductVO findById(@PathVariable("id") Long id) {
		
		ProductVO productVO = productService.findById(id);
		productVO.add(linkTo(methodOn(ProductController.class).findById(id)).withSelfRel());
		
		return productVO;
	}
	
	@PostMapping(produces = {"application/json","application/xml","application/x-yaml"},
			consumes = {"application/json","application/xml","application/x-yaml"})
	public ProductVO create(@RequestBody ProductVO productVO) {
		
		
		ProductVO prodVO = productService.create(productVO);
		prodVO.add(linkTo(methodOn(ProductController.class).findById(prodVO.getId())).withSelfRel());
		
		return prodVO;
	}
	
	
	@GetMapping(produces = {"application/json","application/xml","application/x-yaml"})
	public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection,"name"));
				
		Page<ProductVO> products = productService.findAll(pageable);
		products.stream()
		.forEach(p -> p.add(linkTo(methodOn(ProductController.class).findById(p.getId())).withSelfRel()));
		
		PagedModel<EntityModel<ProductVO>> pagedModel = assembler.toModel(products);
		
		return new ResponseEntity<>(pagedModel, HttpStatus.OK);
	}
	
	@PutMapping(produces = {"application/json","application/xml","application/x-yaml"},
			consumes = {"application/json","application/xml","application/x-yaml"})
	public ProductVO update(@RequestBody ProductVO productVO) {
		
		ProductVO proVO = productService.update(productVO);
		proVO.add(linkTo(methodOn(ProductController.class).findById(productVO.getId())).withSelfRel());
		
		return proVO;
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id){
		
		productService.delete(id);
		
		return ResponseEntity.ok().build();
	}
	
}
