package ar.edu.unq.desaap.grupoJ.backenddesappapi.services;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.Currency;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.repositories.CurrencyRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.Exceptions.CurrencyNotFoundException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CurrencyService {
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CacheService cacheService;

    private Currency getCurrencyPrice(String symbol){
        String uri = "https://api1.binance.com/api/v3/ticker/price?symbol=";
        uri = uri.concat(symbol);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri,Currency.class);
    }

    public List<Currency> getAllWithPrices(){
        List<Currency> result = new ArrayList<>();

        for(String crypto : this.getAllCurrencySymbols()){
            result.add(this.getCurrencyPrice(crypto));
        }

        return result;
    }

    @Transactional
    public Currency save(Currency newCurrency) {
            Currency currencyCreated = currencyRepository.save(newCurrency);
            logger.info("A new currency was created or was updated");
            return currencyCreated;
    }

    @Transactional
    public List<Currency> findAll() {
        return (List<Currency>) this.currencyRepository.findAll();
    }

    @Transactional
    public Currency find(String currencyName) throws CurrencyNotFoundException {
        Currency currency =this.currencyRepository.findById(currencyName).orElseThrow(() -> new CurrencyNotFoundException(currencyName));
        currency.setPrice(cacheService.getCurrentPrice(currencyName));
        return currency;
    }

    @Scheduled(fixedDelay = 600000)
    protected void updatePrices(){
        cacheService.storeCurrencyPrices(this.getAllWithPrices());
    }

    public List<String> getAllCurrencySymbols(){
        return Arrays.asList(
                "ALICEUSDT",
                "MATICUSDT",
                "AXSUSDT",
                "AAVEUSDT",
                "ATOMUSDT",
                "NEOUSDT",
                "DOTUSDT",
                "ETHUSDT",
                "CAKEUSDT",
                "BTCUSDT",
                "BNBUSDT",
                "ADAUSDT",
                "TRXUSDT",
                "AUDIOUSDT");
    }
}