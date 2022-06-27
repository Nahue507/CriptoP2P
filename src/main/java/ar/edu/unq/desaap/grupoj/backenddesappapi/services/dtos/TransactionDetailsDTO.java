package ar.edu.unq.desaap.grupoj.backenddesappapi.services.dtos;

import ar.edu.unq.desaap.grupoj.backenddesappapi.model.*;

import java.util.Date;

public class TransactionDetailsDTO {
    public Integer id;
    public TransactionType type;
    public String buyer;
    public String seller;
    public IntentionDetailsDTO buyIntention;
    public IntentionDetailsDTO saleIntention;
    public String currency;
    public float price;
    public float quantity;
    public Date date;
    public TransactionStatus status;

    public TransactionDetailsDTO(Transaction transactionCreated) {
        this.id = transactionCreated.getId();
        this.type = transactionCreated.getType();
        this.buyer = transactionCreated.getBuyer().getEmail();
        this.seller = transactionCreated.getSeller().getEmail();
        this.buyIntention = new IntentionDetailsDTO(transactionCreated.getBuyIntention());
        this.saleIntention = new IntentionDetailsDTO(transactionCreated.getSaleIntention());
        this.currency = transactionCreated.getCurrency().getSymbol();
        this.price = transactionCreated.getPrice();
        this.quantity = transactionCreated.getQuantity();
        this.date = transactionCreated.getDate();
        this.status = transactionCreated.getStatus();
    }
}