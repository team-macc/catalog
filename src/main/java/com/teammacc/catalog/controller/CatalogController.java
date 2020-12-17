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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teammacc.catalog.data.vo.CatalogVO;
import com.teammacc.catalog.data.vo.CategoryVO;
import com.teammacc.catalog.data.vo.ProductVO;
import com.teammacc.catalog.service.CatalogService;
import com.teammacc.catalog.service.CategoryService;
import com.teammacc.catalog.service.ProductService;

@RestController
@RequestMapping("/")
public class CatalogController {
	
	private final CatalogService catalogService;
	private final PagedResourcesAssembler<CatalogVO> assembler;
	private final PagedResourcesAssembler<ProductVO> productAssembler;
	private final CategoryService categoryService;
    private final ProductService productService;
	
	@Autowired
	public CatalogController(CatalogService catalogService, PagedResourcesAssembler<CatalogVO> assembler, CategoryService categoryService, ProductService productService, PagedResourcesAssembler<ProductVO> productAssembler) {
		this.catalogService = catalogService;
		this.assembler = assembler;
		this.categoryService = categoryService;
		this.productService = productService;
		this.productAssembler = productAssembler;
	}
	
	@GetMapping(value = "/{id}", produces = {"application/json","application/xml","application/x-yaml"})
	public CatalogVO findById(@PathVariable("id") Long id) {
		
		CatalogVO catalogVO = catalogService.findById(id);
		catalogVO.add(linkTo(methodOn(CategoryController.class).findById(id)).withSelfRel());
		return catalogVO;
	}
	
	@GetMapping(value = "/{id}/category/{categoryId}")
	public CategoryVO findCategoryByCatalog(@PathVariable("id") Long id, @PathVariable("categoryId") Long categoryId) {
		
		CatalogVO catalogVO = catalogService.findById(id);
		CategoryVO categoryVO = categoryService.findById(categoryId);
		
		if(categoryVO.getCatalog().getId() == catalogVO.getId()) {
			return categoryVO;
		}
		return null;
	}
	
	@GetMapping(value = "/{catalogId}/category/{categoryId}/product", produces = {"application/json","application/xml","application/x-yaml"})
	public ResponseEntity<?> findAllByCatalogAndCategory(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction, @PathVariable("catalogId") Long catalogId, @PathVariable("categoryId") Long categoryId) {
		
		CatalogVO catalogVO = catalogService.findById(catalogId);
		CategoryVO categoryVO = categoryService.findById(categoryId);
		
		if(categoryVO.getCatalog().getId() == catalogVO.getId()) {
			
			var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
			Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection,"name"));
			Page<ProductVO> products = productService.findAllByCategoryId(pageable,categoryId);
			products.stream()
			.forEach(p -> p.add(linkTo(methodOn(ProductController.class).findById(p.getId())).withSelfRel()));
			
			PagedModel<EntityModel<ProductVO>> pagedModel = productAssembler.toModel(products);
			
			return new ResponseEntity<>(pagedModel, HttpStatus.OK);
			
		}
		
		return null;
		
	}
	
	
	@PostMapping(produces = {"application/json","application/xml","application/x-yaml"},
			consumes = {"application/json","application/xml","application/x-yaml"})
	public CatalogVO create(@RequestBody CatalogVO catalogVO) {
		
		
		CatalogVO  catVO = catalogService.create(catalogVO);
		catVO.add(linkTo(methodOn(CatalogController.class).findById(catVO.getId())).withSelfRel());
		
		return catVO;
	
	}
	
	@GetMapping(produces = {"application/json","application/xml","application/x-yaml"})
	public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection,"name"));
				
		Page<CatalogVO> catalogs = catalogService.findAll(pageable);
		catalogs.stream()
		.forEach(p -> p.add(linkTo(methodOn(CatalogController.class).findById(p.getId())).withSelfRel()));
		
		PagedModel<EntityModel<CatalogVO>> pagedModel = assembler.toModel(catalogs);
		
		return new ResponseEntity<>(pagedModel, HttpStatus.OK);
	}
	
	

}
