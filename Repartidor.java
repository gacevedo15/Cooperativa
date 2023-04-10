import java.io.Serializable;

public class Repartidor implements Serializable {

    private String nombre;

    //Constructor
    public Repartidor(String nombre) {
        this.nombre = nombre;
    }

    //Getters
    public String getNombre() {
        return nombre;
    }

}
