package ar.edu.unq.desaap.grupoJ.backenddesappapi.services;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.Currency;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.repositories.CurrencyRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.Exceptions.CurrencyNotFoundException;

import java.util.Arrays;
import java.util.List;

@Service
public class CurrencyService {
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private CurrencyRepository currencyRepository;

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
        return this.currencyRepository.findById(currencyName).orElseThrow(() -> new CurrencyNotFoundException(currencyName));
    }

    public List<String> getAllCurrencySymbols(){
        List<String> symbols = Arrays.asList("ALICEUSDT","MATICUSDT","AXSUSDT","AAVEUSDT","ATOMUSDT","NEOUSDT",
                "DOTUSDT","ETHUSDT","CAKEUSDT","BTCUSDT","BNBUSDT","ADAUSDT","TRXUSDT",
                "AUDIOUSDT");
        return symbols;
    }
}