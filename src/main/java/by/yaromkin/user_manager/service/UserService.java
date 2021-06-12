package by.yaromkin.user_manager.service;

import by.yaromkin.user_manager.entity.Role;
import by.yaromkin.user_manager.entity.UserAccount;
import by.yaromkin.user_manager.repository.RoleRepository;
import by.yaromkin.user_manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
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
        UserAccount user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public List<UserAccount> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserAccount> findUserById(String userId) {
        return userRepository.findById(userId);
    }

    public boolean saveUser(UserAccount user) {
        UserAccount userFromDataBase = userRepository.findByUsername(user.getUsername());

        if (userFromDataBase != null) {
            return false;
        }

        user.setRole(Collections.singleton(new Role(2, "ROLE_USER")));
        user.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getInitialPassword()));
        user.setActive(true);
        userRepository.save(user);
        return true;
    }

    public boolean updateUser(UserAccount user) {
        UserAccount userFromDataBase = userRepository.findByUsername(user.getUsername());

        if (userFromDataBase == null) {
            return false;
        }

        user.setActive(!userFromDataBase.isActive());
        userRepository.save(user);
        return true;
    }
}
