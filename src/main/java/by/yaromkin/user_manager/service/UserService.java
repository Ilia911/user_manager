package by.yaromkin.user_manager.service;

import by.yaromkin.user_manager.entity.Role;
import by.yaromkin.user_manager.entity.UserAccount;
import by.yaromkin.user_manager.repository.UserInsertRepository;
import by.yaromkin.user_manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserInsertRepository userInsertRepository;

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
        return userRepository.findById(Long.parseLong(userId));
    }

    public boolean saveUser(UserAccount user) {
        UserAccount userFromDataBase = userRepository.findByUsername(user.getUsername());

        if (userFromDataBase != null) {
            return false;
        }

        user.setRole(new Role(2, "ROLE_USER"));
        user.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getInitialPassword()));
        user.setActive(true);
        userInsertRepository.insertUserAccountWithQuery(user);
        return true;
    }

    public boolean updateUser(UserAccount user) {

        if (user.getInitialPassword() != null) {
            user.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getInitialPassword()));
        }
        userRepository.save(user);

        return true;
    }
}
