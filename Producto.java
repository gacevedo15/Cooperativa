import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Clase que representa un producto.
 * Contiene información acerca del tipo de producto, rendimiento por hectárea, valor de referencia por kilogramo,
 * valor de referencia por kilogramo anterior, fecha de última actualización, historial de valores de referencia
 * por kilogramo y si es perecedero o no.
 * @author Gustavo Acevedo Alfonso
 * @version 1.0
 */
public class Producto implements Serializable {

    /**
     * El tipo de producto.
     */
    private TipoProducto tipo;

    /**
     * El rendimiento por hectárea del producto.
     */
    private float rendimientoPorHectarea;

    /**
     * El valor de referencia por kilogramo del producto.
     */
    private float valorReferenciaPorKg;

    /**
     * El valor de referencia por kilogramo anterior del producto.
     */
    private float valorReferenciaPorKgAnterior;

    /**
     * La fecha de última actualización del valor de referencia del producto.
     */
    private LocalDate fechaUltimaActualizacion;

    /**
     * El historial de valores de referencia por kilogramo del producto.
     */
    protected HashMap<LocalDate, Float> historialValorReferenciaPorKg;

    /**
     * Indica si el producto es perecedero o no.
     */
    private boolean esPerecedero;

    /**
     * Constructor de la clase Producto.
     * @param tipo Tipo de producto.
     * @param rendimientoPorHectarea Rendimiento por hectárea del producto.
     * @param valorReferenciaPorKg Valor de referencia por kilogramo del producto.
     * @param esPerecedero Indica si el producto es perecedero o no.
     */
    public Producto(TipoProducto tipo, float rendimientoPorHectarea, float valorReferenciaPorKg, boolean esPerecedero) {
        this.tipo = tipo;
        this.rendimientoPorHectarea = rendimientoPorHectarea;
        this.valorReferenciaPorKg = valorReferenciaPorKg;
        this.fechaUltimaActualizacion = LocalDate.now();
        this.historialValorReferenciaPorKg = new HashMap<>();
        this.historialValorReferenciaPorKg.put(this.fechaUltimaActualizacion, this.valorReferenciaPorKg);
        this.esPerecedero = esPerecedero;
    }

    /**
     * Obtiene el tipo de producto.
     * @return Tipo de producto.
     */
    public TipoProducto getTipo() {
        return this.tipo;
    }

    /**
     * Obtiene el rendimiento por hectárea del producto.
     * @return Rendimiento por hectárea del producto.
     */
    public float getRendimientoPorHectarea() {
        return this.rendimientoPorHectarea;
    }

    /**
     * Obtiene el valor de referencia por kilogramo del producto.
     * @return Valor de referencia por kilogramo del producto.
     */
    public float getValorReferenciaPorKg() {
        return this.valorReferenciaPorKg;
    }

    /**
     * Obtiene el valor de referencia por kilogramo anterior del producto.
     * @return Valor de referencia por kilogramo anterior del producto.
     */
    public float getValorReferenciaPorKgAnterior() {
        return this.valorReferenciaPorKgAnterior;
    }

    /**
     * Obtiene la fecha de última actualización del valor de referencia del producto.
     * @return Fecha de última actualización del valor de referencia del producto.
     */
    public LocalDate getFechaUltimaActualizacion() {
        return this.fechaUltimaActualizacion;
    }

    /**
     * Obtiene el historial de valores de referencia por kilogramo del producto.
     * @return Historial de valores de referencia por kilogramo del producto.
     */
    public HashMap<LocalDate, Float> getHistorialValorReferenciaPorKg() {
        return this.historialValorReferenciaPorKg;
    }

    /**
     * Retorna si el producto es perecedero o no.
     * @return true si el producto es perecedero, false en caso contrario.
     */
    public boolean esPerecedero() {
        return this.esPerecedero;
    }

    /**
     * Establece el tipo de producto.
     * @param tipo el nuevo tipo de producto.
     */
    public void setTipo(TipoProducto tipo) {
        this.tipo = tipo;
    }

    /**
     * Establece el rendimiento por hectárea del producto.
     * @param rendimientoPorHectarea el nuevo rendimiento por hectárea del producto.
     */
    public void setRendimientoPorHectarea(float rendimientoPorHectarea) {
        this.rendimientoPorHectarea = rendimientoPorHectarea;
    }

    /**
     * Establece el valor de referencia por kilogramo del producto y actualiza su precio.
     * @param valorReferenciaPorKg el nuevo valor de referencia por kilogramo del producto
     * @param fechaActualizacion la fecha de actualización del valor de referencia por kilogramo del producto
     */
    public void setValorReferenciaPorKg(float valorReferenciaPorKg, LocalDate fechaActualizacion) {
        actualizarPrecio(valorReferenciaPorKg, fechaActualizacion);
    }

    /**
     * Establece si el producto es perecedero o no.
     * @param esPerecedero true si el producto es perecedero, false en caso contrario.
     */
    public void setEsPerecedero(boolean esPerecedero) {
        this.esPerecedero = esPerecedero;
    }

    /**
     * Actualiza el valor de referencia por kilogramo del producto con el nuevo precio y la fecha de actualización proporcionados.
     * También actualiza el historial del valor de referencia por kilogramo del producto.
     * @param nuevoPrecio el nuevo precio de referencia por kilogramo del producto.
     * @param fechaActualizacion la fecha en que se actualizó el precio de referencia por kilogramo del producto.
     */
    public void actualizarPrecio(float nuevoPrecio, LocalDate fechaActualizacion) {
        this.valorReferenciaPorKgAnterior = this.valorReferenciaPorKg;
        this.valorReferenciaPorKg = nuevoPrecio;
        this.fechaUltimaActualizacion = fechaActualizacion;
        this.historialValorReferenciaPorKg.put(this.fechaUltimaActualizacion, this.valorReferenciaPorKg);
    }

    /**
     * ToString sencillo de la clase Producto.
     * @return String con la información del producto.
     */
    public String toStringSencillo() {
        return  "       Tipo: " + tipo +
                "\n       Rendimiento Por Hectarea: " + rendimientoPorHectarea + " Kg/Ha" +
                "\n       Valor Referencia Por Kg: " + valorReferenciaPorKg + " €/Kg" +
                "\n       " + (esPerecedero ? "Perecedero" : "No Perecedero") +
                "\n";
    }

    /**
     * ToString detallado de la clase Producto.
     * @return String con la información detallada del producto.
     */
    public String toStringDetallado() {
        return "       Tipo: " + tipo +
                "\n       Rendimiento Por Hectarea: " + rendimientoPorHectarea + " Kg/Ha" +
                "\n       Valor Referencia Por Kg: " + valorReferenciaPorKg + " €/Kg" +
                "\n       Valor Referencia Por Kg Anterior: " + valorReferenciaPorKgAnterior + " €/Kg" +
                "\n       Fecha Ultima Actualizacion: " + fechaUltimaActualizacion +
                "\n       " + (esPerecedero ? "Perecedero" : "No Perecedero") +
                "\n       Historial Valor Referencia Por Kg: " + historialValorReferenciaPorKg.entrySet().stream().map(entry -> "\n             " + entry.getKey() + ": " + entry.getValue() + " €/Kg").collect(Collectors.joining()) +
                "\n";
    }
}
