package com.example.demo.faq.repository;

import com.example.demo.common.domain.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Integer> {



}
