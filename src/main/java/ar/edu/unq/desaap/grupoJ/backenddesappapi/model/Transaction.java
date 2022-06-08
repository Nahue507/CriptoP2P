package ar.edu.unq.desaap.grupoJ.backenddesappapi.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Transaction {
    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column
    @ManyToOne
    @JoinColumn(name = "buyerId")
    private User buyer;

    @Column
    @ManyToOne
    @JoinColumn(name = "sellerId")
    private User seller;

    @Column
    @ManyToOne
    @JoinColumn(name = "buyIntentionId")
    private Intention buyIntention;

    @Column
    @ManyToOne
    @JoinColumn(name = "saleIntentionId")
    private Intention saleIntention;

    @Column
    @ManyToOne
    @JoinColumn(name = "currencyId")
    private Currency currency;

    @Column
    private float price;

    @Column
    private Date date;

    @Column
    private TransactionStatus status;
}
