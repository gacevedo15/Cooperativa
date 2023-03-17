import java.time.LocalDate;

public class Producto {
    private TipoProducto tipo;
    private float rendimientoPorHectarea;
    private float valorReferenciaPorKg;
    private float valorReferenciaPorKgAnterior;
    private LocalDate fechaUltimaActualizacion;
    private boolean esPerecedero;

    //Constructor
    public Producto(TipoProducto tipo, float rendimientoPorHectarea, float valorReferenciaPorKg, boolean esPerecedero) {
        this.tipo = tipo;
        this.rendimientoPorHectarea = rendimientoPorHectarea;
        this.valorReferenciaPorKg = valorReferenciaPorKg;
        this.fechaUltimaActualizacion = LocalDate.now();
        this.esPerecedero = esPerecedero;
    }

    /***------------------------------------------------------------***/

    //Getters
    public TipoProducto getTipo() {
        return this.tipo;
    }
    public float getRendimientoPorHectarea() {
        return this.rendimientoPorHectarea;
    }
    public float getValorReferenciaPorKg() {
        return this.valorReferenciaPorKg;
    }
    public float getValorReferenciaPorKgAnterior() {
        return this.valorReferenciaPorKgAnterior;
    }
    public LocalDate getFechaUltimaActualizacion() {
        return this.fechaUltimaActualizacion;
    }
    public boolean esPerecedero() {
        return this.esPerecedero;
    }

    //Setters
    public void setTipo(TipoProducto tipo) {
        this.tipo = tipo;
    }
    public void setRendimientoPorHectarea(float rendimientoPorHectarea) {
        this.rendimientoPorHectarea = rendimientoPorHectarea;
    }
    public void setValorReferenciaPorKg(float valorReferenciaPorKg) {
        actualizarPrecio(valorReferenciaPorKg);
    }
    public void setEsPerecedero(boolean esPerecedero) {
        this.esPerecedero = esPerecedero;
    }

    /***------------------------------------------------------------***/

    public void actualizarPrecio(float nuevoPrecio) {
        this.valorReferenciaPorKgAnterior = this.valorReferenciaPorKg;
        this.valorReferenciaPorKg = nuevoPrecio;
        this.fechaUltimaActualizacion = LocalDate.now();
    }

    /***------------------------------------------------------------***/





}
