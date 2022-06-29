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
    public Date dateCreated;
    public Date dateProcessed;
    public Float totalUSD;
    public Float totalARS;
    public TransactionStatus status;

    public TransactionDetailsDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.buyer = transaction.getBuyer().getEmail();
        this.seller = transaction.getSeller().getEmail();
        this.buyIntention = new IntentionDetailsDTO(transaction.getBuyIntention());
        this.saleIntention = new IntentionDetailsDTO(transaction.getSaleIntention());
        this.currency = transaction.getCurrency().getSymbol();
        this.price = transaction.getPriceUSD();
        this.quantity = transaction.getQuantity();
        this.dateCreated = transaction.getDateCreated();
        this.dateProcessed = transaction.getDateProcessed();
        this.totalUSD = transaction.getTotalUSD();
        this.totalARS = transaction.getTotalARS();
        this.status = transaction.getStatus();
    }
}