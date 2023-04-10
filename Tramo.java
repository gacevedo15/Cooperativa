import java.io.Serializable;

/**
 * La clase Tramo representa un tramo de la ruta que recorre la empresa de logística.
 * Contiene la distancia del tramo y el tipo de logística que se utiliza en ese tramo.
 * @author Gustavo Acevedo Alfonso
 * @version 1.0
 */
public class Tramo implements Serializable {

    /**
     * Distancia del tramo
     */
    private float distancia;

    /**
     * Tipo de logística que se utiliza en el tramo
     */
    private TipoLogistica tipoLogistica;

    /**
     * Constructor de la clase Tramo
     * @param distancia Distancia del tramo
     * @param tipoLogistica Tipo de logística que se utiliza en el tramo
     */
    public Tramo(float distancia, TipoLogistica tipoLogistica) {
        this.distancia = distancia;
        this.tipoLogistica = tipoLogistica;
    }

    /**
     * Devuelve la distancia del tramo
     * @return Distancia del tramo
     */
    public float getDistancia() {
        return distancia;
    }

    /**
     * Devuelve el tipo de logística que se utiliza en el tramo
     * @return Tipo de logística que se utiliza en el tramo
     */
    public TipoLogistica getTipoLogistica() {
        return tipoLogistica;
    }

    /**
     * Devuelve una cadena con la información del tramo
     * @return Cadena con la información del tramo
     */
    public String toString(){
        return "Tramo: " + distancia + "km - " + tipoLogistica;
    }

}
