package ar.edu.unq.desaap.grupoJ.backenddesappapi.webServices;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.Currency;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.CacheService;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.CurrencyService;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.dtos.QuotationHistoryDTO;
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
    public ResponseEntity<List<Currency>> getAllCurrencies(){
        List<Currency> result = new ArrayList<>();
        for(String crypto : currencyService.getAllCurrencySymbols()){
            result.add(cacheService.getCurrency(crypto));
        }
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/lastQuotation/{symbol}")
    public ResponseEntity<List<QuotationHistoryDTO>> getLastQuotations(
            @PathVariable("symbol") String symbol){
        List<QuotationHistoryDTO> list = currencyService.getLastQuotations(symbol);
        return ResponseEntity.ok().body(list);
    }
}