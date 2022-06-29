package ar.edu.unq.desaap.grupoj.backenddesappapi.webServices;

import ar.edu.unq.desaap.grupoj.backenddesappapi.aspect.LogExecutionTime;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.UsersService;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.dtos.UserDTO;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.dtos.UserDetailsDTO;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.exceptions.UsersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
    @LogExecutionTime
    public ResponseEntity<UserDetailsDTO> Create(@RequestBody UserDTO userDTO) {

        try {
            UserDetailsDTO user = usersService.save(userDTO);
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
    @LogExecutionTime
    public ResponseEntity<List<UserDetailsDTO>> allUsers() {
        List<UserDetailsDTO> list = usersService.findAll();
        return ResponseEntity.ok().body(list);
    }
}