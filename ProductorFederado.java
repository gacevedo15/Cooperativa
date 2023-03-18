import java.util.ArrayList;
import java.util.HashMap;

/** Aún falta corregir esta clase, necesitamos sacar el producto en comun y sumar las extensiones que tiene cada miembro */
public class ProductorFederado extends Productor {
    private ArrayList<Productor> miembros;

    //Constructor
    public ProductorFederado(String nombre, ArrayList<Productor> miembros, Producto producto) {
        super(nombre, TipoProductor.PEQUENO_PRODUCTOR);
        this.miembros = miembros;
        this.productos = new HashMap<>();
        productos.put(producto, calcularExtensionTotal(producto.getTipo()));
        extensionTotal=productos.get(producto);
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

    //Suma la extensión que tiene cada miembro del producto que sea igual al tipo de producto
    public float calcularExtensionTotal(TipoProducto tipoProducto){
        float extensionTotal = 0;
        for (Productor p : this.miembros) {
            for (Producto producto : p.getProductos().keySet()) {
                if (producto.getTipo() == tipoProducto) {
                    extensionTotal += p.getProductos().get(producto);
                }
            }
        }
        return extensionTotal;
    }



    //Devuelve un producto, el producto que coincide con el tipo de producto de todos los miembros


    //ToString que muestre los datos del productor federado, el tipo de producto, su extension total y el nombre de cada miembro
    @Override
    public String toString() {
        String s = "idProductor = " + getIdProductor()
                + "\nNombre = " + getNombre()
                + "\nTipo = " + getTipoProductor()
                + "\nTipo de producto = " + productos.keySet().iterator().next().getTipo()
                + "\nExtension total = " + extensionTotal
                + "\nMiembros = " + miembros.stream().map(Productor::getNombre).reduce("", (a, b) -> a + ", " + b).substring(2);
        return s;
    }



}