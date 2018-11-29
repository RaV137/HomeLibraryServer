package pl.danowski.rafal.homelibraryserver.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EditUserDto {

    @NotNull
    @Id
    private Integer id;

    @NotNull
    private String login;

    @NotNull
    private String email;

    @NotNull
    private String password;
}
