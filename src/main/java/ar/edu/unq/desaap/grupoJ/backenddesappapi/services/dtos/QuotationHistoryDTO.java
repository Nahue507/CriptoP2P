package ar.edu.unq.desaap.grupoJ.backenddesappapi.services.dtos;

import java.util.Date;

public class QuotationHistoryDTO {
    public String currency;
    public float price;
    public Date date;

    public QuotationHistoryDTO(String currency, float price, Date date) {
        this.currency = currency;
        this.price = price;
        this.date = date;
    }
}