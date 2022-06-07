package ar.edu.unq.desaap.grupoJ.backenddesappapi.services;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.Currency;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.User;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.repositories.ICurrencyRepository;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.Exceptions.UsersException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class CurrencyService {
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private ICurrencyRepository criptoCurrency;

    @Transactional
    public Currency save(Currency newCurrency) throws UsersException {
            Currency currencyCreated = criptoCurrency.save(newCurrency);
            logger.info("A new currency was created or was updated");
            return currencyCreated;
    }

    @Transactional
    public List<Currency> findAll() {
        return (List<Currency>) this.criptoCurrency.findAll();
    }

    public List<String> getAllCurrencySymbols(){
        List<String> symbols = Arrays.asList("ALICEUSDT","MATICUSDT","AXSUSDT","AAVEUSDT","ATOMUSDT","NEOUSDT",
                "DOTUSDT","ETHUSDT","CAKEUSDT","BTCUSDT","BNBUSDT","ADAUSDT","TRXUSDT",
                "AUDIOUSDT");
        return symbols;
    }
}