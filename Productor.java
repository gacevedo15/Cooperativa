import java.util.HashMap;

public class Productor {

    private static int idProductorActual = 0;
    private int idProductor;
    private String nombre;
    private TipoProductor tipoProductor;
    protected HashMap<Producto, Float> productos;
    protected float extensionTotal;
    protected HashMap<TipoProducto,Float> beneficioTotalPorProducto;

    //Constructor sin productos
    public Productor(String nombre, TipoProductor tipoProductor) {
        this.idProductor = ++idProductorActual;
        this.nombre = nombre;
        this.tipoProductor = tipoProductor;
        this.productos = new HashMap<>();
        this.extensionTotal = 0;
        this.beneficioTotalPorProducto = new HashMap<>();
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
    public HashMap<TipoProducto, Float> getBeneficioTotalPorProducto() {
        return this.beneficioTotalPorProducto;
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

    //Devuelve la extensión de un producto segun el tipo
    public float getExtensionProducto(TipoProducto tipoProducto){
        for (Producto p : this.productos.keySet()) {
            if (p.getTipo() == tipoProducto) {
                return this.productos.get(p);
            }
        }
        return 0;
    }

    //Calcula la extensión total de los productos
    public float calcularExtensionTotal(){
        extensionTotal = 0;
        for (Producto p : this.productos.keySet()) {
           extensionTotal += this.productos.get(p);
        }
        return extensionTotal;
    }

    //Método para calcular la cantidad en Kg de un producto, teniendo en cuenta su rendimiento por Ha y cantidad de Ha
    public float calcularCantidadProductoEnKg(TipoProducto tipoProducto){
        for (Producto p : this.productos.keySet()) {
            if (p.getTipo() == tipoProducto) {
                return p.getRendimientoPorHectarea() * this.productos.get(p);
            }
        }
        return 0;
    }

    /** 
     * Método para añadir el beneficioTotalPorProducto obtenido en cada pedido
     * @param tipoProducto el tipo de producto
     * @param beneficioProductor el beneficioProductor obtenido
     */
    public void addBeneficioProductor(TipoProducto tipoProducto,float beneficioProductor){
        if (this.beneficioTotalPorProducto.containsKey(tipoProducto)) {
            this.beneficioTotalPorProducto.put(tipoProducto, this.beneficioTotalPorProducto.get(tipoProducto) + beneficioProductor);
        } else {
            this.beneficioTotalPorProducto.put(tipoProducto, beneficioProductor);
        }
    }

    /**
     * Método para actualizar el precio de un producto
     * @param tipoProducto
     * @param precio
     */
    public void actualizarPrecioProducto(TipoProducto tipoProducto, float precio){
        for (Producto p : this.productos.keySet()) {
            if (p.getTipo() == tipoProducto) {
                p.actualizarPrecio(precio);
                break;
            }
        }
    }

    /***------------------------------------------------------------***/


    //ToString que printe toda la información del productor y el tipo de producto con su extensión
    @Override
    public String toString(){
        String s = "ID: " + this.idProductor + " - Nombre: " + this.nombre + " - Tipo: " + this.tipoProductor + " - Productos: ";
        for (Producto p : this.productos.keySet()) {
            s += p.getTipo() + " (" + this.productos.get(p) + " Ha) ";
        }
        return s;
    }

}