package com.capstone.affinity_ad;

public class gridItem {
    String id;
    String img;
    String name;
    String price;
    String brand;

    public gridItem(String i, String m, String b, String n, String p) {
        this.id = i;
        this.img = m;
        this.brand = b;
        this.name = n;
        this.price = p;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
