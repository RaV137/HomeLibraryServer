package pl.danowski.rafal.homelibraryserver.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.danowski.rafal.homelibraryserver.dto.user.EditUserDto;
import pl.danowski.rafal.homelibraryserver.dto.user.RegisterUserDto;
import pl.danowski.rafal.homelibraryserver.dto.user.UserDto;
import pl.danowski.rafal.homelibraryserver.model.User;
import pl.danowski.rafal.homelibraryserver.service.interfaces.IUserService;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final IUserService service;

    @Autowired
    private final MapperFacade mapper;

    @GetMapping("/login/{login}")
    @ApiOperation("Find a user by login")
    public ResponseEntity<UserDto> getByLogin(@PathVariable("login") String login) {
        User user = service.getByLogin(login);
        return new ResponseEntity<>(convertToDto(user), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    @ApiOperation("Find a user by login")
    public ResponseEntity<UserDto> getByEmail(@PathVariable("email") String email) {
        User user = service.getByEmail(email);
        return new ResponseEntity<>(convertToDto(user), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Register new user")
    public ResponseEntity<UserDto> registerNewUser(@Valid @RequestBody RegisterUserDto user) {
        User registeredUser = service.registerUser(convertFromDto(user));
        return new ResponseEntity<>(convertToDto(registeredUser), HttpStatus.CREATED);
    }

    @PatchMapping("/{login}")
    @ApiOperation("Update user")
    public ResponseEntity<UserDto> updateUser(@PathVariable("login") String login, @Valid @RequestBody EditUserDto userDto) {
        User userToUpdate = service.getByLogin(login);
        mapper.map(userDto, userToUpdate);
        User updatedUser = service.updateUser(userToUpdate);
        return new ResponseEntity<>(convertToDto(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping("/{login}")
    @ApiOperation("Deregister and delete user")
    public ResponseEntity<UserDto> deregisterUser(@PathVariable("login") String login) {
        User user = service.deleteUser(login);
        return new ResponseEntity<>(convertToDto(user), HttpStatus.OK);
    }

    private UserDto convertToDto(User user) {
        return mapper.map(user, UserDto.class);
    }

    private User convertFromDto(RegisterUserDto dto) {
        return mapper.map(dto, User.class);
    }

}
