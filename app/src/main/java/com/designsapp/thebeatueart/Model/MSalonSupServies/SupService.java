
package com.designsapp.thebeatueart.Model.MSalonSupServies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupService {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("section_id")
    @Expose
    private Integer sectionId;
    @SerializedName("service_id")
    @Expose
    private Integer serviceId;
    @SerializedName("salone_id")
    @Expose
    private Integer saloneId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getSaloneId() {
        return saloneId;
    }

    public void setSaloneId(Integer saloneId) {
        this.saloneId = saloneId;
    }

}
