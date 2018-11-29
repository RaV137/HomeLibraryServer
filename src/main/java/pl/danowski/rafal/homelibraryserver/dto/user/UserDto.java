package pl.danowski.rafal.homelibraryserver.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class UserDto {

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
