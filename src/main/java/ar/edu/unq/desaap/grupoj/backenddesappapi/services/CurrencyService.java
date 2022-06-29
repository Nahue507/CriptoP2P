package ar.edu.unq.desaap.grupoj.backenddesappapi.services;

import ar.edu.unq.desaap.grupoj.backenddesappapi.model.Currency;
import ar.edu.unq.desaap.grupoj.backenddesappapi.model.QuotationHistory;
import ar.edu.unq.desaap.grupoj.backenddesappapi.repositories.CurrencyRepository;
import ar.edu.unq.desaap.grupoj.backenddesappapi.repositories.QuotationRepository;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.dtos.QuotationHistoryDTO;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.exceptions.CurrencyNotFoundException;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.quotations.CryptosApiProperties;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyService {
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private CryptosApiProperties cryptosApiProperties;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private QuotationRepository quotationRepository;

    /**
     * Returns a currency with its price from Crypto API
     *
     * @param symbol currency symbol
     */
    private Currency getCurrencyPriceFromAPI(String symbol) {
        String uri = cryptosApiProperties.getUri().concat(symbol);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, Currency.class);
    }

    /**
     * @return List of all System Crypto Currencies with their current price from Crypto API
     */
    public List<Currency> getAllWithPrices() {
        List<Currency> result = new ArrayList<>();

        for (String crypto : this.getAllCurrencySymbols()) {
            result.add(this.getCurrencyPriceFromAPI(crypto));
        }

        return result;
    }

    @Transactional
    public void save(Currency newCurrency) {
        Currency currencyCreated = currencyRepository.save(newCurrency);
        logger.info(MessageFormat.format("Currency with symbol: {0} was created or was updated", currencyCreated.getSymbol()));
    }

    public Currency find(String currencyName) throws CurrencyNotFoundException {
        Currency currency = this.currencyRepository.findById(currencyName).orElseThrow(() -> new CurrencyNotFoundException(currencyName));
        Currency currencyAPI = this.getCurrencyPriceFromAPI(currencyName);
        currency.setPrice(currencyAPI.getPrice());
        return currency;
    }

    public List<QuotationHistoryDTO> getLastQuotations(String symbol) {
        Date date = DateUtils.addHours(new Date(), -24);

        List<QuotationHistory> list = quotationRepository.getLastQuotations(symbol, date);

        return list.stream().map(x -> new QuotationHistoryDTO(
                        x.getCurrency().getSymbol(),
                        x.getQuotation(),
                        x.getDate()))
                .collect(Collectors.toList());
    }

    public List<String> getAllCurrencySymbols() {
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