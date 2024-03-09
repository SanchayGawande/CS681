package edu.umb.cs681.hw03;

import java.util.ArrayList;
import java.util.List;

public class Car {
    private String make;
    private String model;
    private int year;
    private int milage;
    private float price;

    public Car(String make, String model, int milage, int year, float price) {
        this.make = make;
        this.model = model;
        this.milage = milage;
        this.year = year;
        this.price = price;
    }
    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getMilage() {
        return milage;
    }

    public float getPrice() {
        return price;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMilage(int milage) {
        this.milage = milage;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public static List<Double> normalizeCarValues(int mileage, int year, float pricef) {
        List<Double> normalized = new ArrayList<>();
        int max = mileage;
        int min = year;
        int price = (int)pricef;

        if (year > max) {
            max = year;
        }
        if (price > max) {
            max = price;
        }
        if (year < min) {
            min = price;
        }
        if (price < min) {
            min = price;
        }
        normalized.add((double)mileage/(max-min));
        normalized.add((double)year/(max-min));
        normalized.add((double)price/(max-min));
        return normalized;
    }

}

