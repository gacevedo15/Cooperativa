
/**
 * Write a description of class Tramo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tramo{
    private float distancia;
    private TipoLogistica tipoLogistica;

    //Constructor
    public Tramo(float distancia, TipoLogistica tipoLogistica) {
        this.distancia = distancia;
        this.tipoLogistica = tipoLogistica;
    }

    //Getters
    public float getDistancia() {
        return distancia;
    }
    public TipoLogistica getTipoLogistica() {
        return tipoLogistica;
    }

    //toString
    public String toString(){
        return "Tramo: " + distancia + "km - " + tipoLogistica;
    }


}
