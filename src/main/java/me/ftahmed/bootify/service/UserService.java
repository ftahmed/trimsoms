package me.ftahmed.bootify.service;

import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import me.ftahmed.bootify.domain.Role;
import me.ftahmed.bootify.domain.User;
import me.ftahmed.bootify.model.UserDto;
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

    public List<UserDto> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDto(user, new UserDto()))
                .collect(Collectors.toList());
    }

    public UserDto get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDto(user, new UserDto()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserDto userDto) {
        final User user = new User();
        mapToEntity(userDto, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDto userDto) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDto, user);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    private UserDto mapToDto(final User user, final UserDto userDto) {
        userDto.setId(user.getId());
        userDto.setPassword("********");
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setDob(user.getDob());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setLocked(user.getLocked());
        userDto.setEnabled(user.getEnabled());
        userDto.setRoles(user.getRoles() == null ? null : user.getRoles().stream()
                .map(Role::getId)
                .collect(Collectors.toList()));
        return userDto;
    }

    private User mapToEntity(final UserDto userDto, final User user) {
        user.setUsername(userDto.getUsername());
        if (userDto.getPassword() != null && !"********".equals(userDto.getPassword()))
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setDob(userDto.getDob());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setLocked(userDto.getLocked());
        user.setEnabled(userDto.getEnabled());
        final List<Role> userRoles = roleRepository.findAllById(
                userDto.getRoles() == null ? Collections.emptyList() : userDto.getRoles());
        if (userRoles.size() != (userDto.getRoles() == null ? 0 : userDto.getRoles().size())) {
            throw new NotFoundException("one of user roles not found");
        }
        user.setRoles(userRoles.stream().collect(Collectors.toSet()));
        return user;
    }

    public boolean usernameExists(final String username) {
        return userRepository.existsByUsernameIgnoreCase(username);
    }

    public boolean emailExists(final String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

}
