public class Producto {
    private TipoProducto tipo;
    private float rendimientoPorHectarea;
    private float valorReferenciaPorKg;
    private boolean esPerecedero;

    //Constructor
    public Producto(TipoProducto tipo, float rendimientoPorHectarea, float valorReferenciaPorKg, boolean esPerecedero) {
        this.tipo = tipo;
        this.rendimientoPorHectarea = rendimientoPorHectarea;
        this.valorReferenciaPorKg = valorReferenciaPorKg;
        this.esPerecedero = esPerecedero;
    }

    /***------------------------------------------------------------***/

    //Getters
    public TipoProducto getTipo() {
        return tipo;
    }
    public float getRendimientoPorHectarea() {
        return rendimientoPorHectarea;
    }
    public float getValorReferenciaPorKg() {
        return valorReferenciaPorKg;
    }
    public boolean esPerecedero() {
        return esPerecedero;
    }

    //Setters
    public void setTipo(TipoProducto tipo) {
        this.tipo = tipo;
    }
    public void setRendimientoPorHectarea(float rendimientoPorHectarea) {
        this.rendimientoPorHectarea = rendimientoPorHectarea;
    }
    public void setValorReferenciaPorKg(float valorReferenciaPorKg) {
        this.valorReferenciaPorKg = valorReferenciaPorKg;
    }
    public void setEsPerecedero(boolean esPerecedero) {
        this.esPerecedero = esPerecedero;
    }

    /***------------------------------------------------------------***/





}
