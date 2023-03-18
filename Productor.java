import java.util.HashMap;

public class Productor {

    private static int idProductorActual = 0;
    private int idProductor;
    private String nombre;
    private TipoProductor tipoProductor;
    protected HashMap<Producto, Float> productos;
    protected float extensionTotal;

    //Constructor sin productos
    public Productor(String nombre, TipoProductor tipoProductor) {
        this.idProductor = ++idProductorActual;
        this.nombre = nombre;
        this.tipoProductor = tipoProductor;
        this.productos = new HashMap<>();
        this.extensionTotal = 0;
    }
    //Constructor con productos
    public Productor(String nombre, TipoProductor tipoProductor, HashMap<Producto, Float> productos) {
        this.idProductor = ++idProductorActual;
        this.nombre = nombre;
        this.tipoProductor = tipoProductor;
        this.productos = productos;
        this.extensionTotal = calcularExtensionTotal();
    }

    /***------------------------------------------------------------***/

    //Getters
    public int getIdProductor() {
        return this.idProductor;
    }
    public String getNombre() {
        return this.nombre;
    }
    public TipoProductor getTipoProductor() {
        return this.tipoProductor;
    }
    public HashMap<Producto, Float> getProductos() {
        return this.productos;
    }
    public float getExtensionTotal() {
        return this.extensionTotal;
    }

    //Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public final void setTipoProductor(TipoProductor tipoProductor) {
        this.tipoProductor = tipoProductor;
    }
    public final void setProductos(HashMap<Producto, Float> productos) {
        this.productos = productos;
        this.extensionTotal = calcularExtensionTotal();
    }

    /***------------------------------------------------------------***/

    //Añadir producto
    public final void addProducto(Producto p, float extension){
        this.productos.put(p, extension);
        this.extensionTotal += extension;
    }

    //Eliminar producto segun el tipo
    public void removeProducto(TipoProducto tipoProducto){
        for (Producto p : this.productos.keySet()) {
            if (p.getTipo() == tipoProducto) {
                this.extensionTotal -= this.productos.get(p);
                this.productos.remove(p);
                break;
            }
        }
    }

    //Buscar producto por tipo
    public boolean buscarProducto(TipoProducto tipoProducto){
        for (Producto p : this.productos.keySet()) {
            if (p.getTipo() == tipoProducto) {
                return true;
            }
        }
        return false;
    }

    //Modificar extensión de un producto segun el tipo
    public void modificarExtensionProducto(TipoProducto tipoProducto, float extension){
        for (Producto p : this.productos.keySet()) {
            if (p.getTipo() == tipoProducto) {
                this.extensionTotal -= this.productos.get(p);
                this.productos.put(p, extension);
                this.extensionTotal += extension;
                break;
            }
        }
    }

    //Calcula la extensión total de los productos
    public float calcularExtensionTotal(){
        extensionTotal = 0;
        for (Producto p : this.productos.keySet()) {
           extensionTotal += this.productos.get(p);
        }
        return extensionTotal;
    }

    /***------------------------------------------------------------***/


    //ToString que printe toda la información del productor y el tipo de producto con su extensión
    @Override
    public String toString() {
        String s = "idProductor = " + idProductor
                + "\nNombre = " + nombre
                + "\nTipoProductor = " + tipoProductor
                + "\nProductos:\n";
        for (Producto p : this.productos.keySet()) {
            s += "       " + p.getTipo() + " - " + this.productos.get(p) + " ha\n";
        }
        s += "Extensión Total = " + extensionTotal + " ha\n";
        return s;
    }

}