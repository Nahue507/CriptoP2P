package ar.edu.unq.desaap.grupoJ.backenddesappapi.repositories;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.QuotationHistory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Configuration
@Repository
public interface QuotationRepository extends CrudRepository<QuotationHistory, Integer> {

    @Query("SELECT qu FROM QuotationHistory qu WHERE qu.currency.symbol = :symbol AND qu.date >= :date ")
    List<QuotationHistory> getLastQuotations(@Param("symbol") String symbol, @Param("date") Date date);
}