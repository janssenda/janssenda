package com.dm.herotoday.model;

import java.util.List;

public class Power {

    private int powerID;
    private String powerName;
    private String description;
    private List<Hero> heroList;

    public int getPowerID() {
        return powerID;
    }

    public void setPowerID(int powerID) {
        this.powerID = powerID;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Hero> getHeroList() {return heroList;}

    public void setHeroList(List<Hero> heroList) {this.heroList = heroList;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Power that = (Power) o;

        if (powerID != that.powerID) return false;
        if (powerName != null ? !powerName.equals(that.powerName) : that.powerName != null) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = powerID;
        result = 31 * result + (powerName != null ? powerName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
