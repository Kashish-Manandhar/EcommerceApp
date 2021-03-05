package com.example.ecommerceapp.ModelClass;

public class Order {
    String location,name,number,status,cur_user_num;
    Double total_price;

    public Order() {
    }

    public Order(String location, String name, String number, String status, String cur_user_num, Double total_price) {
        this.location = location;
        this.name = name;
        this.number = number;
        this.status = status;
        this.cur_user_num = cur_user_num;
        this.total_price = total_price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCur_user_num() {
        return cur_user_num;
    }

    public void setCur_user_num(String cur_user_num) {
        this.cur_user_num = cur_user_num;
    }

    public Double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
    }
}
