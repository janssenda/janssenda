package com.dm.contactlist.dao;

import com.dm.contactlist.model.Contact;

import java.util.List;
import java.util.Map;

public class ContactListDaoDbImpl implements ContactListDao {

    private static final String SQL_INSERT_CONTACT =
            "INSERT INTO contacts " +
                    "(first_name, last_name, company, phone, email) values (?,?,?,?,?)";

    private static final String SQL_DELETE_CONTACT =
            "DELETE FROM contacts WHERE contact_id = ?";

    private static final String SQL_UPDATE_CONTACT =
            "UPDATE contacts SET first_name = ?, last_name = ?, company = ?," +
                    "phone = ?, email = ? WHERE contact_id = ?";

    private static final String SQL_SELECT_CONTACT =
            "SELECT * FROM contacts WHERE contact_id = ? ";

    private static final String SQL_SELECT_ALL_CONTACTS =
            "SELECT * FROM contacts";

    private static final String SQL_SELECT_CONTACTS_BY_LAST_NAME =
            "SELECT * FROM contacts WHERE last_name = ?";

    private static final String SQL_SELECT_CONTACTS_BY_COMPANY =
            "SELECT * FROM contacts WHERE company = ?";


    @Override
    public Contact addContact(Contact contact) {
        return null;
    }

    @Override
    public boolean removeContact(long contactId) {
        return false;
    }

    @Override
    public void updateContact(Contact contact) {

    }

    @Override
    public List<Contact> getAllContacts() {
        return null;
    }

    @Override
    public Contact getContactById(long contactId) {
        return null;
    }

    @Override
    public List<Contact> searchContacts(Map<SearchTerm, String> criteria) {
        return null;
    }
}
