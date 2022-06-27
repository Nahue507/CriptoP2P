package ar.edu.unq.desaap.grupoj.backenddesappapi.repositories;

import ar.edu.unq.desaap.grupoj.backenddesappapi.model.Intention;
import ar.edu.unq.desaap.grupoj.backenddesappapi.model.IntentionStatus;
import ar.edu.unq.desaap.grupoj.backenddesappapi.model.TransactionType;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Configuration
@Repository
public interface IntentionRepository extends CrudRepository<Intention, Integer> {

    @Query("SELECT i FROM Intention i WHERE i.type = :type AND i.status = :status")
    List<Intention> findAllWithTypeAndStatus(TransactionType type, IntentionStatus status);
}