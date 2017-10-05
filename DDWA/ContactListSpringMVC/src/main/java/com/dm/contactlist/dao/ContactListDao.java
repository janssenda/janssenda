package com.dm.contactlist.dao;

import com.dm.contactlist.model.Contact;

import java.util.List;
import java.util.Map;

public interface ContactListDao {

        // add the given Contact to the data store
        Contact addContact(Contact contact);

        // remove the Contact with the given id from the data store
        boolean removeContact(long contactId);

        // update the given Contact in the data store
        void updateContact(Contact contact);

        // retrieve all Contacts from the data store
        List<Contact> getAllContacts();

        // retrieve the Contact with the given id from the data store
        Contact getContactById(long contactId);

        // search for Contacts by the given search criteria values
        List<Contact> searchContacts(Map<SearchTerm, String> criteria);

}