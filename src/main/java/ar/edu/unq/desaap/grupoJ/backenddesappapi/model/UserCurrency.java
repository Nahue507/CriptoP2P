package ar.edu.unq.desaap.grupoJ.backenddesappapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserCurrency {
    @Column
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column
    @ManyToOne
    @JoinColumn(name = "currencyId")
    private Currency currency;

    private float Quantity;
}
