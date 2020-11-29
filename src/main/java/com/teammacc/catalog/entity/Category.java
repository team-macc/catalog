package com.teammacc.catalog.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;

import com.sun.istack.NotNull;
import com.teammacc.catalog.data.vo.CategoryVO;

@Entity
@Table(name = "category")
public class Category implements Serializable{
	
	private static final long serialVersionUID = 4323803670674810214L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false, length = 255)
	private String name;
	
	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@NotNull
	private List<Product> products;
	
	@ManyToOne
	@JoinColumn(name = "catalog_id")
	@NotNull
	private Catalog catalog;

	public Catalog getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
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

//	public List<Product> getProducts() {
//		return products;
//	}
//
//	public void setProducts(List<Product> products) {
//		this.products = products;
//	}

	public static Category create(CategoryVO categoryVO){
		
		return new ModelMapper().map(categoryVO, Category.class);
		
	}
}
