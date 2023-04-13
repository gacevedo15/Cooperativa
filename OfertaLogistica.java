import java.io.Serializable;

/**
 * Clase abstracta que representa una oferta logística genérica.
 * Contiene el nombre de la oferta y los costes por kilómetro para la gran y pequeña logística.
 * También tiene un coste fijo que puede ser asignado por subclases.
 * Las subclases deben implementar el método abstracto "aplicarDescuento" para añadir un descuento a la oferta.
 * @author Gustavo Acevedo Alfonso
 * @version 1.0
 */
public abstract class OfertaLogistica implements Serializable {
    private String nombre;
    private float costePorKmGranLogistica;
    private float costePorKmPequenaLogistica;
    private float costeFijo;
    protected TipoCliente tipoCliente;

    /**
     * Constructor de la clase OfertaLogistica que inicializa los atributos de la oferta.
     * @param nombre Nombre de la oferta.
     * @param costePorKmGranLogistica Coste por kilómetro para la gran logística.
     * @param costePorKmPequenaLogistica Coste por kilómetro para la pequeña logística.
     */
    public OfertaLogistica(String nombre, float costePorKmGranLogistica, float costePorKmPequenaLogistica) {
        this.nombre = nombre;
        this.costePorKmGranLogistica = costePorKmGranLogistica;
        this.costePorKmPequenaLogistica = costePorKmPequenaLogistica;
    }

    /**
     * Devuelve el nombre de la oferta.
     * @return Nombre de la oferta.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve el coste por kilómetro para la gran logística.
     * @return Coste por kilómetro para la gran logística.
     */
    public float getCostePorKmGranLogistica() {
        return costePorKmGranLogistica;
    }

    /**
     * Devuelve el coste por kilómetro para la pequeña logística.
     * @return Coste por kilómetro para la pequeña logística.
     */
    public float getCostePorKmPequenaLogistica() {
        return costePorKmPequenaLogistica;
    }

    /**
     * Devuelve el coste fijo de la oferta.
     * @return Coste fijo de la oferta.
     */
    public float getCosteFijo() {
        return costeFijo;
    }

    /**
     * Devuelve el tipo de cliente al que se aplica la oferta.
     */
    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    /**
     * Actualiza el nombre de la oferta.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Actualiza el coste por kilómetro para la gran logística.
     */
    public void setCostePorKmGranLogistica(float costePorKmGranLogistica) {
        this.costePorKmGranLogistica = costePorKmGranLogistica;
    }

    /**
     * Actualiza el coste por kilómetro para la pequeña logística.
     */
    public void setCostePorKmPequenaLogistica(float costePorKmPequenaLogistica) {
        this.costePorKmPequenaLogistica = costePorKmPequenaLogistica;
    }

    /**
     * Actualiza el coste fijo de la oferta.
     * @param costeFijo Coste fijo de la oferta.
     */
    public void setCosteFijo(float costeFijo) {
        this.costeFijo = costeFijo;
    }

    /**
     * Método abstracto que añade un descuento al precio de la logística.
     * @param precioLogistica Precio de la logística.
     * @param porcentaje Porcentaje de descuento.
     * @param c Cliente al que se le aplica el descuento.
     * @return Precio de la logística con el descuento aplicado.
     */
    public abstract float aplicarDescuento(float precioLogistica,float porcentaje,Cliente c);

    /**
     * ToString de la clase OfertaLogistica.
     */
    @Override
    public abstract String toString();

}
