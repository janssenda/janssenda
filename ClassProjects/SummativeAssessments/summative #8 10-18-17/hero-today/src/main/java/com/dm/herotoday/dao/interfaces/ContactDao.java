package com.dm.herotoday.dao.interfaces;

import com.dm.herotoday.exceptions.DuplicateEntryException;
import com.dm.herotoday.exceptions.SQLUpdateException;
import com.dm.herotoday.model.Contact;

import java.util.List;

public interface ContactDao {

    public Contact addContact(Contact contact) throws SQLUpdateException,DuplicateEntryException;
    public boolean removeContact(Contact contact) throws SQLUpdateException;
    public boolean updateContact(Contact oldContact, Contact newContact) throws SQLUpdateException,DuplicateEntryException;
    public List<Contact> getAllContacts();
    public List<Contact> getFromContacts(String... args);
    public List<Contact> getFromContacts(String headQID, String email);
    public boolean ifExists(Contact contact);
    public Integer removeFromContacts(String headQID);

}
