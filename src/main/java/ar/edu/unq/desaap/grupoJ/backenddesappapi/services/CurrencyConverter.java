package ar.edu.unq.desaap.grupoJ.backenddesappapi.services;

public class CurrencyConverter {
    private double value;
    private double factor;

    public void setValue(double value) {
        this.value = value;
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }

    public double Convert(){
        return value * factor;
    }

    public double Reverse(){
        return value / factor;
    }
}
