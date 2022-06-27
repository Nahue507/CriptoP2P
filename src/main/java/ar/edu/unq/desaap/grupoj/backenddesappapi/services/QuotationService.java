package ar.edu.unq.desaap.grupoj.backenddesappapi.services;

import ar.edu.unq.desaap.grupoj.backenddesappapi.model.Currency;
import ar.edu.unq.desaap.grupoj.backenddesappapi.model.QuotationHistory;
import ar.edu.unq.desaap.grupoj.backenddesappapi.model.USD;
import ar.edu.unq.desaap.grupoj.backenddesappapi.repositories.QuotationRepository;
import ar.edu.unq.desaap.grupoj.backenddesappapi.services.quotations.DollarApiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
public class QuotationService {

    @Autowired
    private DollarApiProperties dollarApiProperties;

    @Autowired
    private QuotationRepository quotationRepository;

    private final CurrencyConverter converter = new CurrencyConverter();

    public String getDollarQuotation(){
        String uri = dollarApiProperties.getUri();
        RestTemplate restTemplate = new RestTemplate();
        USD usd = restTemplate.getForObject(uri, USD.class);
        return usd != null ? usd.getPrice() : null;
    }

    public String convert(String dollars, String quotation){
        this.converter.setValue(Double.parseDouble(dollars));
        this.converter.setFactor(Double.parseDouble(quotation));
        return String.valueOf(this.converter.Convert());
    }

    @Transactional
    public void saveQuotation(Currency currency){
        quotationRepository.save(new QuotationHistory(new Date(), currency, Float.parseFloat(currency.getPrice())));
    }
}