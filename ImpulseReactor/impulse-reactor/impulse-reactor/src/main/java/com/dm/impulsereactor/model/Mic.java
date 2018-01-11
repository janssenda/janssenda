package com.dm.impulsereactor.model;

public class Mic {
    private String micID, micModel, micBrand;

    public String getMicID() {
        return micID;
    }

    public void setMicID(String micID) {
        this.micID = micID;
    }

    public String getMicModel() {
        return micModel;
    }

    public void setMicModel(String micModel) {
        this.micModel = micModel;
    }

    public String getMicBrand() {
        return micBrand;
    }

    public void setMicBrand(String micBrand) {
        this.micBrand = micBrand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mic mic = (Mic) o;

        if (micID != null ? !micID.equals(mic.micID) : mic.micID != null) return false;
        if (micModel != null ? !micModel.equals(mic.micModel) : mic.micModel != null) return false;
        return micBrand != null ? micBrand.equals(mic.micBrand) : mic.micBrand == null;
    }

    @Override
    public int hashCode() {
        int result = micID != null ? micID.hashCode() : 0;
        result = 31 * result + (micModel != null ? micModel.hashCode() : 0);
        result = 31 * result + (micBrand != null ? micBrand.hashCode() : 0);
        return result;
    }
}
