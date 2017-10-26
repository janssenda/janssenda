package com.dm.herotoday.dao.impl;

import com.dm.herotoday.dao.interfaces.ContactDao;
import com.dm.herotoday.exceptions.DuplicateEntryException;
import com.dm.herotoday.exceptions.SQLUpdateException;
import com.dm.herotoday.model.Contact;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Named
public class ContactDaoImpl implements ContactDao {

    private JdbcTemplate jdbcTemplate;

    @Inject
    public ContactDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String ADD_CONTACT_QUERY =
            "INSERT INTO contacts (HeadQID, Email) VALUES(?,?)";
    private static final String EXIST_QUERY =
            "SELECT COUNT(*) FROM contacts WHERE HeadQID = ? AND Email LIKE ?";

    private static final String REMOVE_CONTACT_QUERY =
            "DELETE FROM contacts WHERE HeadQID = ? AND Email LIKE ?";

    private static final String GET_CONTACT_QUERY =
            "SELECT * FROM contacts";

    private static final String GET_CONTACT_QUERY_MULTI =
            "SELECT * FROM contacts WHERE 1 = 1 " +
                    "AND (@HeadQID IS NULL OR HeadQID = @HeadQID) " +
                    "AND (@Email IS NULL OR Email LIKE @Email) ";

    @Override
    @Transactional
    public Contact addContact(Contact contact) throws SQLUpdateException, DuplicateEntryException {

        if (ifExists(contact)){
            throw new DuplicateEntryException("Contact exists, please update.");
        }

        try {
            if (jdbcTemplate.update(ADD_CONTACT_QUERY, contact.getHeadQID(), contact.getEmail()) > 0) {
                return contact;
            }

            throw new SQLUpdateException("There was a problem adding the data or row does not exist");

        } catch (SQLUpdateException | DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    @Override
    public boolean removeContact(Contact contact) throws SQLUpdateException {

        String queryMail = "%"+contact.getEmail()+"%";

        try {
            if (jdbcTemplate.update(REMOVE_CONTACT_QUERY, contact.getHeadQID(), queryMail) <= 0) {
                throw new SQLUpdateException("Data could not be removed or row does not exist");
            } return true;
        } catch (DataIntegrityViolationException e) {
            throw new SQLUpdateException(e.getMessage());
        }
    }

    @Override
    public boolean updateContact(Contact oldContact, Contact newContact) throws SQLUpdateException, DuplicateEntryException {
        try {
            removeContact(oldContact);
            addContact(newContact);
            return true;
        } catch (DataIntegrityViolationException e) {
            if(!ifExists(oldContact)){
                addContact(oldContact);
            }
            throw new SQLUpdateException(e.getMessage());
        }
    }

    @Override
    public List<Contact> getAllContacts() {
        return jdbcTemplate.query(GET_CONTACT_QUERY, new ContactMapper());
    }

    @Override
    public List<Contact> getFromContacts(String... args) {
        String[] allArgs = {null, null};
        System.arraycopy(args, 0, allArgs, 0, args.length);
        return getFromContacts(allArgs[0], allArgs[1]);
    }

    @Override
    @Transactional
    public List<Contact> getFromContacts(String headQID, String email) {

        if (headQID == null || headQID.isEmpty()) { headQID = null;}
        if (email == null || email.isEmpty()) {
            email = null;
        } else email = "%"+email+"%";

        String setup = "SET @HeadQID = ?, @Email = ?; ";

        try {
            Connection c = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement ps = c.prepareStatement(setup);

            ps.setString(1, headQID);
            ps.setString(2, email);



            String qdata = ps.toString().split(":")[1].trim();
            qdata = qdata.split("]")[0].trim();
            c.close();
            // Execute the prepared statement string to set the variables
            jdbcTemplate.execute(qdata);

            // Search for contacts based on the given criteria,
            return jdbcTemplate.query(GET_CONTACT_QUERY_MULTI, new ContactMapper());

        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Integer removeFromContacts(String headQID){
        String DELETE_CON_QUERY =
                "DELETE FROM contacts WHERE HeadQID = ?";

        return jdbcTemplate.update(DELETE_CON_QUERY,headQID);
    }

    @Override
    public boolean ifExists(Contact contact) {
        String queryMail = "%"+contact.getEmail()+"%";
        return (jdbcTemplate.queryForObject(EXIST_QUERY, Integer.class, contact.getHeadQID(), queryMail) > 0);
    }

    private static final class ContactMapper implements RowMapper<Contact> {

        public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {

            Contact c = new Contact();
            c.setHeadQID(rs.getInt("HeadQID"));
            c.setEmail(rs.getString("Email"));
            return c;
        }
    }


}
