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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teammacc.catalog.data.vo.CategoryVO;
import com.teammacc.catalog.service.CategoryService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/category")
public class CategoryController {
	
	private final CategoryService categoryService;
	private final PagedResourcesAssembler<CategoryVO> assembler;
	
	@Autowired
	public CategoryController(CategoryService categoryService, PagedResourcesAssembler<CategoryVO> assembler) {
		this.categoryService = categoryService;
		this.assembler = assembler;
	}
	
	
	@GetMapping(value = "/{id}", produces = {"application/json","application/xml","application/x-yaml"})
	public CategoryVO findById(@PathVariable("id") Long id) {
		
		CategoryVO categoryVO = categoryService.findById(id);
		categoryVO.add(linkTo(methodOn(CategoryController.class).findById(id)).withSelfRel());
		return categoryVO;
	}
	
	
	@PostMapping(produces = {"application/json","application/xml","application/x-yaml"},
			consumes = {"application/json","application/xml","application/x-yaml"})
	public CategoryVO create(@RequestBody CategoryVO categoryVO) {
		
		CategoryVO  catVO = categoryService.create(categoryVO);
		catVO.add(linkTo(methodOn(CategoryController.class).findById(catVO.getId())).withSelfRel());
		
		return catVO;
	}
	
	@PutMapping(produces = {"application/json","application/xml","application/x-yaml"},
			consumes = {"application/json","application/xml","application/x-yaml"})
	public CategoryVO update(@RequestBody CategoryVO categoryVO) {
		
		CategoryVO catVO = categoryService.update(categoryVO);
		catVO.add(linkTo(methodOn(CategoryController.class).findById(categoryVO.getId())).withSelfRel());
		
		return catVO;
		
	}
	
	@GetMapping(produces = {"application/json","application/xml","application/x-yaml"})
	public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection,"name"));
				
		Page<CategoryVO> categories = categoryService.findAll(pageable);
		categories.stream()
		.forEach(p -> p.add(linkTo(methodOn(ProductController.class).findById(p.getId())).withSelfRel()));
		
		PagedModel<EntityModel<CategoryVO>> pagedModel = assembler.toModel(categories);
		
		return new ResponseEntity<>(pagedModel, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id){
		
		categoryService.delete(id);
		
		return ResponseEntity.ok().build();
	}

}
