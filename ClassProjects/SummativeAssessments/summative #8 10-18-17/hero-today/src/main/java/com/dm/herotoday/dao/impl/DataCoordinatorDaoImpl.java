package com.dm.herotoday.dao.impl;

import com.dm.herotoday.dao.interfaces.*;
import com.dm.herotoday.exceptions.DuplicateEntryException;
import com.dm.herotoday.exceptions.SQLUpdateException;
import com.dm.herotoday.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Named
public class DataCoordinatorDaoImpl implements DataCoordinatorDao {

    private OrganizationDao orgDao;
    private HeroDao heroDao;
    private HeadquartersDao headQDAO;
    private LocationDao locDao;
    private PowerDao powerDao;
    private SightingDao sightingDao;
    private ContactDao contactDao;
    private BridgeDao bridgeDao;
    private JdbcTemplate jdbcTemplate;

    @Inject
    public DataCoordinatorDaoImpl(OrganizationDao orgDao, HeroDao heroDao, HeadquartersDao headQDAO,
                                  LocationDao locDao, PowerDao powerDao, SightingDao sightingDao, ContactDao contactDao,
                                  BridgeDao bridgeDao, JdbcTemplate jdbcTemplate) {
        this.orgDao = orgDao;
        this.heroDao = heroDao;
        this.headQDAO = headQDAO;
        this.locDao = locDao;
        this.powerDao = powerDao;
        this.sightingDao = sightingDao;
        this.contactDao = contactDao;
        this.bridgeDao = bridgeDao;
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

    private static final String HEADQ_ORG_MAP = "" +
            "SELECT OrgID FROM headquarters INNER JOIN orgsheadquarters ON " +
            "headquarters.HeadQID = orgsheadquarters.HeadQID WHERE headquarters.HeadQID = ?;";

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
    public Hero changeHero(Hero hero) throws SQLUpdateException, DuplicateEntryException {
        try {
            hero = heroDao.addHero(hero);
        } catch (DuplicateEntryException e) {
            // Update instead
        }

        updateHero(hero);
        return getFromHeroes(Integer.toString(hero.getHeroID())).get(0);
    }


    private void updateHero(Hero hero) throws SQLUpdateException, DuplicateEntryException {

        String hID = Integer.toString(hero.getHeroID());

        bridgeDao.removePowersHeroes(null,hID);
        bridgeDao.removeOrgsHeroes(null,hID);
        bridgeDao.removeSightingsHeroes(null,hID);

        if (hero.getHeroPowers() != null){
        for (Power p : hero.getHeroPowers()) {
            try {
                powerDao.addPower(p);
            } catch (SQLUpdateException | DuplicateEntryException e) {
                // skip if not new
            }
            try {
                bridgeDao.addPowersHeroes(p.getPowerID(), hero.getHeroID());
            } catch (SQLUpdateException e) {
                // skip if not new
            }
        }
        }


        if (hero.getHeroOrgs() != null) {
            for (Organization o : hero.getHeroOrgs()) {

                try {
                    orgDao.addOrg(o);
                } catch (SQLUpdateException | DuplicateEntryException e) {
                    // skip if not new
                }
                try {
                    bridgeDao.addOrgsHeroes(o.getOrgID(), hero.getHeroID());
                } catch (SQLUpdateException e) {
                    // skip if not new
                }
            }
        }

        if (hero.getHeroSightings() != null) {
            for (Sighting s : hero.getHeroSightings()) {
                try {
                    sightingDao.addSighting(s);
                } catch (SQLUpdateException | DuplicateEntryException e) {
                    // skip if not new
                }
                try {
                    bridgeDao.addSightingsHeroes(s.getSightingID(), hero.getHeroID());
                } catch (SQLUpdateException e) {
                    // skip if not new
                }
            }
        }

        heroDao.updateHero(hero);
    }

    @Override
    public Headquarters changeHeadquarters(Headquarters headq) throws SQLUpdateException, DuplicateEntryException {
        try {
            headq = headQDAO.addHeadquarters(headq);
        } catch (DuplicateEntryException e){
            // still update
        }
        updateHeadquarters(headq);

        return getFromHeadquarters(Integer.toString(headq.getHeadQID())).get(0);
    }



    private void updateHeadquarters(Headquarters headq) throws SQLUpdateException {

        String hQID = Integer.toString(headq.getHeadQID());

        bridgeDao.removeOrgsHeadquarters(null, hQID);
        contactDao.removeFromContacts(hQID);

        if (headq.getContactList() != null) {
            for (Contact c : headq.getContactList()) {
                try {
                    c.setHeadQID(headq.getHeadQID());
                    contactDao.addContact(c);
                } catch (DuplicateEntryException e) {
                    // skip if not new
                }
            }
        }

        if (headq.getOrgList() != null) {
            for (Organization o : headq.getOrgList()) {
                try {
                    orgDao.addOrg(o);
                } catch (DuplicateEntryException e) {
                    // skip if not new
                }
                try {
                    bridgeDao.addOrgsHeadquarters(o.getOrgID(), headq.getHeadQID());
                } catch (SQLUpdateException e) {
                    // skip if not new
                }
            }
        }
        headQDAO.updateHeadquarters(headq);
    }

    @Override
    public Location changeLocation(Location loc) throws SQLUpdateException, DuplicateEntryException {
        try {
            loc = locDao.addLocation(loc);
        } catch (DuplicateEntryException e){
            // still update
        }
        locDao.updateLocation(loc);
        return getFromLocations(Integer.toString(loc.getLocID())).get(0);
    }

    @Override
    public Organization changeOrg(Organization org) throws SQLUpdateException, DuplicateEntryException {
        try {
            org = orgDao.addOrg(org);
        } catch (DuplicateEntryException e){
            // still update
        }
        updateOrg(org);
        return getFromOrgs(Integer.toString(org.getOrgID())).get(0);
    }


    private void updateOrg(Organization org) throws SQLUpdateException {

        String oID = Integer.toString(org.getOrgID());

        bridgeDao.removeOrgsHeadquarters(oID, null);
        bridgeDao.removeOrgsHeroes(oID, null);

        if (org.getMembers() != null) {
            for (Hero h : org.getMembers()) {
                try {
                    heroDao.addHero(h);
                } catch (DuplicateEntryException e) {
                    // skip if not new
                }

                try {
                    bridgeDao.addOrgsHeroes(org.getOrgID(), h.getHeroID());
                } catch (SQLUpdateException e) {
                    // skip if not new
                }
            }
        }

        if (org.getOrgHeadQ() != null) {
            for (Headquarters h : org.getOrgHeadQ()) {
                try {
                    headQDAO.addHeadquarters(h);
                } catch (DuplicateEntryException e) {
                    // skip if not new
                }

                try {
                    bridgeDao.addOrgsHeadquarters(org.getOrgID(), h.getHeadQID());
                } catch (SQLUpdateException e) {
                    // skip if not new
                }
            }
        }

        orgDao.updateOrg(org);
    }


    @Override
    public Power changePower(Power power) throws SQLUpdateException, DuplicateEntryException {
        try {
            power = powerDao.addPower(power);
        } catch (DuplicateEntryException e){
            powerDao.updatePower(power);
        }
        return getFromPowers(Integer.toString(power.getPowerID())).get(0);
    }


    @Override
    public Sighting changeSighting(Sighting sighting) throws SQLUpdateException, DuplicateEntryException {
        try {
            sighting = sightingDao.addSighting(sighting);
        } catch (DuplicateEntryException e){
            // still update
        }

        updateSighting(sighting);
        return getFromSightings(Integer.toString(sighting.getSightingID())).get(0);
    }


    private void updateSighting(Sighting sighting) throws SQLUpdateException {

        bridgeDao.removeSightingsHeroes(Integer.toString(sighting.getSightingID()),null);

        if (sighting.getSightingHeroes() != null) {
            for (Hero h : sighting.getSightingHeroes()) {
                try {
                    heroDao.addHero(h);
                } catch (DuplicateEntryException e) {
                    // skip if not new
                }
                try {
                    bridgeDao.addSightingsHeroes(sighting.getSightingID(), h.getHeroID());
                } catch (Exception e) {
                    // skip if not new
                }
            }
        }

        sightingDao.updateSighting(sighting);
    }

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
    public boolean removeHero(int heroID) throws SQLUpdateException {
        String hID = Integer.toString(heroID);

        bridgeDao.removeSightingsHeroes(null, hID);
        bridgeDao.removePowersHeroes(null, hID);
        bridgeDao.removeOrgsHeroes(null, hID);

        return heroDao.removeHero(heroID);

    }

    @Override
    public boolean removeHeadquarters(int headqID) throws SQLUpdateException {
        String hqID = Integer.toString(headqID);

        contactDao.removeFromContacts(hqID);
        bridgeDao.removeOrgsHeadquarters(null, hqID);

        return headQDAO.removeHeadquarters(headqID);
    }

    @Override
    public boolean removeLocation(int locID) throws SQLUpdateException {
        String lID = Integer.toString(locID);
        List<Sighting> sightList = getFromSightings(null, null, lID);

        for (Sighting s : sightList) {
            removeSighting(s.getSightingID());
        }

        return locDao.removeLocation(locID);
    }

    @Override
    public boolean removeSighting(int sightingID) throws SQLUpdateException {

        bridgeDao.removeSightingsHeroes(Integer.toString(sightingID), null);
        return sightingDao.removeSighting(sightingID);

    }

    @Override
    public boolean removeOrg(int orgID) throws SQLUpdateException {
        String oID = Integer.toString(orgID);

        bridgeDao.removeOrgsHeroes(oID, null);
        bridgeDao.removeOrgsHeadquarters(oID, null);
        return orgDao.removeOrg(orgID);
    }

    @Override
    public boolean removePower(int powerID) throws SQLUpdateException {
        String pID = Integer.toString(powerID);

        bridgeDao.removePowersHeroes(pID, null);
        return powerDao.removePower(powerID);
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
            setHeadquartersOrg(h);
        });
        return headQList;
    }

    @Override
    @Transactional
    public List<Power> getFromPowers(String... args) {
        String[] allArgs = varArgs(args, 3);
        return powerDao.getFromPowers(allArgs[0], allArgs[1], allArgs[2]);
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

    private void setHeadquartersOrg(Headquarters h) {
        List<Organization> objList = new ArrayList<>();
        resLoop(jdbcTemplate.queryForList(HEADQ_ORG_MAP, h.getHeadQID()))
                .forEach((o) -> {
                    objList.add(orgDao.getFromOrgs(o).get(0));
                });
        h.setOrgList(objList);
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


    @Override
    public Contact changeContact(Contact... args) throws SQLUpdateException, DuplicateEntryException {
        if (args.length == 2){
            updateContact(args[0],args[1]);
            return args[1];
        }
        contactDao.addContact(args[0]);
        return args[2];
    }

    @Override
    public boolean removeContact(Contact contact) throws SQLUpdateException {
        return contactDao.removeContact(contact);
    }

    private void updateContact(Contact oldContact, Contact newContact) throws SQLUpdateException, DuplicateEntryException {
        contactDao.updateContact(oldContact, newContact);
    }

    @Override
    public List<Contact> getFromContacts(String... args){
        String[] allArgs = varArgs(args, 2);
        return contactDao.getFromContacts(allArgs[0], allArgs[1]);
    }

}
