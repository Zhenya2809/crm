package com.evgeniy.service;

import com.evgeniy.entity.Doctor;
import com.evgeniy.entity.Role;
import com.evgeniy.entity.User;
import com.evgeniy.repository.RoleRepository;
import com.evgeniy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public void saveUser(User user) {

        Optional<User> byUsername = userRepository.findByUsername(user.getUsername());
        if (byUsername.isEmpty()) {
            user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } else {
            throw new RuntimeException("A user with the same name already exists");
        }
    }

    public Doctor findDoctorByLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> byUsername = userRepository.findByUsername(authentication.getName());
        if (byUsername.isPresent()) {
            return byUsername.get().getDoctor();
        } else {
            throw new RuntimeException("Doctor is not found");
        }
    }

    public void deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
        } else {
            throw new RuntimeException("User is not found");
        }

    }

    public List<User> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }

    public User findUserByUsername(String userName) {
        return userRepository.findByUsername(userName).orElseThrow();
    }

    public User createUser(String username, String password, String passwordConfirm) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPasswordConfirm(passwordConfirm);
        return user;
    }
}
