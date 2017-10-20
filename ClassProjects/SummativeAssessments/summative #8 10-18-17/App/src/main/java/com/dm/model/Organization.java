package com.dm.model;

import java.util.List;

public class Organization {

    private int orgID;
    private String orgName;
    private String description;
    private List<Hero> members;
    private List<Headquarters> orgHeadQ;

    public List<Hero> getMembers() {
        return members;
    }

    public void setMembers(List<Hero> members) {
        this.members = members;
    }

    public List<Headquarters> getOrgHeadQ() {
        return orgHeadQ;
    }

    public void setOrgHeadQ(List<Headquarters> orgHeadQ) {
        this.orgHeadQ = orgHeadQ;
    }

    public int getOrgID() {
        return orgID;
    }

    public void setOrgID(int orgID) {
        this.orgID = orgID;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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

        Organization that = (Organization) o;

        if (orgID != that.orgID) return false;
        if (orgName != null ? !orgName.equals(that.orgName) : that.orgName != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (members != null ? !members.equals(that.members) : that.members != null) return false;
        return orgHeadQ != null ? orgHeadQ.equals(that.orgHeadQ) : that.orgHeadQ == null;
    }

    @Override
    public int hashCode() {
        int result = orgID;
        result = 31 * result + (orgName != null ? orgName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (members != null ? members.hashCode() : 0);
        result = 31 * result + (orgHeadQ != null ? orgHeadQ.hashCode() : 0);
        return result;
    }
}


