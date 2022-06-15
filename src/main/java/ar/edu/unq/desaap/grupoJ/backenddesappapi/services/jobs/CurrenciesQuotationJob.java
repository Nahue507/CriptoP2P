package ar.edu.unq.desaap.grupoJ.backenddesappapi.services.jobs;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.CacheService;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CurrenciesQuotationJob {
    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CacheService cacheService;


    @Scheduled(fixedDelay = 600000)
    protected void updatePrices(){
        cacheService.storeCurrencyPrices(currencyService.getAllWithPrices());
    }
}