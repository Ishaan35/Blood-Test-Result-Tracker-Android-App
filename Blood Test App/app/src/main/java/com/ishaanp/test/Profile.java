package com.ishaanp.test;

public class Profile {
    private String NAME;
    private int Age;
    private double heightInMeters;
    private double WeightInKg;
    private double BMI;
    private int iconID;

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

    public double getHeightInMeters() {
        return heightInMeters;
    }

    public void setHeightInMeters(double heightInMeters) {
        this.heightInMeters = heightInMeters;
    }

    public double getWeightInKg() {
        return WeightInKg;
    }

    public void setWeightInKg(double weightInKg) {
        WeightInKg = weightInKg;
    }

    public double getBMI() {
        return BMI;
    }

    public void setBMI(double BMI) {
        this.BMI = BMI;
    }
}
