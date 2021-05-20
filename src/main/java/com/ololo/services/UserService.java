package com.ololo.services;

import com.ololo.model.Role;
import com.ololo.model.User;
import com.ololo.repositories.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ololo.repositories.UsersRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private UsersRepository usersRepository;
    private RolesRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

//    @PersistenceContext
//    private EntityManager em;

    public UserService(RolesRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = usersRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", email));

        }
        return user;
    }

    @Transactional
    public List<User> findAll() {
        return usersRepository.findAll();
    }

    @Transactional
    public User findOne(int id) {
        Optional<User> foundPerson = usersRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public User findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public void saveUser(User user) {
        user.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    @Transactional
    public void update(int id, User updatedUser) {
        updatedUser.setId(id);
        updatedUser.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));
        updatedUser.setPassword(bCryptPasswordEncoder.encode(updatedUser.getPassword()));
        usersRepository.save(updatedUser);
    }

    @Transactional
    public void delete(int id) {
        usersRepository.deleteById(id);
    }

}
