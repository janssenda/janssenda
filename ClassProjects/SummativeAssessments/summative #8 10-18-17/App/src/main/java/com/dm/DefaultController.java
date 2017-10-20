package com.dm;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Controller
public class DefaultController {

    @RequestMapping({"/", "", "/home"})
    public String welcomeMap() {
        return "index";
    }

    @RequestMapping(value="/returnRequest",method= RequestMethod.POST)
    public String returnRequest(HttpServletRequest request, Map<String, Object> model) {

        String userInput = request.getParameter("userInput");
        model.put("userInput", userInput);

        return "index";
    }

//    private JdbcTemplate jdbcTemplate;
//
//    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    @Override
//    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
//    public Contact addContact(Contact contact) {
//
//        jdbcTemplate.update(SQL_INSERT_CONTACT,
//                contact.getFirstName(),
//                contact.getLastName(),
//                contact.getCompany(),
//                contact.getPhone(),
//                contact.getContactEmail());
//
//        // query the database for the id that was just assigned to the new
//        // row in the database
//        int newId = jdbcTemplate.queryForObject(
//                "SELECT LAST_INSERT_ID()",
//                Integer.class);
//
//        // set the new id value on the contact object and return it
//        contact.setContactId(newId);
//        return contact;
//
//    }


//    private static final class ContactMapper implements RowMapper<Contact> {
//
//        public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
//            Contact contact = new Contact();
//            contact.setContactId(rs.getLong("contact_id"));
//            return contact;
//        }
//    }

}




