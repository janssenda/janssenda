package com.dm.model;

import java.time.LocalDateTime;
import java.util.List;

public class Sighting {

    private int sightingID;
    private int LocID;
    private List<Hero> sightingHeroes;
    private LocalDateTime sightingTime;

    public List<Hero> getSightingHeroes() {
        return sightingHeroes;
    }

    public void setSightingHeroes(List<Hero> sightingHeroes) {
        this.sightingHeroes = sightingHeroes;
    }

    public int getSightingID() {
        return sightingID;
    }

    public void setSightingID(int sightingID) {
        this.sightingID = sightingID;
    }

    public int getLocID() {
        return LocID;
    }

    public void setLocID(int locID) {
        LocID = locID;
    }

    public LocalDateTime getSightingTime() {
        return sightingTime;
    }

    public void setSightingTime(LocalDateTime sightingTime) {
        this.sightingTime = sightingTime;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sighting sighting = (Sighting) o;

        if (sightingID != sighting.sightingID) return false;
        if (LocID != sighting.LocID) return false;
        if (sightingHeroes != null ? !sightingHeroes.equals(sighting.sightingHeroes) : sighting.sightingHeroes != null)
            return false;
        //return sightingTime != null ? sightingTime.equals(sighting.sightingTime) : sighting.sightingTime == null;
        return true;
    }

    @Override
    public int hashCode() {
        int result = sightingID;
        result = 31 * result + LocID;
        result = 31 * result + (sightingHeroes != null ? sightingHeroes.hashCode() : 0);
        //result = 31 * result + (sightingTime != null ? sightingTime.hashCode() : 0);
        return result;
    }
}
