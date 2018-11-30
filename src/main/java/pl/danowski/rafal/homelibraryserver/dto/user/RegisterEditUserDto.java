package pl.danowski.rafal.homelibraryserver.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RegisterEditUserDto {

    @NotNull
    private String login;

    @NotNull
    private String email;

    @NotNull
    private String password;

}

