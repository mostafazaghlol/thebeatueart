package com.designsapp.thebeatueart.Model;

public class Salon {
    private  String title,location;
    private Float rate;
    private  int image;
    public Salon(String mTitle,String mLocation,Float mRate,int mImage){
        this.title = mTitle;
        this.location = mLocation;
        this.rate = mRate;
        this.image = mImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
