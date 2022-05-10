package ar.edu.unq.desaap.grupoJ.backenddesappapi.webServices;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.User;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.Exceptions.UsersException;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.UsersService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping(path = "users",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> Create(@RequestBody User newUser) throws Exception {

        try {
            User user = usersService.save(newUser);
            return new ResponseEntity<>(user, HttpStatus.CREATED);

        }
        catch (UsersException e){
            throw new Exception(e.getMessage());
        }
        catch (Exception e) {
            throw new Exception("User could not be created");
        }
    }
    @GetMapping( path = "test",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> testCall(){
        User user = new User("Martin",
                            "Serna",
                "martinserna@hotmail.com", "calle 1" , "Martin123#","1234567890123456789012","12345678" );
        return new ResponseEntity<User>(user,HttpStatus.FOUND);
    }
}