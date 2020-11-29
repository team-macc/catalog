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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;

import com.sun.istack.NotNull;
import com.teammacc.catalog.data.vo.CatalogVO;


@Entity
@Table(name = "catalog")
public class Catalog implements Serializable {

	private static final long serialVersionUID = -5631105000295839708L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false, length = 255)
	private String name;
	
	@OneToMany(mappedBy = "catalog", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Category> categories;

//	public List<Category> getCategories() {
//		return categories;
//	}
//
//	public void setCategories(List<Category> categories) {
//		this.categories = categories;
//	}

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
	
	public static Catalog create(CatalogVO catalogVO){
		
		return new ModelMapper().map(catalogVO, Catalog.class);
		
	}
}
