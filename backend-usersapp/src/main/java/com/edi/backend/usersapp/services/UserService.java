package com.edi.backend.usersapp.services;

import com.edi.backend.usersapp.models.dto.UserDto;
import com.edi.backend.usersapp.models.requests.UserRequest;
import com.edi.backend.usersapp.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> findAll();

    Optional<UserDto> findById(Long id);

    UserDto save(User user);
    Optional<UserDto> update(UserRequest user, Long id);

    void remove(Long id);
}
