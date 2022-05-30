package ar.edu.unq.desaap.grupoJ.backenddesappapi.webServices;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.model.Currency;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BinanceController {

    @GetMapping("/prices")
    private long getCurrencyPrice(HttpServletRequest request){
        String currency = request.getParameter("Symbol");
        String uri = "https://api1.binance.com/api/v3/ticker/price?symbol=";
        uri = uri.concat(currency);
        RestTemplate restTemplate = new RestTemplate();
        Currency crypto = restTemplate.getForObject(uri,Currency.class);
        return crypto.getPrice();
    }
}
