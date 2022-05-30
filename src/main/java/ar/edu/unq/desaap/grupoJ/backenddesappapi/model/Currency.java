package ar.edu.unq.desaap.grupoJ.backenddesappapi.model;

public class Currency {
    private String symbol;
    private long price;

    public Currency(String symbol , long price){
        this.price=price;
        this.symbol=symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public long getPrice() {
        return price;
    }

}
