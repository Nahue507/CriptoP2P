package ar.edu.unq.desaap.grupoJ.backenddesappapi.repositories;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.Intention;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.IntentionStatus;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.TransactionType;
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