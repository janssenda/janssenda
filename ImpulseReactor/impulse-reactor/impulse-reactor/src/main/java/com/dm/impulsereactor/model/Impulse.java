package com.dm.impulsereactor.model;

import java.util.List;

public class Impulse {
    private String impulseID, impulseName, creator, packName, description;
    private Cab cab;
    private Speaker speaker;
    private List<Mic> micList;

    public String getImpulseID() {
        return impulseID;
    }

    public void setImpulseID(String impulseID) {
        this.impulseID = impulseID;
    }

    public String getImpulseName() {
        return impulseName;
    }

    public void setImpulseName(String impulseName) {
        this.impulseName = impulseName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Cab getCab() {
        return cab;
    }

    public void setCab(Cab cab) {
        this.cab = cab;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    public List<Mic> getMicList() {
        return micList;
    }

    public void setMicList(List<Mic> micList) {
        this.micList = micList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Impulse impulse = (Impulse) o;

        if (impulseID != null ? !impulseID.equals(impulse.impulseID) : impulse.impulseID != null) return false;
        if (impulseName != null ? !impulseName.equals(impulse.impulseName) : impulse.impulseName != null) return false;
        if (creator != null ? !creator.equals(impulse.creator) : impulse.creator != null) return false;
        if (packName != null ? !packName.equals(impulse.packName) : impulse.packName != null) return false;
        if (description != null ? !description.equals(impulse.description) : impulse.description != null) return false;
        if (cab != null ? !cab.equals(impulse.cab) : impulse.cab != null) return false;
        if (speaker != null ? !speaker.equals(impulse.speaker) : impulse.speaker != null) return false;
        return micList != null ? micList.equals(impulse.micList) : impulse.micList == null;
    }

    @Override
    public int hashCode() {
        int result = impulseID != null ? impulseID.hashCode() : 0;
        result = 31 * result + (impulseName != null ? impulseName.hashCode() : 0);
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        result = 31 * result + (packName != null ? packName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (cab != null ? cab.hashCode() : 0);
        result = 31 * result + (speaker != null ? speaker.hashCode() : 0);
        result = 31 * result + (micList != null ? micList.hashCode() : 0);
        return result;
    }
}
