import java.util.HashMap;

public class Productor {

    private static int idProductorActual = 0;
    private int idProductor;
    private String nombre;
    private TipoProductor tipoProductor;
    private HashMap<Producto, Float> productos;

    //Constructor sin productos
    public Productor(String nombre, TipoProductor tipoProductor) {
        this.idProductor = ++idProductorActual;
        this.nombre = nombre;
        this.tipoProductor = tipoProductor;
        this.productos = new HashMap<Producto, Float>();
    }
    //Constructor con productos
    public Productor(String nombre, TipoProductor tipoProductor, HashMap<Producto, Float> productos) {
        this.idProductor = ++idProductorActual;
        this.nombre = nombre;
        this.tipoProductor = tipoProductor;
        this.productos = productos;
    }

    /***------------------------------------------------------------***/

    //Getters
    public int getIdProductor() {
        return idProductor;
    }
    public String getNombre() {
        return nombre;
    }
    public TipoProductor getTipoProductor() {
        return tipoProductor;
    }
    public HashMap<Producto, Float> getProductos() {
        return productos;
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
    }

    /***------------------------------------------------------------***/

    //Añadir producto
    public final void addProducto(Producto p, float extension){
        this.productos.put(p, extension);
    }

    //Eliminar producto
    public void removeProducto(Producto p){
        this.productos.remove(p);
    }

    //Buscar producto
    public boolean buscarProducto(Producto p){
        return this.productos.containsKey(p);
    }

    //Modificar extensión de un producto
    public void modificarExtensionProducto(Producto p, float extension){
        this.productos.replace(p, extension);
    }

    /***------------------------------------------------------------***/

    //Calcular extensión total
    public float calcularExtensionTotal(){
        float extensionTotal = 0;
        for (Producto p : this.productos.keySet()) {
            extensionTotal += this.productos.get(p);
        }
        return extensionTotal;
    }






}