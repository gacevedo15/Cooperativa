import java.util.ArrayList;
import java.util.HashMap;

public class ProductorFederado extends Productor {
    private ArrayList<Productor> miembros;

    //Constructor
    public ProductorFederado(String nombre, HashMap<Producto, Float> producto, ArrayList<Productor> miembros) {
        super(nombre, TipoProductor.PEQUENO_PRODUCTOR, producto);
        this.miembros = new ArrayList<Productor>();
    }

    /***------------------------------------------------------------***/

    //Getters
    public ArrayList<Productor> getMiembros() {
        return miembros;
    }

    //Setters
    public void setMiembros(ArrayList<Productor> miembros) {
        this.miembros = miembros;
    }

    /***------------------------------------------------------------***/

    //Añadir miembro
    public void addMiembro(Productor p){
        this.miembros.add(p);
    }

    //Eliminar miembro
    public void removeMiembro(Productor p){
        this.miembros.remove(p);
    }



}