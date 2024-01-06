package com.example.fieldpreview;

public class Field {

    String img, title, address, days, hours;

    public Field(String img, String title, String address, String days, String hours) {
        this.img = img;
        this.title = title;
        this.address = address;
        this.days = days;
        this.hours = hours;
    }

    public Field(String img, String title, String days, String hours) {
        this.img = img;
        this.title = title;
        this.days = days;
        this.hours = hours;
    }
}
