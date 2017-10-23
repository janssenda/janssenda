package com.dm.model;

import java.util.List;

public class Location {

    private int locID;
    private double latitude;
    private double longitude;
    private String locName;
    private String country;
    private String city;
    private String state;
    private String address;
    private String zip;
    private String description;
    private List<Sighting> locSightings;

    public List<Sighting> getLocSightings() {
        return locSightings;
    }

    public void setLocSightings(List<Sighting> locSightings) {
        this.locSightings = locSightings;
    }

    public int getLocID() {
        return locID;
    }

    public void setLocID(int locID) {
        this.locID = locID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (locID != location.locID) return false;
        if (Double.compare(location.latitude, latitude) != 0) return false;
        if (Double.compare(location.longitude, longitude) != 0) return false;
        if (locName != null ? !locName.equals(location.locName) : location.locName != null) return false;
        if (country != null ? !country.equals(location.country) : location.country != null) return false;
        if (city != null ? !city.equals(location.city) : location.city != null) return false;
        if (state != null ? !state.equals(location.state) : location.state != null) return false;
        if (address != null ? !address.equals(location.address) : location.address != null) return false;
        if (zip != null ? !zip.equals(location.zip) : location.zip != null) return false;
        if (description != null ? !description.equals(location.description) : location.description != null)
            return false;
        return locSightings != null ? locSightings.equals(location.locSightings) : location.locSightings == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = locID;
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (locName != null ? locName.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (zip != null ? zip.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (locSightings != null ? locSightings.hashCode() : 0);
        return result;
    }
}
