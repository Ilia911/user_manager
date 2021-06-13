package by.yaromkin.user_manager.repository;

import by.yaromkin.user_manager.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserAccount, Long> {

    UserAccount findByUsername(String userName);

    @Query(value = "INSERT INTO public.t_user(active, encrypted_password, first_name, last_name, username, role_id) " +
            "VALUES (:active, :encrypted_password, :first_name, :last_name, :username, :role_id)", nativeQuery = true)
    void save(@Param("username") String username, @Param("encrypted_password") String encryptedPassword,
              @Param("first_name") String firstName, @Param("last_name") String lastName,
              @Param("active") boolean active, @Param("role_id") Integer roleId);
}
