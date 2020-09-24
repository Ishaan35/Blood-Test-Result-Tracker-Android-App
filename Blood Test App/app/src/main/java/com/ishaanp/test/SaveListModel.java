package com.ishaanp.test;

import java.util.ArrayList;

public class SaveListModel {
    private ArrayList<String> fields;
    private ArrayList<String> values;
    private ArrayList<String> units;
    private String date;
    private String RecordName;

    String name = "";

    public SaveListModel(ArrayList<String> fields, ArrayList<String> value, ArrayList<String> units) {
        this.fields = fields;
        this.values = value;
        this.units = units;
    }



    public ArrayList<String> getFields() {
        return fields;
    }

    public void setFields(ArrayList<String> fields) {
        this.fields = fields;
    }

    public ArrayList<String> getValue() {
        return values;
    }

    public void setValue(ArrayList<String> value) {
        this.values = value;
    }

    public ArrayList<String> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<String> units) {
        this.units = units;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRecordName() {
        return RecordName;
    }

    public void setRecordName(String recordName) {
        RecordName = recordName;
    }
}
