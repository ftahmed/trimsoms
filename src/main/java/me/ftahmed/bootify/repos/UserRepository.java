package me.ftahmed.bootify.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import me.ftahmed.bootify.domain.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    User findByEmail(String email);

    boolean existsByUsernameIgnoreCase(String username);
    boolean existsByEmailIgnoreCase(String email);

}
