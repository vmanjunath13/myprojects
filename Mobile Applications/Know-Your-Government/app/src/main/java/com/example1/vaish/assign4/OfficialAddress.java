package com.example1.vaish.assign4;

import java.io.Serializable;

/**
 * Created by Vaishnavi on 03/20/2020.
 */

public class OfficialAddress implements Serializable{
    private String line1;
    private String line2;
    private String line3;
    private String city;
    private String state;
    private String zip;

    public OfficialAddress() {
    }

    public OfficialAddress(String line1, String line2, String line3, String city, String state, String zip) {
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public String toString() {
        return "Address : "+line1
                +"\n "+line2
                +"\n "+line3
                +"\n City = "+city
                +"\n State = "+state
                +"\n zip = "+zip;
    }
}
