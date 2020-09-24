package com.ishaanp.test;

public class CompareFieldObject {

    private String date;
    private String name;
    private String value;

    public CompareFieldObject(String date, String name, String value) {
        this.date = date;
        this.name = name;
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
