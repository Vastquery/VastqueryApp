package com.vastquery.www.vastquery.PropertyClasses;




import java.io.Serializable;
import java.util.Date;




public class ProductClass implements Serializable {

    private String product_name,price;
    private byte[] front;
    private int Id,shop_Id;


    public ProductClass(int Id,int shop_Id,String product_name, String price, byte[] front) {
        this.product_name = product_name;
        this.price = price;
        this.front = front;
        this.Id = Id;
        this.shop_Id = shop_Id;
    }

    public String getProduct_name(){
        return product_name;
    }

    public String getPrice() {
        return price;
    }

    public byte[] getFront() {
        return front;
    }

    public int getShop_Id(){ return shop_Id;}

    public int getId() {
        return Id;
    }
}
