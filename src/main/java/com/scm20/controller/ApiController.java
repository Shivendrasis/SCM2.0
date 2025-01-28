package com.scm20.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm20.entities.Contact;
import com.scm20.services.ContactService;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ContactService contactService;

    // get contacts of user

    @RequestMapping("/contacts/{contactId}")
    public Contact getContact(@PathVariable String contactId) {

        return contactService.getById(contactId);
    }

}
