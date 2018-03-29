package com.vastquery.www.vastquery.PropertyClasses;


public class ShopClass {
    private String address,pincode,city,district,owner_name;
    private byte[] logo,owner;
    private float rating;

    public ShopClass( String address,String city, String district, String pincode, byte[] logo, String owner_name, byte[] owner, float rating) {
        this.address = address;
        this.pincode = pincode;
        this.city = city;
        this.district = district;
        this.owner_name = owner_name;
        this.logo = logo;
        this.owner = owner;
        this.rating = rating;
    }

    public String getPincode() {
        return pincode;
    }

    public String getOwnerName() {
        return owner_name;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getOwner_name() {
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
