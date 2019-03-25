
package com.designsapp.thebeatueart.Model;

public class OrderItem2 {
    private Integer numberOfPeople;

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public OrderItem2(Integer numberOfPeople, Integer id, Integer orderId, Integer subServiceId, String price, Integer peopleNumber, String name) {
        this.numberOfPeople = numberOfPeople;
        this.id = id;
        this.orderId = orderId;
        this.subServiceId = subServiceId;
        this.price = price;
        this.peopleNumber = peopleNumber;
        this.name = name;
    }
    private Integer id;
    private Integer orderId;
    private Integer subServiceId;
    private String price;
    private Integer peopleNumber;
    private String name;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getSubServiceId() {
        return subServiceId;
    }

    public void setSubServiceId(Integer subServiceId) {
        this.subServiceId = subServiceId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(Integer peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
