package com.example.ecommerceapp.ModelClass;

public class Cart {

    private String cartid,pid,pname,description,quantity,img_name,cat_name;
    private double price;

    public Cart() {
    }

    public Cart(String cartid, String pid, String pname, String description, String quantity, String img_name, String cat_name, double price) {
        this.cartid = cartid;
        this.pid = pid;
        this.pname = pname;
        this.description = description;
        this.quantity = quantity;
        this.img_name = img_name;
        this.cat_name = cat_name;
        this.price = price;
    }

    public String getCartid() {
        return cartid;
    }

    public void setCartid(String cartid) {
        this.cartid = cartid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImg_name() {
        return img_name;
    }

    public void setImg_name(String img_name) {
        this.img_name = img_name;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
