package com.dm.dao.db.interfaces;

import com.dm.dao.db.impl.ContactDaoImpl;
import com.dm.exceptions.SQLUpdateException;
import com.dm.model.Contact;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ContactDao {

    public Contact addContact(Contact contact) throws SQLUpdateException;
    public boolean removeContact(Contact contact) throws SQLUpdateException;
    public boolean updateContact(Contact oldContact, Contact newContact) throws SQLUpdateException;
    public List<Contact> getAllContacts();
    public List<Contact> getFromContacts(String... args);
    public List<Contact> getFromContacts(String headQID, String email);
    public boolean ifExists(Contact contact);

}
