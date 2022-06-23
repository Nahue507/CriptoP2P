package ar.edu.unq.desaap.grupoJ.backenddesappapi.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class QuotationHistory {

    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column
    private Date date;

    @ManyToOne
    @JoinColumn(name = "currencyId")
    private Currency currency;

    @Column
    private Float quotation;

    public Date getDate() {
        return date;
    }

    public QuotationHistory(){}

    public QuotationHistory(Date date, Currency currency, Float quotation) {
        this.date = date;
        this.currency = currency;
        this.quotation = quotation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Float getQuotation() {
        return quotation;
    }

    public void setQuotation(Float quotation) {
        this.quotation = quotation;
    }
}