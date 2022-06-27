package ar.edu.unq.desaap.grupoj.backenddesappapi.model;

import javax.persistence.*;

@Entity
public class UserCurrency {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "currencyId")
    private Currency currency;

    private float Quantity;

    public UserCurrency() {
    }

    public UserCurrency(User user, Currency currency, float quantity) {
        this.user = user;
        this.currency = currency;
        Quantity = quantity;
    }
}
