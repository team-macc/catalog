package com.teammacc.catalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.teammacc.catalog.data.vo.CatalogVO;
import com.teammacc.catalog.entity.Catalog;
import com.teammacc.catalog.exception.ResourceNotFoundException;
import com.teammacc.catalog.repository.CatalogRepository;

@Service
public class CatalogService {

	private final CatalogRepository catalogRepository;

	@Autowired
	public CatalogService(CatalogRepository catalogRepository) {
		this.catalogRepository = catalogRepository;
	}
	
	public CatalogVO create(CatalogVO catalogVO) {
		
		CatalogVO catalogVOReturn = CatalogVO.create(catalogRepository.save(Catalog.create(catalogVO)));
		
		return catalogVOReturn;
	}
	
	public CatalogVO findById(Long id) {
		
		var entity = catalogRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		return CatalogVO.create(entity);
	}
	
	public Page<CatalogVO> findAll(Pageable pageable){
		
		var page = catalogRepository.findAll(pageable);
		
		return page.map(this::convertToCatalogVO);
		
	}
	
	private CatalogVO convertToCatalogVO(Catalog catalog) {
		
		return CatalogVO.create(catalog);
		
	}

	
}
