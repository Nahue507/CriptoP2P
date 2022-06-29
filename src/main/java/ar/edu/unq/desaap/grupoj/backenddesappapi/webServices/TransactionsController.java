package ar.edu.unq.desaap.grupoj.backenddesappapi.webServices;

import ar.edu.unq.desaap.grupoj.backenddesappapi.services.TransactionService;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.dtos.TransactionBuyDTO;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.dtos.TransactionDetailsDTO;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.dtos.TransactionProcessDTO;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.exceptions.*;
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
public class TransactionsController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(path = "transactions/buy",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionDetailsDTO> Buy(@RequestBody TransactionBuyDTO transactionDTO) {
        try {
            TransactionDetailsDTO transaction = transactionService.saveBuyTransaction(transactionDTO);
            return new ResponseEntity<>(transaction, HttpStatus.CREATED);
        } catch (TransactionException | SameUserException | PriceIncreasedException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Transaction could not be created");
        }
    }

    @PostMapping(path = "transactions/accept",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> accept(@RequestBody TransactionProcessDTO dto) {
        try {
            transactionService.acceptTransaction(dto.userId, dto.transactionId);
            return ResponseEntity.ok().body("Transaction Accepted");
        } catch (TransactionNotFoundException | UserNotFoundException | TransactionProcessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Transaction could not be accepted");
        }
    }

    @PostMapping(path = "transactions/cancel",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> cancel(@RequestBody TransactionProcessDTO dto) {
        try {
            transactionService.cancelTransaction(dto.userId, dto.transactionId);
            return ResponseEntity.ok().body("Transaction Cancelled");
        } catch (TransactionNotFoundException | UserNotFoundException | TransactionProcessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Transaction could not be cancelled");
        }
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionDetailsDTO>> findAll() {
        List<TransactionDetailsDTO> list = transactionService.findAll();
        return ResponseEntity.ok().body(list);
    }
}