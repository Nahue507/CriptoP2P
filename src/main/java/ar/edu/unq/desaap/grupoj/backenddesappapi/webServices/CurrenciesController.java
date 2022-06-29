package ar.edu.unq.desaap.grupoj.backenddesappapi.webServices;

import ar.edu.unq.desaap.grupoj.backenddesappapi.aspect.LogExecutionTime;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.CacheService;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.CurrencyService;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.dtos.CurrencyDTO;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.dtos.QuotationHistoryDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CurrenciesController {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/allPrices")
    @LogExecutionTime
    public ResponseEntity<List<CurrencyDTO>> getAllCurrencies() throws JsonProcessingException {
        List<CurrencyDTO> result = new ArrayList<>();
        for(String crypto : currencyService.getAllCurrencySymbols()){
            result.add(cacheService.getCurrency(crypto));
        }
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/lastQuotation/{symbol}")
    @LogExecutionTime
    public ResponseEntity<List<QuotationHistoryDTO>> getLastQuotations(
            @PathVariable("symbol") String symbol){
        List<QuotationHistoryDTO> list = currencyService.getLastQuotations(symbol);
        return ResponseEntity.ok().body(list);
    }
}