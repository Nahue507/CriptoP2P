package ar.edu.unq.desaap.grupoj.backenddesappapi.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Intention {
    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "issuerId")
    private User issuer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "currencyId")
    private Currency currency;

    @Column
    private float price;

    @Column
    private float quantity;

    @Column
    private Date date;

    @Column
    @Enumerated(EnumType.STRING)
    private IntentionStatus status;

    public Intention() {
    }

    public Intention(TransactionType type, User issuer, Currency currency, float price, float quantity, Date date, IntentionStatus status) {
        this.type = type;
        this.issuer = issuer;
        this.currency = currency;
        this.price = price;
        this.quantity = quantity;
        this.date = date;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public User getIssuer() {
        return issuer;
    }

    public void setIssuer(User issuer) {
        this.issuer = issuer;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public IntentionStatus getStatus() {
        return status;
    }

    public void setStatus(IntentionStatus status) {
        this.status = status;
    }
}