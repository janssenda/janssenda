package com.dm.impulsereactor.model;

public class Speaker {
    private String speakerID, speakerModel, speakerBrand, speakerName;

    public String getSpeakerID() {
        return speakerID;
    }

    public void setSpeakerID(String speakerID) {
        this.speakerID = speakerID;
    }

    public String getSpeakerModel() {
        return speakerModel;
    }

    public void setSpeakerModel(String speakerModel) {
        this.speakerModel = speakerModel;
    }

    public String getSpeakerBrand() {
        return speakerBrand;
    }

    public void setSpeakerBrand(String speakerBrand) {
        this.speakerBrand = speakerBrand;
    }

    public String getSpeakerName() {
        return speakerName;
    }

    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Speaker speaker = (Speaker) o;

        if (speakerID != null ? !speakerID.equals(speaker.speakerID) : speaker.speakerID != null) return false;
        if (speakerModel != null ? !speakerModel.equals(speaker.speakerModel) : speaker.speakerModel != null)
            return false;
        if (speakerBrand != null ? !speakerBrand.equals(speaker.speakerBrand) : speaker.speakerBrand != null)
            return false;
        return speakerName != null ? speakerName.equals(speaker.speakerName) : speaker.speakerName == null;
    }

    @Override
    public int hashCode() {
        int result = speakerID != null ? speakerID.hashCode() : 0;
        result = 31 * result + (speakerModel != null ? speakerModel.hashCode() : 0);
        result = 31 * result + (speakerBrand != null ? speakerBrand.hashCode() : 0);
        result = 31 * result + (speakerName != null ? speakerName.hashCode() : 0);
        return result;
    }
}
