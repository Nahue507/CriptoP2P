package ar.edu.unq.desaap.grupoJ.backenddesappapi.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Intention {
    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column
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
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public Integer getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
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

    public float getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }
}
