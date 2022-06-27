package ar.edu.unq.desaap.grupoj.backenddesappapi.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Transaction {
    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column
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

    public boolean shouldBeCancelled(){
        return sameUser() ||
                this.type == TransactionType.Buy && priceIncreased() ||
                this.type == TransactionType.Sale && priceDecreased();
    }

    public boolean sameUser() {
        return this.getBuyer().getId().equals(this.getSeller().getId());
    }

    public boolean priceIncreased(){
        int result = Float.compare(this.price,this.saleIntention.getPrice());
        return result > 0;
    }

    public boolean priceDecreased(){
        int result = Float.compare(this.price,this.buyIntention.getPrice());
        return result < 0;
    }
}