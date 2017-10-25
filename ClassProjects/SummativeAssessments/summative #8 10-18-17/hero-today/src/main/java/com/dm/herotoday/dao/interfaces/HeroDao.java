package com.dm.herotoday.dao.interfaces;

import com.dm.herotoday.exceptions.SQLUpdateException;
import com.dm.herotoday.model.Hero;

import java.util.List;

public interface HeroDao {

    public List<Hero> getAllHeroes();
    public List<Hero> getFromHeroes(String... args);
    public List<Hero> getFromHeroes(String heroID, String heroName, String heroType, String description);
    public boolean removeHero(int heroID) throws SQLUpdateException;
    public boolean updateHero(Hero hero) throws SQLUpdateException;
    public Hero addHero(Hero hero) throws SQLUpdateException;
    public boolean ifExists(int heroID);

}
