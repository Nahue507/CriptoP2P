package ar.edu.unq.desaap.grupoj.backenddesappapi.repositories;

import ar.edu.unq.desaap.grupoj.backenddesappapi.model.Transaction;
import ar.edu.unq.desaap.grupoj.backenddesappapi.model.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Configuration
@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    @Query("SELECT t FROM Transaction t WHERE " +
            "t.status = 'COMPLETED' AND" +
            "(t.type = 'BUY' AND t.buyer = :user) OR " +
            "(t.type = 'SALE' AND t.seller = :user) AND " +
            "t.dateProcessed >= :dateFrom AND t.dateProcessed <= :dateTo")
    List<Transaction> getCompletedTransactionsByUserBetweenDates(User user, Date dateFrom, Date dateTo);
}