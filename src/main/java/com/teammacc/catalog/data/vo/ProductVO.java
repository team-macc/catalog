package com.teammacc.catalog.data.vo;

import java.io.Serializable;


import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.teammacc.catalog.entity.Category;
import com.teammacc.catalog.entity.Product;


@JsonPropertyOrder({"id","name","description","price","category", "imageUrl"})
public class ProductVO extends RepresentationModel<ProductVO> implements Serializable{

	private static final long serialVersionUID = 3267976156500540485L;

	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("price")
	private Double price;
	
	@JsonProperty("category")
	private Category category;
	
	@JsonProperty("imageUrl")
	private String imageUrl;
	
	
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public static ProductVO create(Product product) {
		
		return new ModelMapper().map(product, ProductVO.class);		
	}
	

}