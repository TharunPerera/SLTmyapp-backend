package com.example.Gate_pass_system.services;

import com.example.Gate_pass_system.DTO.ItemCategoryDTO;
import com.example.Gate_pass_system.entity.ItemCategory;
import com.example.Gate_pass_system.repo.ItemCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemCategoryService {

    @Autowired
    private ItemCategoryRepository categoryRepository;

    // Create a new category
    public ItemCategoryDTO createCategory(ItemCategoryDTO dto) {
        if (categoryRepository.findByCategoryName(dto.getCategoryName()).isPresent()) {
            throw new RuntimeException("Category already exists with name: " + dto.getCategoryName());
        }

        ItemCategory category = new ItemCategory();
        category.setCategoryName(dto.getCategoryName());

        ItemCategory savedCategory = categoryRepository.save(category);
        dto.setCategoryId(savedCategory.getCategoryId());
        return dto;
    }

    // Get all categories
    public List<ItemCategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> {
                    ItemCategoryDTO dto = new ItemCategoryDTO();
                    dto.setCategoryId(category.getCategoryId());
                    dto.setCategoryName(category.getCategoryName());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // Get category by ID
    public ItemCategoryDTO getCategoryById(Long categoryId) {
        ItemCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));

        ItemCategoryDTO dto = new ItemCategoryDTO();
        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        return dto;
    }

    // Update a category
    public ItemCategoryDTO updateCategory(Long categoryId, ItemCategoryDTO dto) {
        ItemCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));

        category.setCategoryName(dto.getCategoryName());

        ItemCategory updatedCategory = categoryRepository.save(category);
        dto.setCategoryId(updatedCategory.getCategoryId());
        return dto;
    }

    // Delete a category
    public void deleteCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new RuntimeException("Category not found with ID: " + categoryId);
        }
        categoryRepository.deleteById(categoryId);
    }
}