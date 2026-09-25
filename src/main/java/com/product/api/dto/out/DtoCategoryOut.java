package com.product.api.dto.out;

import com.product.api.entity.Category;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Id;

public class DtoCategoryOut {
    @Id
    @JsonProperty("category_id")
    private Integer categoryId;

    @JsonProperty("category")
    private String category;

    @JsonProperty("tag")
    private String tag;

    @JsonProperty("status")
    private Integer status;

    public DtoCategoryOut(Integer categoryId, String category, String tag, Integer status) {
        super();
        this.categoryId = categoryId;
        this.category = category;
        this.tag = tag;
        this.status = status;
    }

    public DtoCategoryOut(Category category) {
        super();
        this.categoryId = category.getCategoryId();
        this.category = category.getCategory();
        this.tag = category.getTag();
        this.status = category.getStatus();
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
