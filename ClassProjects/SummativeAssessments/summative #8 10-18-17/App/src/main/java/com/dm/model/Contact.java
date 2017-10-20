package com.dm.model;

public class Contact {
    private int HeadQID;
    private String contactEmail;

    public int getHeadQID() {
        return HeadQID;
    }

    public void setHeadQID(int headQID) {
        HeadQID = headQID;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (HeadQID != contact.HeadQID) return false;
        return contactEmail != null ? contactEmail.equals(contact.contactEmail) : contact.contactEmail == null;
    }

    @Override
    public int hashCode() {
        int result = HeadQID;
        result = 31 * result + (contactEmail != null ? contactEmail.hashCode() : 0);
        return result;
    }
}
