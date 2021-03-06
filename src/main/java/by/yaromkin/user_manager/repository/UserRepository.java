package by.yaromkin.user_manager.repository;

import by.yaromkin.user_manager.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserAccount, Long> {

    UserAccount findByUsername(String userName);

}
