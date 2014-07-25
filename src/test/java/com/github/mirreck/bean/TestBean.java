package com.github.mirreck.bean;

/**
 * Created by thomas.decoster on 11/07/2014.
 */
public class TestBean {



    public enum Color {RED, GREEN, BLUE}

    private String str;
    private String str2;
    private Color color;

    public int getInt1() {
        return int1;
    }

    public void setInt1(int int1) {
        this.int1 = int1;
    }

    public int getInt2() {
        return int2;
    }

    public void setInt2(int int2) {
        this.int2 = int2;
    }

    private int int1;
    private int int2;

    public String getStr2() {
        return str2;
    }

    public void setStr2(String str2) {
        this.str2 = str2;
    }

    public Color getColor2() {
        return color2;
    }

    public void setColor2(Color color2) {
        this.color2 = color2;
    }

    private Color color2;

    private String name;

    private Address address;

    public TestBean() {
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "str='" + str + '\'' +
                ", str2='" + str2 + '\'' +
                ", color=" + color +
                ", int1=" + int1 +
                ", int2=" + int2 +
                ", color2=" + color2 +
                ", name='" + name + '\'' +
                ", address=" + address +
                '}';
    }
}
