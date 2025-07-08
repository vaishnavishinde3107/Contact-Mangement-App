package com.contactmanager.contact_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.contactmanager.contact_manager.entity.Contact;
import com.contactmanager.contact_manager.service.ContactService;


import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class ContactController {
    @Autowired
    private ContactService contactService;

    //home page
    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("contacts", contactService.getAllContacts());
        return "index";
    }

    //contact form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "add_contact";
    }
    

    //adding a new contact
    @PostMapping("/add")
    public String save(@Valid @ModelAttribute("contact") Contact contact, BindingResult result, Model model) {
        boolean emailDuplicate = contactService.isEmailDuplicate(contact.getEmail());
        boolean phoneDuplicate = contactService.isPhoneDuplicate(contact.getPhone());
        if (result.hasErrors() || emailDuplicate || phoneDuplicate) {
            if (emailDuplicate) {
                model.addAttribute("EmailError", "Email already exists!");
            }
            if (phoneDuplicate) {
                model.addAttribute("PhoneError", "Phone number already exists!");
            }
            return "add_contact";
        }
        //redirect to home page
        contactService.saveContact(contact);
        return "redirect:/";
    }

    // editing the existing contact
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("contact", contactService.getContact(id));
        return "edit_contact";
    }
    
    // updating the contact
    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("contact") Contact contact, BindingResult result, Model model) {
        boolean emailDuplicate = contactService.isEmailDuplicateForOther(contact.getEmail(), contact.getId());
        boolean phoneDuplicate = contactService.isPhoneDuplicateForOther(contact.getPhone(), contact.getId());
        if (result.hasErrors() || emailDuplicate || phoneDuplicate) {
            if (emailDuplicate) {
                model.addAttribute("EmailError", "Email already exists!");
            }
            if (phoneDuplicate) {
                model.addAttribute("PhoneError", "Phone number already exists!");
            }
            return "edit_contact";
        }
        contactService.saveContact(contact);
        return "redirect:/";
    }

    //delete the contact
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        contactService.deleteConatct(id);
        return "redirect:/";
    }
    
    
}
