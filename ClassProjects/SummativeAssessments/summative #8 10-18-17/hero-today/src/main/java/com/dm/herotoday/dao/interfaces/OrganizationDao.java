package com.dm.herotoday.dao.interfaces;

import com.dm.herotoday.exceptions.DuplicateEntryException;
import com.dm.herotoday.exceptions.SQLUpdateException;
import com.dm.herotoday.model.Organization;

import java.util.List;

public interface OrganizationDao {

    public Organization addOrg(Organization org) throws SQLUpdateException,DuplicateEntryException;
    public boolean removeOrg(int orgID) throws SQLUpdateException;
    public boolean updateOrg(Organization org) throws SQLUpdateException;
    public List<Organization> getAllOrgs();
    public List<Organization> getFromOrgs(String... args);
    public List<Organization> getFromOrgs(String orgID, String orgName, String description);
    public boolean ifExists(int orgID);
}
