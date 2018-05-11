package com.dm.huetron.model;

public class Light {
    private String lightID;
    private String title;
    private String description;
    private String hue;
    private String brt;
    private String sat;
    private boolean state;

    public Light() {
        this.lightID = "";
        this.description = "";
        this.title = "";
        this.brt = "0";
        this.sat = "0";
        this.hue = "0";
        this.state = false;
    }

    public String getLightID() {
        return lightID;
    }

    public void setLightID(String lightID) {
        this.lightID = lightID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHue() {
        return hue;
    }

    public void setHue(String hue) {
        this.hue = hue;
    }

    public String getBrt() {
        return brt;
    }

    public void setBrt(String brt) {
        this.brt = brt;
    }

    public String getSat() {
        return sat;
    }

    public void setSat(String sat) {
        this.sat = sat;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Light light = (Light) o;

        if (state != light.state) return false;
        if (lightID != null ? !lightID.equals(light.lightID) : light.lightID != null) return false;
        if (title != null ? !title.equals(light.title) : light.title != null) return false;
        if (description != null ? !description.equals(light.description) : light.description != null) return false;
        if (hue != null ? !hue.equals(light.hue) : light.hue != null) return false;
        if (brt != null ? !brt.equals(light.brt) : light.brt != null) return false;
        return sat != null ? sat.equals(light.sat) : light.sat == null;
    }

    @Override
    public int hashCode() {
        int result = lightID != null ? lightID.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (hue != null ? hue.hashCode() : 0);
        result = 31 * result + (brt != null ? brt.hashCode() : 0);
        result = 31 * result + (sat != null ? sat.hashCode() : 0);
        result = 31 * result + (state ? 1 : 0);
        return result;
    }
}
