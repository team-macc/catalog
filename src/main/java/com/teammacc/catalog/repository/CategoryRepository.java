package com.teammacc.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teammacc.catalog.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
