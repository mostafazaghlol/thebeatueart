
package com.designsapp.thebeatueart.Model.MSalonSupServies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("section_id")
    @Expose
    private Integer sectionId;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("sup_services")
    @Expose
    private List<SupService> supServices = null;

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

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<SupService> getSupServices() {
        return supServices;
    }

    public void setSupServices(List<SupService> supServices) {
        this.supServices = supServices;
    }

}
