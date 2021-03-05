package com.example.ecommerceapp.ModelClass;

public class Product {
    String prodName,prodDesc,prodImg,catName,prodId;
    int prodQuant;
    double prodPrice;

    public Product(String prodName, String prodDesc, String prodImg, String catName, String prodId, int prodQuant, double prodPrice) {
        this.prodName = prodName;
        this.prodDesc = prodDesc;
        this.prodImg = prodImg;
        this.catName = catName;
        this.prodId = prodId;
        this.prodQuant = prodQuant;
        this.prodPrice = prodPrice;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdDesc() {
        return prodDesc;
    }

    public void setProdDesc(String prodDesc) {
        this.prodDesc = prodDesc;
    }

    public String getProdImg() {
        return prodImg;
    }

    public void setProdImg(String prodImg) {
        this.prodImg = prodImg;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public int getProdQuant() {
        return prodQuant;
    }

    public void setProdQuant(int prodQuant) {
        this.prodQuant = prodQuant;
    }

    public double getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(double prodPrice) {
        this.prodPrice = prodPrice;
    }
}

