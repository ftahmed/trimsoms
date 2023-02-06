package me.ftahmed.bootify.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String firstName;

    @NotNull
    @Size(max = 255)
    private String lastName;

    @NotNull
    @Size(max = 255)
    private String email;

    private LocalDate dob;

    @Size(max = 20)
    private String phoneNumber;

    @NotNull
    private Boolean locked;

    @NotNull
    private Boolean enabled;

    private List<Long> userRoles;

}
