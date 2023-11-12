package com.edi.backend.usersapp.repositories;

import com.edi.backend.usersapp.models.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);  //Consulta mediante palabras reservadas JPA

    @Query("select u from User u where u.username=?1")   //Consulta personalizada mediante Query
    Optional<User> getUserByUsername(String username);
}
