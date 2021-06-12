package by.yaromkin.user_manager.repository;

import by.yaromkin.user_manager.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public interface UserRepository extends JpaRepository<UserAccount, Long> {

    UserAccount findByUsername(String userName);



    @Query(value = "INSERT INTO public.t_user(active, encrypted_password, first_name, last_name, username) " +
            "VALUES (:active, :encrypted_password, :first_name, :last_name, :username)", nativeQuery = true)
    void save(@Param("username") String username, @Param("encrypted_password") String encryptedPassword,
              @Param("first_name") String firstName, @Param("last_name") String lastName,
              @Param("active") boolean active);

}
/*
@Query(value = "insert into Users values (:name, :age, :email, :status)", nativeQuery = true)
void save(@Param("name") String name, @Param("age") Integer age, @Param("status") Integer status, @Param("email") String email);*/
