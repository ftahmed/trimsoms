package me.ftahmed.bootify.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import me.ftahmed.bootify.domain.Role;
import me.ftahmed.bootify.model.RoleDto;
import me.ftahmed.bootify.repos.RoleRepository;
import me.ftahmed.bootify.util.NotFoundException;
import me.ftahmed.bootify.util.WebUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleDto> findAll() {
        final List<Role> roles = roleRepository.findAll(Sort.by("id"));
        return roles.stream()
                .map((role) -> mapToDto(role, new RoleDto()))
                .collect(Collectors.toList());
    }

    public RoleDto get(final Long id) {
        return roleRepository.findById(id)
                .map(role -> mapToDto(role, new RoleDto()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final RoleDto roleDto) {
        final Role role = new Role();
        mapToEntity(roleDto, role);
        return roleRepository.save(role).getId();
    }

    public void update(final Long id, final RoleDto roleDto) {
        final Role role = roleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(roleDto, role);
        roleRepository.save(role);
    }

    public void delete(final Long id) {
        roleRepository.deleteById(id);
    }

    private RoleDto mapToDto(final Role role, final RoleDto roleDto) {
        roleDto.setId(role.getId());
        roleDto.setRoleName(role.getRoleName());
        roleDto.setEnabled(role.getEnabled());
        return roleDto;
    }

    private Role mapToEntity(final RoleDto roleDto, final Role role) {
        role.setRoleName(roleDto.getRoleName());
        role.setEnabled(roleDto.getEnabled());
        return role;
    }

    public boolean roleNameExists(final String roleName) {
        return roleRepository.existsByRoleName(roleName);
    }

    @Transactional
    public String getReferencedWarning(final Long id) {
        final Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());
        if (!role.getUsers().isEmpty()) {
            return WebUtils.getMessage("role.user.manyToMany.referenced", role.getUsers().iterator().next().getId());
        }
        return null;
    }

}
