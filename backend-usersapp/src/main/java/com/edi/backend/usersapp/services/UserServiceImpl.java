package com.edi.backend.usersapp.services;

import com.edi.backend.usersapp.models.IUser;
import com.edi.backend.usersapp.models.dto.UserDto;
import com.edi.backend.usersapp.models.dto.mapper.DtoMapperUser;
import com.edi.backend.usersapp.models.entities.Role;
import com.edi.backend.usersapp.models.requests.UserRequest;
import com.edi.backend.usersapp.models.entities.User;
import com.edi.backend.usersapp.repositories.RoleRepository;
import com.edi.backend.usersapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        List<User> users = (List<User>) repository.findAll();

        return users
                .stream()
                .map(user -> DtoMapperUser.builder().setUser(user).build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> findById(Long id) {
        return repository.findById(id).map(u -> DtoMapperUser
                .builder()
                .setUser(u)
                .build());
    }

    @Override
    @Transactional
    public UserDto save(User user) {

        String passwordBCrypt = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordBCrypt);
        user.setRoles(getRoles(user));
        return DtoMapperUser.builder().setUser(repository.save(user)).build();
    }

    @Override
    @Transactional
    public Optional<UserDto> update(UserRequest user, Long id) {
        Optional<User> o = repository.findById(id);
        User newUser = null;
        if (o.isPresent()) {
            User userDb = o.orElseThrow();
            userDb.setRoles(getRoles(user));
            userDb.setUsername(user.getUsername());
            userDb.setEmail(user.getEmail());
            newUser = repository.save(userDb);
        }
        return Optional.ofNullable(DtoMapperUser.builder().setUser(newUser).build());
    }

    @Override
    @Transactional
    public void remove(Long id) {
        repository.deleteById(id);
    }

    private List<Role> getRoles(IUser user) {
        Optional<Role> roleUsuario = roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        if (roleUsuario.isPresent()) {
            roles.add(roleUsuario.orElseThrow());
        }
        if (user.isAdmin()) {
            Optional<Role> oa = roleRepository.findByName("ROLE_ADMIN");
            if (oa.isPresent()) {
                roles.add(oa.orElseThrow());
            }
        }
        return roles;
    }
}
