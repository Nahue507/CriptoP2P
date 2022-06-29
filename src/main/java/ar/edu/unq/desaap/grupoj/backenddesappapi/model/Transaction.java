package ar.edu.unq.desaap.grupoj.backenddesappapi.model;

import ar.edu.unq.desaap.grupoj.backenddesappapi.exceptions.TransactionProcessException;

import javax.persistence.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Entity
public class Transaction {
    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @ManyToOne
    @JoinColumn(name = "buyerId")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "sellerId")
    private User seller;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "buyIntentionId")
    private Intention buyIntention;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "saleIntentionId")
    private Intention saleIntention;

    @ManyToOne
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
    private TransactionStatus status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Intention getBuyIntention() {
        return buyIntention;
    }

    public void setBuyIntention(Intention buyIntention) {
        this.buyIntention = buyIntention;
    }

    public Intention getSaleIntention() {
        return saleIntention;
    }

    public void setSaleIntention(Intention saleIntention) {
        this.saleIntention = saleIntention;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public boolean shouldBeCancelled() {
        return sameUser() ||
                this.type == TransactionType.BUY && priceIncreased() ||
                this.type == TransactionType.SALE && priceDecreased();
    }

    public boolean sameUser() {
        return this.getBuyer().getId().equals(this.getSeller().getId());
    }

    public boolean priceIncreased() {
        float percentage = (this.price - this.saleIntention.getPrice()) / 100;
        return percentage > 0.05;
    }

    public boolean priceDecreased() {
        float percentage = (this.price - this.saleIntention.getPrice()) / 100;
        return percentage < -0.05;
    }

    public Integer calculatePoints() {
        long duration = new Date().getTime() - this.getDate().getTime();
        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);

        return diffInMinutes < 30 ? 10 : 5;
    }

    public void checkUserCanProcess(Integer userId) throws TransactionProcessException {
        if (saleAndUserNotBuyer(userId) || buyAndUserNotSeller(userId)) {
            throw new TransactionProcessException("This user cannot accept the transaction");
        }
    }

    private boolean saleAndUserNotBuyer(Integer userId) {
        return type == TransactionType.SALE && !buyer.getId().equals(userId);
    }

    private boolean buyAndUserNotSeller(Integer userId) {
        return type == TransactionType.BUY && !seller.getId().equals(userId);
    }

    public void checkCanBeProcessed() throws TransactionProcessException {
        if (this.status != TransactionStatus.TO_BE_CONFIRMED) {
            throw new TransactionProcessException("Transaction already processed");
        }
    }
}