package com.group04.shop.Domain;

public class ReviewDomain {
    private String Name;
    private String Description;
    private String PicUrl;
    private double Rating;
    private int ItemId;

    public ReviewDomain() {
    }

    public ReviewDomain(String name, String description, String picUrl, double rating, int itemId) {
        this.Name = name;
        this.Description = description;
        this.PicUrl = picUrl;
        this.Rating = rating;
        this.ItemId = itemId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        this.PicUrl = picUrl;
    }

    public double getRating() {
        return Rating;
    }

    public void setRating(double rating) {
        this.Rating = rating;
    }

    public int getItemId() {
        return ItemId;
    }

    public void setItemId(int itemId) {
        this.ItemId = itemId;
    }
}
