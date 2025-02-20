package com.scm20.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm20.entities.Contact;
import com.scm20.entities.User;

@Repository
public interface ContactRepo extends JpaRepository<Contact, String> {

    // find the contact by user method
    // custom finder method
    List<Contact> findByUser(User user);

    // custom query method
    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
    List<Contact> findByUserId(@Param("userId") String userId);

}
