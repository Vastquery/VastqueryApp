package com.vastquery.www.vastquery.PropertyClasses;

/**
 * Created by aj-ajay on 4/7/18.
 */

public class UserDetails {

    int user_id;
    String name,mobile,shop_id;

    public UserDetails(int user_id, String name, String mobile,String shop_id) {
        this.user_id = user_id;
        this.name = name;
        this.mobile = mobile;
        this.shop_id = shop_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }
}
