package com.edi.backend.usersapp.repositories;

import com.edi.backend.usersapp.models.entities.Role;
import com.edi.backend.usersapp.models.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
