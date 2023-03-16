import java.util.ArrayList;

/**
 * Write a description of class OfertaLogistica here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class OfertaLogistica{
    private String nombre;
    private float costePorKmGranLogistica;
    private float costePorKmPequenaLogistica;
    private float costeFijo;


    //Constructor
    public OfertaLogistica(String nombre, float costePorKmGranLogistica, float costePorKmPequenaLogistica) {
        this.nombre = nombre;
        this.costePorKmGranLogistica = costePorKmGranLogistica;
        this.costePorKmPequenaLogistica = costePorKmPequenaLogistica;
    }

    //Getters
    public String getNombre() {
        return nombre;
    }
    public float getCostePorKmGranLogistica() {
        return costePorKmGranLogistica;
    }
    public float getCostePorKmPequenaLogistica() {
        return costePorKmPequenaLogistica;
    }
    public float getCosteFijo() {
        return costeFijo;
    }

    //Setters
    public void setCosteFijo(float costeFijo) {
        this.costeFijo = costeFijo;
    }

    //Método abstracto que añade un descuento a la oferta en porcentaje
    public abstract float aplicarDescuento(float precioLogistica,float porcentaje,Cliente c);


}
