package ar.edu.unq.desaap.grupoJ.backenddesappapi.webServices;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.Currency;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.CacheService;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    public List<Currency> getAllCurrencies(){
        List<Currency> result = new ArrayList<>();
        for(String crypto : currencyService.getAllCurrencySymbols()){
            result.add(cacheService.getCurrency(crypto));
        }
        return result;
    }
}