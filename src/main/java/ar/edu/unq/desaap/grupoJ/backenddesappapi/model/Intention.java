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


    @ManyToOne
    @JoinColumn(name = "issuerId")
    private User issuer;

    @ManyToOne
    @JoinColumn(name = "currencyId")
    private Currency currency;

    @Column
    private float price;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}
