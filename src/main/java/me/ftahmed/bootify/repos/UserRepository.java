package me.ftahmed.bootify.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import me.ftahmed.bootify.domain.User;


public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmailIgnoreCase(String email);

}
