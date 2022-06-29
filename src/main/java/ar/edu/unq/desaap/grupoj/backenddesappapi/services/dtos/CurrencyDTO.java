package ar.edu.unq.desaap.grupoj.backenddesappapi.services.dtos;

import ar.edu.unq.desaap.grupoj.backenddesappapi.model.Currency;

import java.util.Date;

public class CurrencyDTO {
    public String symbol;
    public String priceUSD;
    public Date lastUpdate;

    public CurrencyDTO() {
    }

    public CurrencyDTO(Currency currency, Date lastUpdate) {
        this.priceUSD = currency.getPrice();
        this.symbol = currency.getSymbol();
        this.lastUpdate = lastUpdate;
    }
}