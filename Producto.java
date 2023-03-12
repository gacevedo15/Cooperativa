public class Producto {
    private TipoProducto tipo;
    private float rendimientoPorHectarea;
    private float precio;
    private boolean esPerecedero;

    public Producto(TipoProducto tipo,float rendimientoPorHectarea,float precio,boolean esPerecedero) {
        this.tipo=tipo;
        this.rendimientoPorHectarea=rendimientoPorHectarea;
        this.precio=precio;
        this.esPerecedero=esPerecedero;
    }

    //Getters
    public TipoProducto getTipo() {
        return tipo;
    }
    public float getRendimientoPorHectarea() {
        return rendimientoPorHectarea;
    }
    public float getPrecio() {
        return precio;
    }
    public boolean isEsPerecedero() {
        return esPerecedero;
    }

    //Setters
    public void setTipo(TipoProducto tipo) {
        this.tipo = tipo;
    }
    public void setRendimientoPorHectarea(float rendimientoPorHectarea) {
        this.rendimientoPorHectarea = rendimientoPorHectarea;
    }
    public void setPrecio(float precio) {
        this.precio = precio;
    }
    public void setEsPerecedero(boolean esPerecedero) {
        this.esPerecedero = esPerecedero;
    }

    //ToString
    public String toString() {
        if (esPerecedero)
            return "Producto{" + "tipo=" + tipo + ", rendimientoPorHectarea=" + rendimientoPorHectarea + ", precio=" + precio + ", Perecedero";
        else
            return "Producto{" + "tipo=" + tipo + ", rendimientoPorHectarea=" + rendimientoPorHectarea + ", precio=" + precio + ", No Perecedero";
    }

}
