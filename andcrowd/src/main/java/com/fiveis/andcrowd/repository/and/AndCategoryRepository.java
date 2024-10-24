package com.fiveis.andcrowd.repository.and;

import com.fiveis.andcrowd.entity.and.AndCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AndCategoryRepository extends JpaRepository<AndCategory, Integer> {

    List<AndCategory> findAllByIsDeletedFalse();
}
