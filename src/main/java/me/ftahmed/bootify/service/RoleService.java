package me.ftahmed.bootify.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import me.ftahmed.bootify.domain.Role;
import me.ftahmed.bootify.model.RoleDTO;
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

    public List<RoleDTO> findAll() {
        final List<Role> roles = roleRepository.findAll(Sort.by("id"));
        return roles.stream()
                .map((role) -> mapToDTO(role, new RoleDTO()))
                .collect(Collectors.toList());
    }

    public RoleDTO get(final Long id) {
        return roleRepository.findById(id)
                .map(role -> mapToDTO(role, new RoleDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final RoleDTO roleDTO) {
        final Role role = new Role();
        mapToEntity(roleDTO, role);
        return roleRepository.save(role).getId();
    }

    public void update(final Long id, final RoleDTO roleDTO) {
        final Role role = roleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(roleDTO, role);
        roleRepository.save(role);
    }

    public void delete(final Long id) {
        roleRepository.deleteById(id);
    }

    private RoleDTO mapToDTO(final Role role, final RoleDTO roleDTO) {
        roleDTO.setId(role.getId());
        roleDTO.setRoleName(role.getRoleName());
        roleDTO.setEnabled(role.getEnabled());
        return roleDTO;
    }

    private Role mapToEntity(final RoleDTO roleDTO, final Role role) {
        role.setRoleName(roleDTO.getRoleName());
        role.setEnabled(roleDTO.getEnabled());
        return role;
    }

    public boolean roleNameExists(final String roleName) {
        return roleRepository.existsByRoleName(roleName);
    }

    @Transactional
    public String getReferencedWarning(final Long id) {
        final Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());
        if (!role.getUserRoleUsers().isEmpty()) {
            return WebUtils.getMessage("role.user.manyToMany.referenced", role.getUserRoleUsers().iterator().next().getId());
        }
        return null;
    }

}
