package ar.edu.unq.desaap.grupoJ.backenddesappapi.webServices;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.User;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.exceptions.UsersException;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@EnableAutoConfiguration
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping(path = "users",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> Create(@RequestBody User newUser) {

        try {
            User user = usersService.save(newUser);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        catch (UsersException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User could not be created");
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> allUsers() {
        List<User> list = usersService.findAll();
        return ResponseEntity.ok().body(list);
    }
}