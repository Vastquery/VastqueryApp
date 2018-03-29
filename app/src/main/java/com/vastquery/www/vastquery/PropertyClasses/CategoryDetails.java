package com.vastquery.www.vastquery.PropertyClasses;

/**
 * Created by aj-ajay on 2/7/18.
 */

public class CategoryDetails {
    String name,catId,groupid;
    byte[] image;

    public CategoryDetails(String groupid,String catId,String name, byte[] image){
        this.groupid = groupid;
        this.catId = catId;
        this.name = name;
        this.image = image;
    }

    public String getGroupid(){ return groupid;}

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
