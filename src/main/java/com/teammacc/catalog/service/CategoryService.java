package com.teammacc.catalog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.teammacc.catalog.data.vo.CategoryVO;
import com.teammacc.catalog.data.vo.ProductVO;
import com.teammacc.catalog.entity.Category;
import com.teammacc.catalog.entity.Product;
import com.teammacc.catalog.exception.ResourceNotFoundException;
import com.teammacc.catalog.repository.CategoryRepository;

@Service
public class CategoryService {
	
	private final CategoryRepository categoryRepository;

	@Autowired
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	public CategoryVO create(CategoryVO categoryVO) {
		
		CategoryVO categoryVOReturn = CategoryVO.create(categoryRepository.save(Category.create(categoryVO)));
		
		return categoryVOReturn;
	}

	public CategoryVO findById(Long id) {
		
		var entity = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		return CategoryVO.create(entity);
	}
	
	public Page<CategoryVO> findAll(Pageable pageable){
		
		var page = categoryRepository.findAll(pageable);
		
		return page.map(this::convertToCategoryVO);
		
	}
	
	private CategoryVO convertToCategoryVO(Category category) {
		
		return CategoryVO.create(category);
		
	}
	
	public CategoryVO update(CategoryVO categoryVO) {
		
		final Optional<Category> optionalCategory = categoryRepository.findById(categoryVO.getId());
		
		if(!optionalCategory.isPresent()) {
			
			 new ResourceNotFoundException("No records found for this ID");
		}
		
		return CategoryVO.create(categoryRepository.save(Category.create(categoryVO)));
	}
	
	public void delete(Long id) {
		
		var entity = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		categoryRepository.delete(entity);
	}
}
