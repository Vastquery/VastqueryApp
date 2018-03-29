package com.vastquery.www.vastquery.PropertyClasses;




import java.io.Serializable;
import java.util.Date;




public class ProductClass implements Serializable {

    private String product_name,price;
    private byte[] front,back,side;
    private String Id,shop_Id;


    public ProductClass(String Id,String shop_Id,String product_name, String price, byte[] front,byte[] back,byte[] side) {
        this.product_name = product_name;
        this.price = price;
        this.front = front;
        this.Id = Id;
        this.shop_Id = shop_Id;
        this.back = back;
        this.side = side;
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

    public String getShop_Id(){ return shop_Id;}

    public String getId() {
        return Id;
    }

    public byte[] getBack(){ return back;}

    public byte[] getSide(){ return side;}
}
