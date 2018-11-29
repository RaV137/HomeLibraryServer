package pl.danowski.rafal.homelibraryserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.danowski.rafal.homelibraryserver.dto.user.UserDto;
import pl.danowski.rafal.homelibraryserver.service.interfaces.IUserService;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService service;

    @GetMapping("/login")
    public Boolean attemptLogin(@RequestBody UserDto userDto) {
        return false;
    }

    // TODO: zarejestruj, zmień hasło, przypomnij hasło, zmień email, usuń konto


}
