package by.yaromkin.user_manager.repository;

import by.yaromkin.user_manager.entity.UserAccount;
import org.springframework.data.repository.CrudRepository;

public interface UserCrudRepository extends CrudRepository<UserAccount, Long> {

}
