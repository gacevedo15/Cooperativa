import java.time.LocalDate;
import java.util.HashMap;

public class Producto {
    private TipoProducto tipo;
    private float rendimientoPorHectarea;
    private float valorReferenciaPorKg;
    private float valorReferenciaPorKgAnterior;
    private LocalDate fechaUltimaActualizacion;
    protected HashMap<LocalDate, Float> historialValorReferenciaPorKg;
    private boolean esPerecedero;

    //Constructor
    public Producto(TipoProducto tipo, float rendimientoPorHectarea, float valorReferenciaPorKg, boolean esPerecedero) {
        this.tipo = tipo;
        this.rendimientoPorHectarea = rendimientoPorHectarea;
        this.valorReferenciaPorKg = valorReferenciaPorKg;
        this.fechaUltimaActualizacion = LocalDate.now();
        this.historialValorReferenciaPorKg = new HashMap<>();
        this.historialValorReferenciaPorKg.put(this.fechaUltimaActualizacion, this.valorReferenciaPorKg);
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
    public HashMap<LocalDate, Float> getHistorialValorReferenciaPorKg() {
        return this.historialValorReferenciaPorKg;
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
        //now + 1 day
        this.fechaUltimaActualizacion = LocalDate.now().plusDays(1);
        this.historialValorReferenciaPorKg.put(this.fechaUltimaActualizacion, this.valorReferenciaPorKg);
    }

    /***------------------------------------------------------------***/





}
