package me.ftahmed.bootify.service;

import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import me.ftahmed.bootify.domain.Role;
import me.ftahmed.bootify.domain.User;
import me.ftahmed.bootify.model.UserDTO;
import me.ftahmed.bootify.repos.RoleRepository;
import me.ftahmed.bootify.repos.UserRepository;
import me.ftahmed.bootify.util.NotFoundException;

import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(final UserRepository userRepository, final RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .collect(Collectors.toList());
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setDob(user.getDob());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setLocked(user.getLocked());
        userDTO.setEnabled(user.getEnabled());
        userDTO.setUserRoles(user.getUserRoleRoles() == null ? null : user.getUserRoleRoles().stream()
                .map(Role::getId)
                .collect(Collectors.toList()));
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setDob(userDTO.getDob());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setLocked(userDTO.getLocked());
        user.setEnabled(userDTO.getEnabled());
        final List<Role> userRoles = roleRepository.findAllById(
                userDTO.getUserRoles() == null ? Collections.emptyList() : userDTO.getUserRoles());
        if (userRoles.size() != (userDTO.getUserRoles() == null ? 0 : userDTO.getUserRoles().size())) {
            throw new NotFoundException("one of userRoles not found");
        }
        user.setUserRoleRoles(userRoles.stream().collect(Collectors.toSet()));
        return user;
    }

    public boolean usernameExists(final String username) {
        return userRepository.existsByUsernameIgnoreCase(username);
    }

    public boolean emailExists(final String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

}
