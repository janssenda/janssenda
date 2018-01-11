package com.dm.impulsereactor.model;

public class Cab {
    private String cabID, brand, cabName, size;


    public String getCabID() {
        return cabID;
    }
    public void setCabID(String cabID) {
        this.cabID = cabID;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getCabName() {
        return cabName;
    }
    public void setCabName(String cabName) {
        this.cabName = cabName;
    }
    public String getSize() { return size; }
    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return    "Cab ID: " + cabID
                + " Name: " + cabName
                + " Brand: " + brand
                + " Size: " + size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cab cab = (Cab) o;

        if (cabID != null ? !cabID.equals(cab.cabID) : cab.cabID != null) return false;
        if (brand != null ? !brand.equals(cab.brand) : cab.brand != null) return false;
        if (cabName != null ? !cabName.equals(cab.cabName) : cab.cabName != null) return false;
        return size != null ? size.equals(cab.size) : cab.size == null;
    }

    @Override
    public int hashCode() {
        int result = cabID != null ? cabID.hashCode() : 0;
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (cabName != null ? cabName.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        return result;
    }
}
