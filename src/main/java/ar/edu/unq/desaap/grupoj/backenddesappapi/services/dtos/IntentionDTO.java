package ar.edu.unq.desaap.grupoj.backenddesappapi.services.dtos;

import ar.edu.unq.desaap.grupoj.backenddesappapi.model.TransactionType;

public class IntentionDTO {
    public TransactionType type;
    public Integer issuerId;
    public String currencySymbol;
    public float quantity;
}