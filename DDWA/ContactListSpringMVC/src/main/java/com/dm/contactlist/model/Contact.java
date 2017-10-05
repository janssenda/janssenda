package com.dm.contactlist.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;


public class Contact {


    private long contactId;
    @NotEmpty(message = "You must supply a value for First Name.")
    @Length(max = 50, message = "First Name must be no more than 50 characters in length.")

    private String firstName;
    @NotEmpty(message = "You must supply a value for Last Name.")
    @Length(max = 50, message = "Last Name must be no more than 50 characters in length.")

    private String lastName;
    @NotEmpty(message = "You must supply a value for Company.")
    @Length(max = 50, message = "Company must be no more than 50 characters in length.")

    private String company;
    @NotEmpty(message = "You must supply a value for Phone.")
    @Length(max = 10, message = "Phone must be no more than 10 characters in length.")

    private String phone;

    @Email(message = "Please enter a valid email address.")
    @Length(max = 50, message = "Email must be no more than 50 characters in length.")
    private String email;



    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (contactId != contact.contactId) return false;
        if (firstName != null ? !firstName.equals(contact.firstName) : contact.firstName != null) return false;
        if (lastName != null ? !lastName.equals(contact.lastName) : contact.lastName != null) return false;
        if (company != null ? !company.equals(contact.company) : contact.company != null) return false;
        if (phone != null ? !phone.equals(contact.phone) : contact.phone != null) return false;
        return email != null ? email.equals(contact.email) : contact.email == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (contactId ^ (contactId >>> 32));
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

}
