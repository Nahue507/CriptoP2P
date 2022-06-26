package ar.edu.unq.desaap.grupoJ.backenddesappapi.webServices;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.IntentionStatus;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.TransactionType;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.dtos.IntentionDTO;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.dtos.IntentionDetailsDTO;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.exceptions.IntentionException;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.IntentionService;
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
public class IntentionsController {

    @Autowired
    private IntentionService intentionService;

    @PostMapping(path = "intentions",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IntentionDetailsDTO> Create(@RequestBody IntentionDTO intention) {
        try {
            IntentionDetailsDTO newIntention = intentionService.save(intention);
            return new ResponseEntity<>(newIntention, HttpStatus.CREATED);
        }
        catch (IntentionException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Intention could not be created");
        }
    }

    @GetMapping("/intentions")
    public ResponseEntity<List<IntentionDetailsDTO>> getAllWithTypeAndStatus(
            @RequestParam("type") TransactionType type,
            @RequestParam("status") IntentionStatus status) {
        List<IntentionDetailsDTO> list = intentionService.findAllWithStatus(type, status);
        return ResponseEntity.ok().body(list);
    }
}