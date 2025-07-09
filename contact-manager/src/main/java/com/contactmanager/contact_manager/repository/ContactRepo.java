package com.contactmanager.contact_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contactmanager.contact_manager.entity.Contact;

public interface ContactRepo extends JpaRepository<Contact, Long>{
    boolean existsByEmail(String email); // check if the email already exists in the repo
    boolean existsByEmailAndIdNot(String email, Long id); // check if the email exists for another contact
    boolean existsByPhone(String phone); // check if the phone already exists in the repo
    boolean existsByPhoneAndIdNot(String phone, Long id); // check if the phone exists for another contact
}
