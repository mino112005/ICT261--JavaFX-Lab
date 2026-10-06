package com.example.hellofx;

public class Customer {
    private final String name, province;

    public Customer(String name, String province) {
        this.name = name;
        this.province = province;
    }

    public String getName() { return name; }
    public String getProvince() { return province; }
}
