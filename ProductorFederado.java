import java.util.ArrayList;
import java.util.HashMap;

/** Aún falta corregir esta clase, necesitamos sacar el producto en comun y sumar las extensiones que tiene cada miembro */
/**
 * Clase que representa un productor federado que tiene varios miembros.
 * Hereda de la clase "Productor". Contiene información acerca de los miembros que tiene el productor federado,
 * el producto que cultiva y la extensión total del mismo.
 */
public class ProductorFederado extends Productor {
    private ArrayList<Productor> miembros;

    /**
     * Constructor de la clase ProductorFederado.
     * @param nombre Nombre del productor federado.
     * @param miembros Miembros que tiene el productor federado.
     * @param producto Producto que cultiva el productor federado.
     */
    public ProductorFederado(String nombre, ArrayList<Productor> miembros, Producto producto) {
        super(nombre, TipoProductor.PEQUENO_PRODUCTOR);
        this.miembros = miembros;
        this.productos = new HashMap<>();
        productos.put(producto, calcularExtensionTotal(producto.getTipo()));
        extensionTotal=productos.get(producto);
    }

    /**
     * Devuelve los miembros del productor federado.
     * @return los miembros del productor federado.
     */
    public ArrayList<Productor> getMiembros() {
        return miembros;
    }

    /**
     * Establece los miembros del productor federado.
     * @param miembros los miembros del productor federado.
     */
    public void setMiembros(ArrayList<Productor> miembros) {
        this.miembros = miembros;
    }

    /**
     * Añade un miembro al productor federado.
     * @param p miembro que se añade al productor federado.
     */
    public void addMiembro(Productor p){
        this.miembros.add(p);
    }

    /**
     * Elimina un miembro del productor federado.
     * @param p miembro que se elimina del productor federado.
     */
    public void removeMiembro(Productor p){
        this.miembros.remove(p);
    }

    /**
     * Calcula la extensión total de un tipo de producto.
     * @param tipoProducto tipo de producto del que se quiere calcular la extensión total.
     * @return la extensión total del tipo de producto.
     */
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

    /**
     * Devuelve el producto en comun que tienen todos los miembros del productor federado.
     * @return el producto en comun que tienen todos los miembros del productor federado.
     */
    public Producto getProductoComun(){
        Producto productoComun = null;
        for (Productor p : this.miembros) {
            for (Producto producto : p.getProductos().keySet()) {
                if (productoComun == null) {
                    productoComun = producto;
                } else if (productoComun.getTipo() != producto.getTipo()) {
                    return null;
                }
            }
        }
        return productoComun;
    }


    /**
     * Devuelve una cadena con la información del productor federado.
     * @return una cadena con la información del productor federado.
     */
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