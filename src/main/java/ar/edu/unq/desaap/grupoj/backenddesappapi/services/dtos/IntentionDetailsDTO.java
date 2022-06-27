package ar.edu.unq.desaap.grupoj.backenddesappapi.services.dtos;

import ar.edu.unq.desaap.grupoj.backenddesappapi.model.IntentionStatus;
import ar.edu.unq.desaap.grupoj.backenddesappapi.model.TransactionType;

import java.util.Date;

public class IntentionDetailsDTO {
    public TransactionType type;
    public String issuer;
    public String currencySymbol;
    public float price;
    public float quantity;
    public Date date;
    public IntentionStatus status;

    public IntentionDetailsDTO(TransactionType type, String issuer, String currencySymbol, float price, float quantity, Date date, IntentionStatus status) {
        this.type = type;
        this.issuer = issuer;
        this.currencySymbol = currencySymbol;
        this.price = price;
        this.quantity = quantity;
        this.date = date;
        this.status = status;
    }
}