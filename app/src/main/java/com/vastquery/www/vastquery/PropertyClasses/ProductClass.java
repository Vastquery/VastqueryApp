package com.vastquery.www.vastquery.PropertyClasses;




import java.io.Serializable;
import java.util.Date;




public class ProductClass implements Serializable {

    String product_name,price;
    byte[] front;
    int Id;

    public ProductClass(int Id,String product_name, String price, byte[] front) {
        this.product_name = product_name;
        this.price = price;
        this.front = front;
        this.Id = Id;
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


    public int getId() {
        return Id;
    }
}
