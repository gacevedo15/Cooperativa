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
    
    public float getPrecio(){
        return precio;
    }

}
