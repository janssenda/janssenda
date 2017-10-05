package com.dm.contactlist.controller;


import com.dm.contactlist.dao.ContactListDao;
import com.dm.contactlist.model.Contact;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class ContactController {

    ContactListDao dao;

    @Inject
    public ContactController(ContactListDao dao) {
        this.dao = dao;
    }

    @RequestMapping(value="/displayContactsPage", method=RequestMethod.GET)
    public String displayContactsPage(HttpServletRequest request, Model model) {
        String msg = request.getParameter("msg");

        // Get all the Contacts from the DAO
        List<Contact> contactList = dao.getAllContacts();

        // Put the List of Contacts on the Model
        model.addAttribute("contactList", contactList);
        model.addAttribute("message", msg);

        // Return the logical name of our View component
        return "contacts";
    }

    @RequestMapping(value = "/createContact", method = RequestMethod.POST)
    public String createContact(HttpServletRequest request) {

        // grab the incoming values from the form and create a new Contact
        // object

        Contact contact = new Contact();
        contact.setFirstName(request.getParameter("firstName"));
        contact.setLastName(request.getParameter("lastName"));
        contact.setCompany(request.getParameter("company"));
        contact.setPhone(request.getParameter("phone"));
        contact.setEmail(request.getParameter("email"));

        // persist the new Contact
        dao.addContact(contact);

        // we don't want to forward to a View component - we want to
        // redirect to the endpoint that displays the Contacts Page
        // so it can display the new Contact in the table.
        return "redirect:displayContactsPage";
    }

    @RequestMapping(value = "/displayContactDetails", method = RequestMethod.GET)
    public String displayContactDetails(HttpServletRequest request, Model model) {

        String contactIdParameter = request.getParameter("contactId");
        int contactId = Integer.parseInt(contactIdParameter);

        Contact contact = dao.getContactById(contactId);

        model.addAttribute("contact", contact);

        return "contactDetails";
    }

    @RequestMapping(value = "/deleteContact", method = RequestMethod.GET)
    public String deleteContact(HttpServletRequest request) {

        String contactIdParameter = request.getParameter("contactId");
        long contactId = Long.parseLong(contactIdParameter);


        if(dao.removeContact(contactId)){
            return "redirect:displayContactsPage?msg=success";
        } else {
            return "redirect:displayContactsPage?msg=failure";
        }


    }

    @RequestMapping(value = "/displayEditContactForm", method = RequestMethod.GET)
    public String displayEditContactForm(HttpServletRequest request, Model model) {

        String contactIdParameter = request.getParameter("contactId");
        long contactId = Long.parseLong(contactIdParameter);

        Contact contact = dao.getContactById(contactId);
        model.addAttribute("contact", contact);

        return "editContactForm";
    }

    @RequestMapping(value = "/editContact", method = RequestMethod.POST)
    public String editContact(@Valid @ModelAttribute("contact") Contact contact, BindingResult result) {

        if (result.hasErrors()) {
            return "editContactForm";
        }

        dao.updateContact(contact);

        return "redirect:displayContactsPage";
    }

}
