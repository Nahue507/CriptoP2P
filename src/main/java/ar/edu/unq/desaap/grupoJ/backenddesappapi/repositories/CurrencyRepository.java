package ar.edu.unq.desaap.grupoJ.backenddesappapi.repositories;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.Currency;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Configuration
@Repository
public interface CurrencyRepository extends CrudRepository<Currency, String> {
}
