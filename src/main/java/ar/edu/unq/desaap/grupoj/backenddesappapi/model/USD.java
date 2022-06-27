package ar.edu.unq.desaap.grupoj.backenddesappapi.model;

public class USD {
    private String fecha;
    private String compra;
    private String venta;


    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCompra() {
        return compra;
    }

    public void setCompra(String compra) {
        this.compra = compra;
    }

    public void setVenta(String venta) {
        this.venta = venta;
    }

    public String getPrice(){
        return this.venta;
    }
}
