package ar.edu.unq.desaap.grupoJ.backenddesappapi.webServices;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.Intention;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.Exceptions.IntentionException;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.IntentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@EnableAutoConfiguration
public class IntentionsController {

    @Autowired
    private IntentionService intentionService;

    @PostMapping(path = "intentions",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Intention> Create(@RequestBody Intention intention) {
        try {
            Intention newIntention = intentionService.save(intention);
            return new ResponseEntity<>(newIntention, HttpStatus.CREATED);
        }
        catch (IntentionException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Intention could not be created");
        }
    }
}