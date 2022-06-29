package ar.edu.unq.desaap.grupoj.backenddesappapi.services;

public class CurrencyConverter {
    private float value;
    private float factor;

    public void setValue(float value) {
        this.value = value;
    }

    public void setFactor(float factor) {
        this.factor = factor;
    }

    public float Convert(){
        return value * factor;
    }

    public float Reverse(){
        return value / factor;
    }
}
