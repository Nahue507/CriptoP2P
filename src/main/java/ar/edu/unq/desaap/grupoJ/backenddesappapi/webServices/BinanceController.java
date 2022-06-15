package ar.edu.unq.desaap.grupoJ.backenddesappapi.webServices;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.Currency;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.CacheService;
import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
public class BinanceController {
    @Autowired
    private CacheService cacheService;
    private List<String> symbols = Arrays.asList("ALICEUSDT","MATICUSDT","AXSUSDT","AAVEUSDT","ATOMUSDT","NEOUSDT",
            "DOTUSDT","ETHUSDT","CAKEUSDT","BTCUSDT","BNBUSDT","ADAUSDT","TRXUSDT",
            "AUDIOUSDT");
    private Currency getCurrencyPrice(String symbol){
        String uri = "https://api1.binance.com/api/v3/ticker/price?symbol=";
        uri = uri.concat(symbol);
        RestTemplate restTemplate = new RestTemplate();
        Currency crypto = restTemplate.getForObject(uri,Currency.class);
        return crypto;
    }

    public List<Currency> getAllPrices(){
        List<Currency> result = new ArrayList<Currency>();


        for(String crypto : this.symbols){
            result.add(this.getCurrencyPrice(crypto));
        }

        return result;

    }
    @GetMapping("/allPrices")
    public List<Currency> getAllCurrencies(){
        List<Currency> result = new ArrayList<Currency>();
        for(String crypto : this.symbols){
            result.add(cacheService.getCurrentPriceAsCurrency(crypto));
        }
        return result;
    }

}
