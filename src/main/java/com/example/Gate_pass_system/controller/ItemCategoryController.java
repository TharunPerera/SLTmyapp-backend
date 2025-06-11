package com.example.Gate_pass_system.controller;

import com.example.Gate_pass_system.DTO.ItemCategoryDTO;
import com.example.Gate_pass_system.entity.ItemCategory;
import com.example.Gate_pass_system.repo.ItemCategoryRepository;
import com.example.Gate_pass_system.services.ItemCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item-categories")
public class ItemCategoryController {

    @Autowired
    private ItemCategoryService itemCategoryService;

    // Create a new category
    @PostMapping("/create")
    public ResponseEntity<ItemCategoryDTO> createCategory(@RequestBody ItemCategoryDTO dto) {
        return ResponseEntity.ok(itemCategoryService.createCategory(dto));
    }

    // Get all categories
    @GetMapping("/all")
    public ResponseEntity<List<ItemCategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(itemCategoryService.getAllCategories());
    }

    // Get category by ID
    @GetMapping("/{id}")
    public ResponseEntity<ItemCategoryDTO> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(itemCategoryService.getCategoryById(id));
    }

    // Update a category
    @PutMapping("/update/{id}")
    public ResponseEntity<ItemCategoryDTO> updateCategory(@PathVariable Long id, @RequestBody ItemCategoryDTO dto) {
        return ResponseEntity.ok(itemCategoryService.updateCategory(id, dto));
    }

    // Delete a category
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        itemCategoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully.");
    }
}