package by.yaromkin.user_manager.repository;

import by.yaromkin.user_manager.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
