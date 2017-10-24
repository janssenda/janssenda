package com.dm.herotoday.model;

public class Contact implements Cloneable {
    private int headQID;
    private String email;

    public int getHeadQID() {
        return headQID;
    }

    public void setHeadQID(int headQID) {
        this.headQID = headQID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Contact clone(){
        try {
            return (Contact) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (headQID != contact.headQID) return false;
        return email != null ? email.equals(contact.email) : contact.email == null;
    }

    @Override
    public int hashCode() {
        int result = headQID;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
