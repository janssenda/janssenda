package com.dm.model;

import java.util.List;

public class Hero {

    private int heroID;
    private String heroName;
    private String heroType;
    private String description;
    private List<Superpower> heroPowers;
    private List<Organization> heroOrgs;
    private List<Sighting> heroSightings;

    public List<Superpower> getHeroPowers() {
        return heroPowers;
    }

    public void setHeroPowers(List<Superpower> heroPowers) {
        this.heroPowers = heroPowers;
    }

    public List<Organization> getHeroOrgs() {
        return heroOrgs;
    }

    public void setHeroOrgs(List<Organization> heroOrgs) {
        this.heroOrgs = heroOrgs;
    }

    public List<Sighting> getHeroSightings() {
        return heroSightings;
    }

    public void setHeroSightings(List<Sighting> heroSightings) {
        this.heroSightings = heroSightings;
    }

    public int getHeroID() {
        return heroID;
    }

    public void setHeroID(int heroID) {
        this.heroID = heroID;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String getHeroType() {
        return heroType;
    }

    public void setHeroType(String heroType) {
        this.heroType = heroType;
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

        Hero hero = (Hero) o;

        if (heroID != hero.heroID) return false;
        if (heroName != null ? !heroName.equals(hero.heroName) : hero.heroName != null) return false;
        if (heroType != null ? !heroType.equals(hero.heroType) : hero.heroType != null) return false;
        if (description != null ? !description.equals(hero.description) : hero.description != null) return false;
        if (heroPowers != null ? !heroPowers.equals(hero.heroPowers) : hero.heroPowers != null) return false;
        if (heroOrgs != null ? !heroOrgs.equals(hero.heroOrgs) : hero.heroOrgs != null) return false;
        return heroSightings != null ? heroSightings.equals(hero.heroSightings) : hero.heroSightings == null;
    }

    @Override
    public int hashCode() {
        int result = heroID;
        result = 31 * result + (heroName != null ? heroName.hashCode() : 0);
        result = 31 * result + (heroType != null ? heroType.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (heroPowers != null ? heroPowers.hashCode() : 0);
        result = 31 * result + (heroOrgs != null ? heroOrgs.hashCode() : 0);
        result = 31 * result + (heroSightings != null ? heroSightings.hashCode() : 0);
        return result;
    }
}
