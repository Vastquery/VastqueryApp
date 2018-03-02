package com.vastquery.www.vastquery.PropertyClasses;

/**
 * Created by aj-ajay on 2/7/18.
 */

public class CategoryDetails {
    String name,catId;
    byte[] image;

    public CategoryDetails(String catId,String name, byte[] image){
        this.catId = catId;
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getCatId() {
        return catId;
    }

    public byte[] getImage() {
        return image;
    }
}
