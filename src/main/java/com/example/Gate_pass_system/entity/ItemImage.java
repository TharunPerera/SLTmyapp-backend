package com.example.Gate_pass_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "item_images")
public class ItemImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    private String imagePath;
    private Integer uploadOrder;

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getUploadOrder() {
        return uploadOrder;
    }

    public void setUploadOrder(Integer uploadOrder) {
        this.uploadOrder = uploadOrder;
    }
}
