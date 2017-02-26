package com.example.vuduc.model;

/**
 * Created by Brucelee Thanh on 25/02/2017.
 */

public class Place {
    private String id;
    private String name;
    private double lat;
    private double lon;
    private String type;
    private String parent;

    public Place() {
    }

    public Place(String id, String name,  String type, String parent) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.parent = parent;
    }

    public Place(String id, String name, double lat, double lon, String parent, String type) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.parent = parent;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
