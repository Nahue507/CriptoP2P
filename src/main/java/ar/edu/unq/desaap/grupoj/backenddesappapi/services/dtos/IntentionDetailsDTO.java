package ar.edu.unq.desaap.grupoj.backenddesappapi.services.dtos;

import ar.edu.unq.desaap.grupoj.backenddesappapi.model.Intention;
import ar.edu.unq.desaap.grupoj.backenddesappapi.model.IntentionStatus;
import ar.edu.unq.desaap.grupoj.backenddesappapi.model.TransactionType;

import java.util.Date;

public class IntentionDetailsDTO {
    public Integer id;
    public TransactionType type;
    public String issuer;
    public String issuerReputation;
    public String currencySymbol;
    public Float priceUSD;
    public Float quantity;
    public Float totalUSD;
    public Date date;
    public IntentionStatus status;

    public IntentionDetailsDTO(Intention intention) {
        this.id = intention.getId();
        this.type = intention.getType();
        this.issuer = intention.getIssuer().getEmail();
        this.issuerReputation = intention.getIssuer().getReputation();
        this.currencySymbol = intention.getCurrency().getSymbol();
        this.priceUSD = intention.getPrice();
        this.quantity = intention.getQuantity();
        this.totalUSD = priceUSD * quantity;
        this.date = intention.getDate();
        this.status = intention.getStatus();
    }
}