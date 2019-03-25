package com.designsapp.thebeatueart.Model;

public class khadamat {
    private String title,desc,price;
    private int image;
    public khadamat(String title,int image){
        this.title = title;
        this.image = image;
    }
    public khadamat(String title,int image,String desc,String price){
        this.title = title;
        this.image = image;
        this.desc = desc;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }

    public String getDesc() {
        return desc;
    }

    public String getPrice() {
        return price;
    }
}
