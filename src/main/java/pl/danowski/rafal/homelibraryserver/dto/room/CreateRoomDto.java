package pl.danowski.rafal.homelibraryserver.dto.room;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateRoomDto {

    @NotNull
    private String name;

    @NotNull
    private String colour;

    @NotNull
    private String shortName;

    @NotNull
    private String userId;

}
