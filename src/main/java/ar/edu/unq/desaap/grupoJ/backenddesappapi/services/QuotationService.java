package ar.edu.unq.desaap.grupoJ.backenddesappapi.services;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.USD;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuotationService {

    private CurrencyConverter converter = new CurrencyConverter();

    private String getDollarQuotation(){
        String uri = "https://api-dolar-argentina.herokuapp.com/api/dolaroficial";
        RestTemplate restTemplate = new RestTemplate();
        USD usd = restTemplate.getForObject(uri, USD.class);
        return usd != null ? usd.getPrice() : null;
    }

    public String getPriceArs(String dollars){
        this.converter.setValue(Double.parseDouble(dollars));
        this.converter.setFactor(Double.parseDouble(this.getDollarQuotation()));
        return String.valueOf(this.converter.Convert());
    }
}
