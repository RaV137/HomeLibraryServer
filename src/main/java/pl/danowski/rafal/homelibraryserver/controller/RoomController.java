package pl.danowski.rafal.homelibraryserver.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.danowski.rafal.homelibraryserver.dto.room.CreateRoomDto;
import pl.danowski.rafal.homelibraryserver.dto.room.RoomDto;
import pl.danowski.rafal.homelibraryserver.model.Room;
import pl.danowski.rafal.homelibraryserver.service.interfaces.IRoomService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private IRoomService service;

    @Autowired
    private final MapperFacade mapper;

    @GetMapping("/user/{login}")
    @ApiOperation("Find all rooms by user login")
    public ResponseEntity<List<RoomDto>> getAllByLogin(@PathVariable("login") String login) {
        List<Room> rooms = service.findByUserLogin(login);
        return new ResponseEntity<>(rooms.stream().map(this::convertToDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation("Find a room by id")
    public ResponseEntity<RoomDto> getByEmail(@PathVariable("id") Integer id) {
        Room room = service.getById(id);
        return new ResponseEntity<>(convertToDto(room), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Create new room")
    public ResponseEntity<RoomDto> createNewRoom(@Valid @RequestBody CreateRoomDto createRoom) {
        Room room = convertFromDto(createRoom);
        Room registeredRoom = service.createRoom(room);
        return new ResponseEntity<>(convertToDto(registeredRoom), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @ApiOperation("Update room")
    public ResponseEntity<RoomDto> updateRoom(@PathVariable("id") Integer id, @Valid @RequestBody RoomDto roomDto) {
        Room roomToUpdate = service.getById(id);
        mapper.map(roomDto, roomToUpdate);
        Room updatedRoom = service.updateRoom(roomToUpdate);
        return new ResponseEntity<>(convertToDto(updatedRoom), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete room")
    public ResponseEntity<RoomDto> deleteRoom(@PathVariable("id") Integer id) {
        Room room = service.deleteRoom(id);
        return new ResponseEntity<>(convertToDto(room), HttpStatus.OK);
    }

    private RoomDto convertToDto(Room room) {
        return mapper.map(room, RoomDto.class);
    }

    private Room convertFromDto(CreateRoomDto dto) {
        return mapper.map(dto, Room.class);
    }

}
