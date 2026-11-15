package cl.duoc.tienda.model;

public abstract class Producto {
    protected String codigo;
    protected double precio;
    protected int stock;

    public Producto() {
    }

    public Producto(String codigo, double precio, int stock) {
        setCodigo(codigo);
        setPrecio(precio);
        setStock(stock);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        if(codigo.trim().length()!=8){
            throw new IllegalArgumentException("El código debe tener 8 caracteres.");
        }
        this.codigo = codigo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if(precio<=0){
            throw new IllegalArgumentException("El precio debe ser positivo.");
        }
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if(stock<0){
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "codigo='" + codigo + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                '}';
    }

    public abstract double calcularDescuento();
}
