package com.teammacc.catalog.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;

import com.sun.istack.NotNull;
import com.teammacc.catalog.data.vo.ProductVO;

@Entity
@Table(name = "product")
public class Product implements Serializable {

	private static final long serialVersionUID = 2362215899014013163L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false, length = 255)
	private String name;
	
	@Column(name = "description", nullable = false, length = 255)
	private String description;
	
	@Column(name = "price", nullable = false, length = 10)
	private Double price;
	
	@Column(name = "imageUrl", nullable = false, length = 255)
	private String imageUrl;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	@NotNull
	private Category category;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

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
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public static Product create(ProductVO productVO){
		
		return new ModelMapper().map(productVO, Product.class);
		
	}


}
