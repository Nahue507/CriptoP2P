package ar.edu.unq.desaap.grupoJ.backenddesappapi.webServices;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.Transaction;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.dtos.TransactionAcceptanceDTO;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.exceptions.PriceIncreasedException;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.exceptions.SameUserException;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.exceptions.TransactionException;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.TransactionService;
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
public class TransactionsController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(path = "transactions/buy",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> Buy(@RequestBody Transaction transaction) {
        try {
            Transaction newTransaction = transactionService.saveBuyTransaction(transaction);
            return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
        }
        catch (TransactionException | SameUserException | PriceIncreasedException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Transaction could not be created");
        }
    }

    @PostMapping(path = "transactions/accept",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> accept(@RequestBody TransactionAcceptanceDTO dto) {
        try {
            transactionService.acceptTransaction(dto.getUserId(), dto.getTransactionId());
            return ResponseEntity.ok().body("Transaction Accepted");
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Transaction could not be accepted");
        }
    }
}