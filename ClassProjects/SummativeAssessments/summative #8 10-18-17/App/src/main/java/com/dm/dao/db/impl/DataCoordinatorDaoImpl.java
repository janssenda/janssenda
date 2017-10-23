package com.dm.dao.db.impl;

import com.dm.dao.db.interfaces.*;
import com.dm.exceptions.SQLUpdateException;
import com.dm.model.*;
import jdk.nashorn.internal.scripts.JD;
import org.aspectj.weaver.ast.Or;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataCoordinatorDaoImpl implements com.dm.dao.db.interfaces.DataCoordinatorDao {

    private OrganizationDao orgDao;
    private HeroDao heroDao;
    private HeadquartersDao headQDAO;
    private LocationDao locDao;
    private PowerDao powerDao;
    private SightingDao sightingDao;
    private ContactDao contactDao;
    private BridgeDao bridgeDao;
    private JdbcTemplate jdbcTemplate;

    public DataCoordinatorDaoImpl(OrganizationDao orgDao, HeroDao heroDao, HeadquartersDao headQDAO,
                                  LocationDao locDao, PowerDao powerDao, SightingDao sightingDao, ContactDao contactDao,
                                  BridgeDao bridgeDao) {
        this.orgDao = orgDao;
        this.heroDao = heroDao;
        this.headQDAO = headQDAO;
        this.locDao = locDao;
        this.powerDao = powerDao;
        this.sightingDao = sightingDao;
        this.contactDao = contactDao;
        this.bridgeDao = bridgeDao;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    ///////////////////
    // Organizations //
    ///////////////////
    private static final String ORG_HERO_MAP = "" +
            "SELECT HeroID FROM organizations INNER JOIN orgsheroes ON " +
            "orgsheroes.OrgID = organizations.OrgID WHERE organizations.OrgID = ?;";

    private static final String ORG_HEADQ_MAP = "" +
            "SELECT HeadQID FROM organizations INNER JOIN orgsheadquarters ON " +
            "organizations.OrgID = orgsheadquarters.OrgID WHERE organizations.OrgID = ?;";

    ///////////////////
    //   Sightings   //
    ///////////////////
    private static final String SIGHT_HERO_MAP = "" +
            "SELECT HeroID FROM sightings INNER JOIN sightingsheroes ON " +
            "sightingsheroes.SightingID = sightings.SightingID WHERE sightings.SightingID = ?;";


    ///////////////////
    //     Heroes    //
    ///////////////////
    private static final String HERO_POWER_MAP = "" +
            "SELECT PowerID FROM heroes INNER JOIN superpowersheroes ON " +
            "heroes.HeroID = superpowersheroes.HeroID WHERE heroes.HeroID = ?;";

    private static final String HERO_SIGHTING_MAP = "" +
            "SELECT SightingID FROM heroes INNER JOIN sightingsheroes ON " +
            "heroes.HeroID = sightingsheroes.HeroID WHERE heroes.HeroID = ?;";

    private static final String HERO_ORG_MAP = "" +
            "SELECT OrgID FROM heroes INNER JOIN orgsheroes ON " +
            "heroes.HeroID = orgsheroes.HeroID WHERE heroes.HeroID = ?;";
    private static final String DATEFORMAT = "yyyy-MM-dd";
    @Override
    public List<Sighting> sightingsByDate(LocalDate d) {
        Set<Sighting> sightSet = new HashSet<>();

        String dateString = d.format(DateTimeFormatter.ofPattern(DATEFORMAT));
        sightSet.addAll(getFromSightings(null, dateString));

        List<Sighting> sightList = new ArrayList<>();
        sightList.addAll(sightSet);
        return sightList;
    }

    @Override
    public List<Location> locationsByHero(int HeroID) {
        Set<Location> locSet = new HashSet<>();
        Hero h = getFromHeroes(Integer.toString(HeroID)).get(0);

        h.getHeroSightings().forEach((s) -> {
            locSet.addAll(locDao.getFromLocations(Integer.toString(s.getLocID())));
        });

        List<Location> locList = new ArrayList<>();
        locList.addAll(locSet);

        return locList;

    }

    @Override
    public List<Hero> heroesByLocation(int locID) {
        Set<Hero> heroSet = new HashSet<>();
        Location l = getFromLocations(Integer.toString(locID)).get(0);

        l.getLocSightings().forEach((s) -> {
            Sighting deep = getFromSightings(Integer.toString(s.getSightingID())).get(0);
            heroSet.addAll(deep.getSightingHeroes());
        });

        List<Hero> heroList = new ArrayList<>();
        heroList.addAll(heroSet);
        return heroList;
    }

    @Override
    public List<Location> getFromLocations(String... args) {
        String[] allArgs = varArgs(args, 10);
        List<Location> locList = locDao.getFromLocations(allArgs[0], allArgs[1], allArgs[2], allArgs[3],
                allArgs[4], allArgs[5], allArgs[6], allArgs[7], allArgs[8], allArgs[9]);

        locList.forEach((l) -> {
            l.setLocSightings(sightingDao
                    .getFromSightings(null, null, Integer.toString(l.getLocID())));
        });

        return locList;
    }

    @Override
    @Transactional
    public List<Sighting> getFromSightings(String... args) {
        String[] allArgs = varArgs(args, 3);
        List<Sighting> sightingList = sightingDao.getFromSightings(allArgs[0], allArgs[1], allArgs[2]);

        sightingList.forEach((s) -> setSightingHeroes(s));
        return sightingList;
    }

    @Override
    @Transactional
    public List<Headquarters> getFromHeadquarters(String... args) {
        String[] allArgs = varArgs(args, 4);
        List<Headquarters> headQList = headQDAO.getFromHeadquarters(allArgs[0], allArgs[1], allArgs[2], allArgs[3]);

        headQList.forEach((h) -> {
            h.setContactList(contactDao.getFromContacts(Integer.toString(h.getHeadQID())));
        });

        return headQList;
    }

    @Override
    @Transactional
    public List<Organization> getFromOrgs(String... args) {
        String[] allArgs = varArgs(args, 3);
        List<Organization> orgList = orgDao.getFromOrgs(allArgs[0], allArgs[1], allArgs[2]);

        orgList.forEach((o) -> {
            setOrgHeroes(o);
            setOrgHeadquarters(o);
        });

        return orgList;
    }

    @Override
    @Transactional
    public List<Hero> getFromHeroes(String... args) {
        String[] allArgs = varArgs(args, 4);
        List<Hero> heroList = heroDao.getFromHeroes(allArgs[0], allArgs[1], allArgs[2], allArgs[3]);

        heroList.forEach((h) -> {
            setHeroPowers(h);
            setHeroOrgs(h);
            setHeroSightings(h);
        });

        return heroList;
    }

    private void setSightingHeroes(Sighting s) {
        List<Hero> objList = new ArrayList<>();
        resLoop(jdbcTemplate.queryForList(SIGHT_HERO_MAP, s.getSightingID())).forEach((h) -> {
            objList.add(heroDao.getFromHeroes(h).get(0));
        });
        s.setSightingHeroes(objList);
    }

    private void setOrgHeadquarters(Organization o) {
        List<Headquarters> objList = new ArrayList<>();
        resLoop(jdbcTemplate.queryForList(ORG_HEADQ_MAP, o.getOrgID()))
                .forEach((h) -> {
                    objList.add(headQDAO.getFromHeadquarters(h).get(0));
                });
        o.setOrgHeadQ(objList);
    }

    private void setOrgHeroes(Organization o) {
        List<Hero> objList = new ArrayList<>();
        resLoop(jdbcTemplate.queryForList(ORG_HERO_MAP, o.getOrgID()))
                .forEach((h) -> {
                    objList.add(heroDao.getFromHeroes(h).get(0));
                });
        o.setMembers(objList);
    }

    private void setHeroSightings(Hero h) {
        List<Sighting> objList = new ArrayList<>();
        resLoop(jdbcTemplate.queryForList(HERO_SIGHTING_MAP, h.getHeroID()))
                .forEach((p) -> {
                    objList.add(sightingDao.getFromSightings(p).get(0));
                });
        h.setHeroSightings(objList);
    }

    private void setHeroPowers(Hero h) {
        List<Power> objList = new ArrayList<>();
        resLoop(jdbcTemplate.queryForList(HERO_POWER_MAP, h.getHeroID()))
                .forEach((p) -> {
                    objList.add(powerDao.getFromPowers(p).get(0));
                });
        h.setHeroPowers(objList);
    }

    private void setHeroOrgs(Hero h) {
        List<Organization> objList = new ArrayList<>();
        resLoop(jdbcTemplate.queryForList(HERO_ORG_MAP, h.getHeroID()))
                .forEach((p) -> {
                    objList.add(orgDao.getFromOrgs(p).get(0));
                });
        h.setHeroOrgs(objList);
    }

    //////////////////////
    // Repeated methods //
    //////////////////////
    private static List<String> resLoop(List<Map<String, Object>> results) {
        List<String> stringparams = new ArrayList<>();
        results.forEach((m) -> {
            m.forEach((k, v) -> {
                stringparams.add(v.toString());
            });
        });
        return stringparams;
    }

    private static String[] varArgs(String[] args, int arrLength) {
        String[] allArgs = new String[arrLength];
        System.arraycopy(args, 0, allArgs, 0, args.length);
        for (int i = args.length; i < allArgs.length; i++) {
            allArgs[i] = null;
        }
        return allArgs;
    }


    ////////////////////
    //  Pass-through  //
    ////////////////////
    @Override public boolean removeHero(int heroID) throws SQLUpdateException {return heroDao.removeHero(heroID);}
    @Override public boolean updateHero(Hero hero) throws SQLUpdateException {return heroDao.updateHero(hero); }
    @Override public Hero addHero (Hero hero) throws SQLUpdateException {return heroDao.addHero(hero);}

    @Override public Contact addContact(Contact contact) throws SQLUpdateException {return contactDao.addContact(contact);}
    @Override public boolean removeContact(Contact contact) throws SQLUpdateException {return contactDao.removeContact(contact);}
    @Override public boolean updateContact(Contact oldContact, Contact newContact) throws SQLUpdateException {return contactDao.updateContact(oldContact,newContact);}

    @Override public Headquarters addHeadquarters(Headquarters headq) throws SQLUpdateException {return headQDAO.addHeadquarters(headq);}
    @Override public boolean removeHeadquarters(int headqID) throws SQLUpdateException {return headQDAO.removeHeadquarters(headqID);}
    @Override public boolean updateHeadquarters(Headquarters headq) throws SQLUpdateException {return headQDAO.updateHeadquarters(headq);}

    @Override public Location addLocation(Location loc) throws SQLUpdateException {return locDao.addLocation(loc);}
    @Override public boolean removeLocation(int locID) throws SQLUpdateException {return locDao.removeLocation(locID);}
    @Override public boolean updateLocation(Location loc) throws SQLUpdateException {return locDao.updateLocation(loc);}

    @Override public Organization addOrg(Organization org) throws SQLUpdateException {return orgDao.addOrg(org);}
    @Override public boolean removeOrg(int orgID) throws SQLUpdateException {return orgDao.removeOrg(orgID);}
    @Override public boolean updateOrg(Organization org) throws SQLUpdateException {return orgDao.updateOrg(org);}

    @Override public Power addPower (Power power) throws SQLUpdateException {return powerDao.addPower(power);}
    @Override public boolean removePower(int powerID) throws SQLUpdateException {return powerDao.removePower(powerID);}
    @Override public boolean updatePower(Power power) throws SQLUpdateException {return powerDao.updatePower(power);}

    @Override public Sighting addSighting(Sighting sighting) throws SQLUpdateException {return sightingDao.addSighting(sighting);}
    @Override public boolean removeSighting(int sightingID) throws SQLUpdateException {return sightingDao.removeSighting(sightingID);}
    @Override public boolean updateSighting(Sighting sighting) throws SQLUpdateException {return sightingDao.updateSighting(sighting);}

    // Bridge maps
    @Override public boolean addOrgsHeadquarters(int orgID, int headQID) throws SQLUpdateException {return bridgeDao.addOrgsHeadquarters(orgID, headQID);}
    @Override public boolean removeOrgsHeadquarters(int orgID, int headQID) throws SQLUpdateException {return bridgeDao.removeOrgsHeadquarters(orgID, headQID);}
    @Override public boolean addOrgsHeroes(int orgID, int heroID) throws SQLUpdateException {return bridgeDao.addOrgsHeroes(orgID, heroID);}
    @Override public boolean removeOrgsHeroes(int orgID, int heroID) throws SQLUpdateException {return bridgeDao.removeOrgsHeroes(orgID, heroID);}
    @Override public boolean addSightingsHeroes(int sightingID, int heroID) throws SQLUpdateException {return bridgeDao.addSightingsHeroes(sightingID, heroID);}
    @Override public boolean removeSightingsHeroes(int sightingID, int heroID) throws SQLUpdateException {return bridgeDao.removeSightingsHeroes(sightingID, heroID);}
    @Override public boolean addPowersHeroes(int powerID, int heroID) throws SQLUpdateException {return bridgeDao.addPowersHeroes(powerID, heroID);}
    @Override public boolean removePowersHeroes(int powerID, int heroID) throws SQLUpdateException {return bridgeDao.removePowersHeroes(powerID, heroID);}


}
