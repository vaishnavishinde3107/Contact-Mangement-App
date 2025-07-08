package com.contactmanager.contact_manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contactmanager.contact_manager.entity.Contact;
import com.contactmanager.contact_manager.repository.ContactRepo;

@Service
public class ContactService {
    @Autowired
    private ContactRepo contactRepo;

    public List<Contact> getAllContacts(){
        return contactRepo.findAll();
    }

    public Contact getContact(Long id) {
        return contactRepo.findById(id).orElse(null);
    }

    public void saveContact(Contact contact){
        contactRepo.save(contact);
    }

    public void deleteConatct(Long id) {
        contactRepo.deleteById(id);
    }

    public boolean isEmailDuplicate(String email){
        return contactRepo.existsByEmail(email);
    }

    public boolean isEmailDuplicateForOther(String email, Long id) {
        return contactRepo.existsByEmailAndIdNot(email, id);
    }

    public boolean isPhoneDuplicate(String phone) {
        return contactRepo.existsByPhone(phone);
    }

    public boolean isPhoneDuplicateForOther(String phone, Long id) {
        return contactRepo.existsByPhoneAndIdNot(phone, id);
    }
}
