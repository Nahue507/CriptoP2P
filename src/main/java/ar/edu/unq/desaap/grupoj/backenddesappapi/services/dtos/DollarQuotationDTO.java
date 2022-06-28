package ar.edu.unq.desaap.grupoj.backenddesappapi.services.dtos;

public class DollarQuotationDTO {
    public String fecha;
    public String compra;
    public String venta;

    public String getPrice(){
        return this.venta;
    }
}
