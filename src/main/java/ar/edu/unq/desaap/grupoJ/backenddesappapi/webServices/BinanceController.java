package ar.edu.unq.desaap.grupoJ.backenddesappapi.webServices;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.Currency;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
public class BinanceController {



    private Currency getCurrencyPrice(String symbol){
        String uri = "https://api1.binance.com/api/v3/ticker/price?symbol=";
        uri = uri.concat(symbol);
        RestTemplate restTemplate = new RestTemplate();
        Currency crypto = restTemplate.getForObject(uri,Currency.class);
        return crypto;
    }

    @GetMapping("/allPrices")
    public List<Currency> getAllPrices(){
        List<Currency> result = new ArrayList<Currency>();
        List<String> symbols = Arrays.asList("ALICEUSDT","MATICUSDT","AXSUSDT","AAVEUSDT","ATOMUSDT","NEOUSDT",
                "DOTUSDT","ETHUSDT","CAKEUSDT","BTCUSDT","BNBUSDT","ADAUSDT","TRXUSDT",
                "AUDIOUSDT");

        for(String crypto : symbols){
            result.add(this.getCurrencyPrice(crypto));
        }

        return result;

    }

}
