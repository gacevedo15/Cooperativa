import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * La clase Productor representa un productor de la cooperativa.
 * Contiene información acerca de un productor, sus productos, su extensión total y su beneficio total por producto.
 * @author Gustavo Acevedo Alfonso
 * @version 1.0
 */
public class Productor implements Serializable {

    /**
     * El nombre del productor.
     */
    private String nombre;

    /**
     * El tipo de productor.
     */
    private TipoProductor tipoProductor;

    /**
     * Los productos que cultiva el productor.
     */
    protected HashMap<Producto, Float> productos;

    /**
     * La extensión total del productor.
     */
    protected float extensionTotal;

    /**
     * El beneficio total por producto del productor.
     */
    protected HashMap<TipoProducto,Float> beneficioTotalPorProducto;

    /**
     * Constructor de la clase Productor sin productos.
     * @param nombre Nombre del productor.
     * @param tipoProductor Tipo de productor.
     */
    public Productor(String nombre, TipoProductor tipoProductor) {
        this.nombre = nombre;
        this.tipoProductor = tipoProductor;
        this.productos = new HashMap<>();
        this.extensionTotal = 0;
        this.beneficioTotalPorProducto = new HashMap<>();
    }

    /**
     * Constructor de la clase Productor con productos.
     * @param nombre Nombre del productor.
     * @param tipoProductor Tipo de productor.
     * @param productos Productos que cultiva el productor.
     */
    public Productor(String nombre, TipoProductor tipoProductor, HashMap<Producto, Float> productos) {
        this.nombre = nombre;
        this.tipoProductor = tipoProductor;
        this.productos = productos;
        this.extensionTotal = calcularExtensionTotal();
    }

    /**
     * Devuelve el nombre del productor.
     * @return el nombre del productor.
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Devuelve el tipo de productor.
     * @return el tipo de productor.
     */
    public TipoProductor getTipoProductor() {
        return this.tipoProductor;
    }

    /**
     * Devuelve los productos que cultiva el productor.
     * @return los productos que cultiva el productor.
     */
    public HashMap<Producto, Float> getProductos() {
        return this.productos;
    }


    /**
     * Devuelve la extensión total de los productos que cultiva el productor.
     * @return la extensión total de los productos que cultiva el productor.
     */
    public float getExtensionTotal() {
        return this.extensionTotal;
    }

    /**
     * Devuelve el beneficio total por producto de los productos que cultiva el productor.
     * @return el beneficio total por producto de los productos que cultiva el productor.
     */
    public HashMap<TipoProducto, Float> getBeneficioTotalPorProducto() {
        return this.beneficioTotalPorProducto;
    }

    /**
     * Actualiza el nombre del productor.
     * @param nombre El nuevo nombre del productor.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Actualiza el tipo de productor.
     * @param tipoProductor El nuevo tipo de productor.
     */
    public final void setTipoProductor(TipoProductor tipoProductor) {
        this.tipoProductor = tipoProductor;
    }

    /**
     * Actualiza los productos que cultiva el productor y la extensión total.
     * @param productos Los nuevos productos que cultiva el productor.
     */
    public final void setProductos(HashMap<Producto, Float> productos) {
        this.productos = productos;
        this.extensionTotal = calcularExtensionTotal();
    }


    /**
     * Añade un nuevo producto a la lista de productos del productor y su correspondiente extensión.
     * @param p el producto a añadir.
     * @param extension la extensión correspondiente al producto a añadir.
     */
    public final void addProducto(Producto p, float extension){
        this.productos.put(p, extension);
        this.extensionTotal += extension;
    }

    /**
     * Elimina un producto de la lista de productos del productor y su correspondiente extensión.
     * @param tipoProducto el tipo de producto a eliminar.
     */
    public void removeProducto(TipoProducto tipoProducto) {
        Iterator<Map.Entry<Producto, Float>> it = this.productos.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Producto, Float> entry = it.next();
            Producto p = entry.getKey();
            if (p.getTipo() == tipoProducto) {
                this.extensionTotal -= entry.getValue();
                it.remove();
                break;
            }
        }
    }

    /**
     * Comprueba si el productor tiene un producto.
     * @param tipoProducto el tipo de producto a comprobar.
     * @return true si el productor tiene el producto, false en caso contrario.
     */
    public boolean buscarProducto(TipoProducto tipoProducto){
        for (Producto p : this.productos.keySet()) {
            if (p.getTipo() == tipoProducto) {
                return true;
            }
        }
        return false;
    }

    /**
     * Modifica la extensión de un producto.
     * @param tipoProducto el tipo de producto a modificar.
     * @param extension la nueva extensión del producto.
     */
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

    /**
     * Devuelve la extensión de un producto.
     * @param tipoProducto el tipo de producto.
     * @return la extensión del producto.
     */
    public float getExtensionProducto(TipoProducto tipoProducto){
        for (Producto p : this.productos.keySet()) {
            if (p.getTipo() == tipoProducto) {
                return this.productos.get(p);
            }
        }
        return 0;
    }

    /**
     * Método para calcular la extensión total de los productos que cultiva el productor.
     * @return la extensión total de los productos que cultiva el productor.
     */
    public float calcularExtensionTotal(){
        extensionTotal = 0;
        for (Producto p : this.productos.keySet()) {
           extensionTotal += this.productos.get(p);
        }
        return extensionTotal;
    }

    /**
     * Método para calcular la cantidad de producto en Kg.
     * Multiplica el rendimiento por hectarea del producto por su extensión.
     * @param tipoProducto el tipo de producto.
     * @return la cantidad de producto en Kg.
     */
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
     * Método para actualizar el precio de un producto.
     * @param tipoProducto el tipo de producto.
     * @param precio el nuevo precio del producto.
     */
    public void actualizarPrecioProducto(TipoProducto tipoProducto, float precio, LocalDate fecha){
        for (Producto p : this.productos.keySet()) {
            if (p.getTipo() == tipoProducto) {
                p.actualizarPrecio(precio, fecha);
                break;
            }
        }
    }

    /**
     * Devuelve un String con la información del productor y sus productos.
     * @return una cadena de texto que representa al productor.
     */
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder("Nombre: " + this.nombre + " - Tipo: " + this.tipoProductor + " - Productos: ");
        for (Producto p : this.productos.keySet()) {
            s.append(p.getTipo()).append(" (").append(this.productos.get(p)).append(" Ha) ");
        }
        return s.toString();
    }

}