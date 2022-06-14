package ar.edu.unq.desaap.grupoJ.backenddesappapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Currency {

    @Id
    @Column(unique = true)
    private String symbol;

    @Column
    private String price;

    @OneToMany(mappedBy = "currency")
    private Set<UserCurrency> users;

    public Currency(){}

    public Currency(String symbol, String price){
        this.price=price;
        this.symbol=symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public long getPrice() {
        return Long.getLong(price);
    }

    public String getPriceAsString(){
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Set<UserCurrency> getUsers() {
        return users;
    }

    public void setUsers(Set<UserCurrency> users) {
        this.users = users;
    }
}
