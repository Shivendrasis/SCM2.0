//Used to interct with databases
package com.scm20.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm20.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, String> {

    // add extra methods for db related operation

    Optional<User> findByEmail(String email);

}
