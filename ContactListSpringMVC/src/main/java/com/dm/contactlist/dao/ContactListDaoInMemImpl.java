package com.dm.contactlist.dao;

import com.dm.contactlist.model.Contact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContactListDaoInMemImpl  implements ContactListDao {

    // holds all of our Contact objects - simulates the database
    private Map<Long, Contact> contactMap = new HashMap<>();

    // used to assign ids to Contacts - simulates the auto increment
    // primary key for Contacts in a database
    private static long contactIdCounter = 0;

    @Override
    public Contact addContact(Contact contact) {
        contact.setContactId(contactIdCounter);
        contactIdCounter++;

        contactMap.put(contact.getContactId(), contact);
        return contact;
    }

    @Override
    public void removeContact(long contactId) {
        if (contactMap.containsKey(contactId)) {
            contactMap.remove(contactId);
        }
    }

    @Override
    public void updateContact(Contact contact) {
        contactMap.put(contact.getContactId(), contact);
    }

    @Override
    public List<Contact> getAllContacts() {
        return new ArrayList<>(contactMap.values());
    }

    @Override
    public Contact getContactById(long contactId) {
        return contactMap.get(contactId);
    }

    @Override
    public List<Contact> searchContacts(Map<SearchTerm, String> criteria) {
        String firstNameSearchCriteria =
                criteria.get(SearchTerm.FIRST_NAME);
        String lastNameSearchCriteria =
                criteria.get(SearchTerm.LAST_NAME);
        String companySearchCriteria =
                criteria.get(SearchTerm.COMPANY);
        String phoneSearchCriteria =
                criteria.get(SearchTerm.PHONE);
        String emailSearchCriteria =
                criteria.get(SearchTerm.EMAIL);


        return null;
    }
}
