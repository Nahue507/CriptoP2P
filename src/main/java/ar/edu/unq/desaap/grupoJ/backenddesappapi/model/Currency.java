package ar.edu.unq.desaap.grupoJ.backenddesappapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;


@Entity
public class Currency {
    @Id
    @Column(unique = true)
    private String symbol;
    @Column
    private long price;

    public Currency(String symbol , long price){
        this.price=price;
        this.symbol=symbol;
    }
    public Currency(){}

    public String getSymbol() {
        return symbol;
    }

    public long getPrice() {
        return price;
    }

}
