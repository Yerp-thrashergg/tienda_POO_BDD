package cl.duoc.tienda.model;

public class Bebestible extends Producto implements Calculable{
    private boolean tieneGas;

    public Bebestible() {
    }

    public Bebestible(String codigo, double precio, int stock, boolean tieneGas) {
        super(codigo, precio, stock);
        this.tieneGas = tieneGas;
    }

    public boolean isTieneGas() {
        return tieneGas;
    }

    public void setTieneGas(boolean tieneGas) {
        this.tieneGas = tieneGas;
    }

    @Override
    public String toString() {
        return super.toString() + "Bebestible{" +
                "tieneGas=" + (tieneGas?"si":"no") +
                '}';
    }

    @Override
    public double calcularDescuento() {
        return super.precio * 0.15;
    }

    @Override
    public boolean consultarGas() {
        return this.tieneGas;
    }

    @Override
    public void asignarGas() {
        this.tieneGas = true;
    }

    @Override
    public String getTipo() {
        return "Bebestible";
    }

    @Override
    public String getCaracteristica() {
        return tieneGas ? "Con gas" : "Sin gas";
    }

}
