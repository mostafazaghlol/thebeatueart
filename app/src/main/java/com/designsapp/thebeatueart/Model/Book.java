package com.designsapp.thebeatueart.Model;

public class Book {
    private String title,branch,price,date,opening;
    private  String image;
    public Book(String title,String branch,String price,String date,String opening,String image){
        this.title = title;
        this.branch = branch;
        this.price = price;
        this.date = date;
        this.opening = opening;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getBranch() {
        return branch;
    }

    public String getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public String getOpening() {
        return opening;
    }

    public String getImage() {
        return image;
    }
}
