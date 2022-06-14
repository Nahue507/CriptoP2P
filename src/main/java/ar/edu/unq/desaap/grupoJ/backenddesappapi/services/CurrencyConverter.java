package ar.edu.unq.desaap.grupoJ.backenddesappapi.services;

import ar.edu.unq.desaap.grupoJ.backenddesappapi.services.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyConverter {
    @Autowired

    private QuotationService quotationService;

    public String getPriceArs(String usd){
        double usdPrice = Double.valueOf(quotationService.getQuotationUSD());
        double price = Double.valueOf(usd);
        double res = price * usdPrice;
        return String.valueOf(res);
    }

}
