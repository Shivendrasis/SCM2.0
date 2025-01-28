package com.scm20.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.scm20.entities.Contact;
import com.scm20.entities.User;
import com.scm20.forms.ContactForm;
import com.scm20.helper.Helper;
import com.scm20.helper.Message;
import com.scm20.helper.MessageType;
import com.scm20.services.ContactService;
import com.scm20.services.ImageService;
import com.scm20.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

    private Logger logger = org.slf4j.LoggerFactory.getLogger(ContactController.class);

    @RequestMapping("/add")
    // add contact page:handler
    public String addContactView(Model model) {

        ContactForm contactForm = new ContactForm();

        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }

    // save contact method
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result,
            Authentication authentication, HttpSession session) {

        // process the form data

        // 1 vaidate form
        if (result.hasErrors()) {

            result.getAllErrors().forEach(error -> logger.info(error.toString()));

            session.setAttribute("message", Message.builder().content("Please correct the following errors")
                    .type(MessageType.red).build());
            return "user/add_contact";
        }

        String username = Helper.getEmailOfLoggedlnUser(authentication);

        // conver from into contact
        User user = userService.getUserByEmail(username);

        // 2 process the contact picture
        // image upload code

        Contact contact = new Contact();

        contact.setName(contactForm.getName());
        contact.setFavorite(contactForm.isFavorite());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setUser(user);
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());

        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {

            String filename = UUID.randomUUID().toString();
            String fileURL = imageService.uploadImage(contactForm.getContactImage(), filename);
            contact.setPicture(fileURL);
            contact.setCloudinaryImagePublicId(filename);
        }

        contactService.save(contact);
        System.out.println(contactForm);

        // 3 set the contact picture url

        // 4 set message to be diaplayed on the view
        session.setAttribute("message",
                Message.builder().content("You have successfully added a new contact").type(MessageType.green).build());

        return "redirect:/user/contacts/add";
    }

    // view contacts

    @RequestMapping
    public String viewContacts(Model model, Authentication authentication) {

        // load all user contacts
        String username = Helper.getEmailOfLoggedlnUser(authentication);

        User user = userService.getUserByEmail(username);

        List<Contact> contacts = contactService.getByUser(user);

        model.addAttribute("contacts", contacts);

        return "user/contacts";
    }

    // delete contact
    @RequestMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable String contactId) {
        contactService.delete(contactId);
        return "redirect:/user/contacts";
    }

    // update contact
    @GetMapping("/view/{contactId}")
    public String updateContact(@PathVariable("contactId") String contactId, Model model) {
        var contact = contactService.getById(contactId);

        ContactForm contactForm = new ContactForm();

        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setLinkedInLink(contact.getLinkedInLink());

        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactId", contactId);
        return "user/update_contact_view";
    }

    @RequestMapping(value = "/update/{contactId}", method = RequestMethod.POST)
    public String updateContact(@PathVariable("contactId") String contactId,
            @Valid @ModelAttribute ContactForm contactForm, Model model, BindingResult bindingResult) {

        // update contact
        if (bindingResult.hasErrors()) {
            return "user/update_contact_view";
        }
        var con = new Contact();

        con.setId(contactId);
        con.setName(contactForm.getName());
        con.setEmail(contactForm.getEmail());
        con.setPhoneNumber(contactForm.getPhoneNumber());
        con.setAddress(contactForm.getAddress());
        con.setDescription(contactForm.getDescription());
        con.setFavorite(contactForm.isFavorite());
        con.setWebsiteLink(contactForm.getWebsiteLink());
        con.setLinkedInLink(contactForm.getLinkedInLink());
        // process image

        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            String fileName = UUID.randomUUID().toString();
            String imageURL = imageService.uploadImage(contactForm.getContactImage(), fileName);
            con.setCloudinaryImagePublicId(fileName);
            con.setPicture(imageURL);
            contactForm.setPicture(imageURL);
        }

        var updateCon = contactService.update(con);
        logger.info("updated contact {}", updateCon);

        logger.debug("Received contactId for update: {}", contactId);

        return "redirect:/user/contacts/view/" + contactId;
    }

}
