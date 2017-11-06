package com.dm.herotoday.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.time.LocalDateTime;
import java.util.List;

public class Sighting {


    private int sightingID;
    private int locID;
    private List<Hero> sightingHeroes;
    private Location loc;

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = ParseDeserializer.class)
    private LocalDateTime sightingTime;

    public Location getLoc() {return loc; }

    public void setLoc(Location loc) {this.loc = loc;}

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
        return locID;
    }

    public void setLocID(int locID) {
        this.locID = locID;
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
        if (locID != sighting.locID) return false;
        if (sightingHeroes != null ? !sightingHeroes.equals(sighting.sightingHeroes) : sighting.sightingHeroes != null)
            return false;
        //return sightingTime != null ? sightingTime.equals(sighting.sightingTime) : sighting.sightingTime == null;
        return true;
    }

    @Override
    public int hashCode() {
        int result = sightingID;
        result = 31 * result + locID;
        result = 31 * result + (sightingHeroes != null ? sightingHeroes.hashCode() : 0);
        //result = 31 * result + (sightingTime != null ? sightingTime.hashCode() : 0);
        return result;
    }


}
