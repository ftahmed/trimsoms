package me.ftahmed.bootify.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RoleDTO {

    private Long id;

    @NotNull
    private String roleName;

    @NotNull
    private Boolean enabled;

}
