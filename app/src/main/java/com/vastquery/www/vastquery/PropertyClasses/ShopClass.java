package com.vastquery.www.vastquery.PropertyClasses;


public class ShopClass {
    private String address,pincode,email,phone,mobile1,mobile2,website,owner_name;
    private byte[] logo,owner;
    private float rating;

    public ShopClass( String address,String pincode, String email, String phone, String mobile1, String mobile2, String website, byte[] logo, byte[] owner, String owner_name, float rating) {
        this.address = address;
        this.pincode = pincode;
        this.email = email;
        this.phone = phone;
        this.mobile1 = mobile1;
        this.mobile2 = mobile2;
        this.website = website;
        this.owner_name = owner_name;
        this.logo = logo;
        this.owner = owner;
        this.rating = rating;
    }

    public String getPincode() {
        return pincode;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getMobile1() {
        return mobile1;
    }

    public String getMobile2() {
        return mobile2;
    }

    public String getWebsite() {
        return website;
    }

    public String getOwnerName() {
        return owner_name;
    }

    public byte[] getLogo() {
        return logo;
    }

    public byte[] getOwner() {
        return owner;
    }

    public float getRating() { return rating;}

    public String getAddress(){
        return address;
    }
}
