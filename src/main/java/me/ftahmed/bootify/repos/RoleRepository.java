package me.ftahmed.bootify.repos;

import me.ftahmed.bootify.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByRoleName(String roleName);

}
