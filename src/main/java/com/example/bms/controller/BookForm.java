package com.example.bms.controller;

public class BookForm {
    private String name ="";
    private String publisher = "";
    private Integer price = 0;

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}

