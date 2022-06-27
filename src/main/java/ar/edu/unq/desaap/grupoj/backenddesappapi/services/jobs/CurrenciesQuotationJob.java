package ar.edu.unq.desaap.grupoj.backenddesappapi.services.jobs;

import ar.edu.unq.desaap.grupoj.backenddesappapi.model.Currency;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.CacheService;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.CurrencyService;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrenciesQuotationJob {
    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private QuotationService quotationService;

    @Autowired
    private CacheService cacheService;

    @Scheduled(fixedDelay = 600000)
    protected void updatePrices(){
        //Get prices
        List<Currency> currencyList = currencyService.getAllWithPrices();

        //Store prices history
        currencyList.forEach(currency -> quotationService.saveQuotation(currency));

        //Store price in cache
        cacheService.storeCurrencyPrices(currencyList);
    }
}