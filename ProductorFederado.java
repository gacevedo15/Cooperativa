import java.util.ArrayList;

public class ProductorFederado extends Productor {
    private ArrayList<Productor> miembros;

    //Constructor
    public ProductorFederado(String nombre, ArrayList<ProductoAsociado> listaProductos, ArrayList<Productor> miembros) {
        super(nombre, TipoProductor.PEQUENO_PRODUCTOR, listaProductos);
        this.miembros = new ArrayList<Productor>();
        this.miembros = miembros;
    }

    public void addMiembro(Productor p){
        this.miembros.add(p);
    }

    public void removeMiembro(Productor p){
        this.miembros.remove(p);
    }

    public ArrayList<Productor> getMiembros(){
        return this.miembros;
    }


}