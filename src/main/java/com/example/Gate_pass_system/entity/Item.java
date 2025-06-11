package com.example.Gate_pass_system.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    private GatePassRequest request;

    // Item.java - Add this to your existing Item class
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemImage> images = new ArrayList<>();

    private String description;
    private Integer quantity;

    public List<ItemImage> getImages() {
        return images;
    }

    public void setImages(List<ItemImage> images) {
        this.images = images;
    }

    //
    private String itemName;
    private String serialNumber;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
//
//    @Lob
//    @Column(columnDefinition = "LONGBLOB")
//    private byte[] pictureOfTheItem;  // Stores image as binary
// To:
//private String picturePath;  // Stores the file path or identifier
//
//    public String getPicturePath() {
//        return picturePath;
//    }
//
//    public void setPicturePath(String picturePath) {
//        this.picturePath = picturePath;
//    }

    private Boolean returnable;
    private LocalDate dueDate;

    // Getters and Setters

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ItemCategory category;

    public ItemCategory getCategory() {
        return category;
    }

    public void setCategory(ItemCategory category) {
        this.category = category;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public GatePassRequest getRequest() {
        return request;
    }

    public void setRequest(GatePassRequest request) {
        this.request = request;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }



    public Boolean getReturnable() {
        return returnable;
    }

    public void setReturnable(Boolean returnable) {
        this.returnable = returnable;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
