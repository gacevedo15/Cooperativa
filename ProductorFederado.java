import java.util.ArrayList;
import java.util.HashMap;

/** Aún falta corregir esta clase, necesitamos sacar el producto en comun y sumar las extensiones que tiene cada miembro */
public class ProductorFederado extends Productor {
    private ArrayList<Productor> miembros;

    //Constructor
    public ProductorFederado(String nombre, ArrayList<Productor> miembros) {
        super(nombre, TipoProductor.PEQUENO_PRODUCTOR);
        this.miembros = miembros;
        this.productos = miembros.get(0).getProductos();
        this.extensionTotal = calcularExtensionTotal();

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

    //Calcular la extension total de todos los miembros


    //Devuelve un producto, el producto que coincide con el tipo de producto de todos los miembros


    //ToString que muestre los datos del productor federado, el producto en comun y los miembros
    @Override
    public String toString() {
        String s = "idProductor = " + getIdProductor()
                + "\nNombre = " + getNombre()
                + "\nTipoProductor = " + getIdProductor()
                + "\nProductos = " + productos
                + "\nExtensionTotal = " + extensionTotal
                + "\nMiembros = " + miembros;
        return s;
    }


}