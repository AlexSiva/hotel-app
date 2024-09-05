package com.petproject.app.service;

import com.petproject.app.exception.UserAlreadyExistsException;
import com.petproject.app.model.Role;
import com.petproject.app.model.User;
import com.petproject.app.repository.RoleRepository;
import com.petproject.app.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@RequiredArgsConstructor
@Service
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    @Transactional
    @Override
    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())){
            throw new UserAlreadyExistsException(user.getEmail() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        roleRepository.findByName("ROLE_USER")
                .ifPresentOrElse(
                        userRole -> user.setRoles(Collections.singletonList(userRole)),
                        () -> {
                            // Role not found, create it
                            Role newRole = new Role("ROLE_USER");
                            roleRepository.save(newRole);
                            user.setRoles(Collections.singletonList(newRole));
                        }
                );
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteUser(String email) {
        User theUser = getUser(email);
        if (theUser != null){
            userRepository.deleteByEmail(email);
        }
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("user doesn't exists"));
    }
}
