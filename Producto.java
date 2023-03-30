import java.time.LocalDate;
import java.util.HashMap;

public class Producto {
    private final TipoProducto tipo;
    private final float rendimientoPorHectarea;
    private float valorReferenciaPorKg;
    private LocalDate fechaUltimaActualizacion;
    protected HashMap<LocalDate, Float> historialValorReferenciaPorKg;
    private final boolean esPerecedero;

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
    public LocalDate getFechaUltimaActualizacion() {
        return this.fechaUltimaActualizacion;
    }
    public HashMap<LocalDate, Float> getHistorialValorReferenciaPorKg() {
        return this.historialValorReferenciaPorKg;
    }
    public boolean esPerecedero() {
        return this.esPerecedero;
    }

    /***------------------------------------------------------------***/

    public void actualizarPrecio(LocalDate fecha,float nuevoPrecio) {
        this.valorReferenciaPorKg = nuevoPrecio;
        this.fechaUltimaActualizacion = fecha;
        this.historialValorReferenciaPorKg.put(this.fechaUltimaActualizacion, this.valorReferenciaPorKg);
    }

}
