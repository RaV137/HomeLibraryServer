package pl.danowski.rafal.homelibraryserver.dto.room;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RoomDto {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String colour;

    @NotNull
    private String shortName;

    @NotNull
    private Integer userId;

}
