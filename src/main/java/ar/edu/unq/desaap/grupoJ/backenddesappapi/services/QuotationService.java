package ar.edu.unq.desaap.grupoJ.backenddesappapi.services;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.USD;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class QuotationService {

    public String getQuotationUSD(){
        String uri = "https://api-dolar-argentina.herokuapp.com/api/dolaroficial";
        RestTemplate restTemplate = new RestTemplate();
        USD usd = restTemplate.getForObject(uri, USD.class);
        return usd.getPrice();
    }
}
