package com.dm.contactlist.controller;

import com.dm.contactlist.dao.ContactListDao;
import com.dm.contactlist.model.Contact;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@Controller
public class RESTController {

    private ContactListDao dao;

    @Inject
    public RESTController(ContactListDao dao) {
        this.dao = dao;
    }


    @RequestMapping(value = "/contact/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Contact getContact(@PathVariable("id") long id) {
        return dao.getContactById(id);
    }

    @RequestMapping(value = "/contact", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Contact createContact(@Valid @RequestBody Contact contact) {
        return dao.addContact(contact);
    }

    @RequestMapping(value = "/contact/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContact(@PathVariable("id") long id) {
        dao.removeContact(id);
    }

    @RequestMapping(value = "/contact/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateContact(@PathVariable("id") long id, @Valid @RequestBody Contact contact)
            throws UpdateIntegrityException {

        if (id != contact.getContactId()) {
            throw new UpdateIntegrityException("Contact Id on URL must match Contact Id in submitted data.");
        }

        dao.updateContact(contact);
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    @ResponseBody
    public List<Contact> getAllContacts() {
        return dao.getAllContacts();
    }

}


