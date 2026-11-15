package cl.duoc.tienda.model;

public class Congelado extends Producto{
    private double temperatura;

    public Congelado() {
    }

    public Congelado(String codigo, double precio, int stock, double temperatura) {
        super(codigo, precio, stock);
        setTemperatura(temperatura);
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        if(temperatura>5){
            throw new IllegalArgumentException("La temperatura debe ser inferior a 5.");
        }
        this.temperatura = temperatura;
    }

    @Override
    public String toString() {
        return super.toString() + "Congelado{" +
                "temperatura=" + temperatura +
                '}';
    }

    @Override
    public double calcularDescuento() {
        return super.precio*0.1;
    }
}
