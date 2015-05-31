package com.h4_technology.eobee.model;

/**
 * Created by user on 5/30/2015.
 */
public class ZipcodeLatLongModel {
    public String zipcode;
    public double latitude;
    public double longitude;
    public ZipcodeLatLongModel(){

    }
    public ZipcodeLatLongModel(String zip, double lat, double lon) {
        this.zipcode = zip;
        this.latitude = lat;
        this.longitude = lon;
    }
}
