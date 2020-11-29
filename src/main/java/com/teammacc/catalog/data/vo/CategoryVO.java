package com.teammacc.catalog.data.vo;

import java.io.Serializable;


import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.teammacc.catalog.entity.Catalog;
import com.teammacc.catalog.entity.Category;


@JsonPropertyOrder({"id","name","catalog"})
public class CategoryVO extends RepresentationModel<CategoryVO> implements Serializable{

	private static final long serialVersionUID = 5098670473577699777L;
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("name")
	private String name;
	

	@JsonProperty("catalog")
	private Catalog catalog;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Catalog getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}
	
	public static CategoryVO create(Category category) {
		
		return new ModelMapper().map(category, CategoryVO.class);		
	}
}
