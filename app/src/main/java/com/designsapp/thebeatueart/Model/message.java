package com.designsapp.thebeatueart.Model;

public class message {
    private String title,subtitle,time;
    private int image,id;
    private String imageurl;
    public  message(String title,String subtitle,String time,int image){
        this.title = title;
        this.subtitle = subtitle;
        this.time = time;
        this.image = image;
    }
    public  message(String title,String image,int id){
        this.title = title;
        this.imageurl = image;
        this.id = id;
    }

    public String getImageurl() {
        return imageurl;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getTime() {
        return time;
    }

    public int getImage() {
        return image;
    }
}
