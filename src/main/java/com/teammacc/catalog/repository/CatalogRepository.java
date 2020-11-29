package com.teammacc.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teammacc.catalog.entity.Catalog;


@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long>  {

}
