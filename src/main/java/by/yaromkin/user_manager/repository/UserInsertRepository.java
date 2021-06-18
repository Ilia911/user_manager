package by.yaromkin.user_manager.repository;

import by.yaromkin.user_manager.entity.UserAccount;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class UserInsertRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insertUserAccountWithQuery(UserAccount userAccount) {
        entityManager.createNativeQuery("INSERT INTO public.t_user (active, encrypted_password, first_name, " +
                "last_name, username, role_id) VALUES (?, ?, ?, ?, ?, ?)")
                .setParameter(1, userAccount.isActive())
                .setParameter(2, userAccount.getEncryptedPassword())
                .setParameter(3, userAccount.getFirstName())
                .setParameter(4, userAccount.getLastName())
                .setParameter(5, userAccount.getUsername())
                .setParameter(6, userAccount.getRole().getId())
                .executeUpdate();
    }
}
