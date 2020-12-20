package com.teammacc.catalog.data.vo;

import java.io.Serializable;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.teammacc.catalog.entity.Catalog;

@JsonPropertyOrder({"id","name"})
public class CatalogVO extends RepresentationModel<CatalogVO> implements Serializable{

	private static final long serialVersionUID = 5098670473577699777L;
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("name")
	private String name;
	
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

	public static CatalogVO create(Catalog catalog) {
		
		return new ModelMapper().map(catalog, CatalogVO.class);		
	}
	
}
