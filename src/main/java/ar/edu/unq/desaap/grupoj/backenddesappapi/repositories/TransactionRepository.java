package ar.edu.unq.desaap.grupoj.backenddesappapi.repositories;

import ar.edu.unq.desaap.grupoj.backenddesappapi.model.Transaction;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Configuration
@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
}