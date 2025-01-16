package com.github.sats17.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "location")
public class Location extends PanacheEntity {

    @Column(nullable = false)
    public String city;

    @Column(nullable = false, unique = false)
    public String lat;

    @Column(nullable = false)
    public String lon;

    public Location() {}

    public Location(String city, String lat, String lon) {
        this.city = city;
        this.lat = lat;
        this.lon = lon;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getCity() {
        return city;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }
}