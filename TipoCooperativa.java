import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Clase que representa una cooperativa
 * Contiene los atributos y métodos de una cooperativa
 *
 * @author Gustavo Acevedo Alfonso
 * @version 1.0
 */
public class TipoCooperativa implements Serializable {

    /**
     * Constante que representa el máximo de Ha que puede poseer un pequeño productor.
     */
    public static float MAX_HA=5.0f;

    /**
     * Constante que representa el máximo de Ha que puede poseer un pequeño productor.
     */
    public static final int MAX_PRODUCTOS=5;

    /**
     * Constante que representa el margen del distribuidor.
     */
    public static final float MARGEN_DISTRIBUIDOR=1.05f;

    /**
     * Constante que representa el margen del consumidor final.
     */
    public static final float MARGEN_CONSUMIDOR_FINAL=1.15f;

    /**
     * Constante que representa el mínimo de Kg que puede comprar un distribuidor.
     */
    public static final float MIN_KG_DISTRIBUIDOR=1000.0f;

    /**
     * Constante que representa el máximo de Kg que puede comprar un consumidor final.
     */
    public static final float MAX_KG_CONSUMIDOR_FINAL=100.0f;

    /**
     * Constante que representa el IVA.
     */
    public static final float IVA=0.1f;

    /**
     * Formato para los decimales con 2 cifras.
     */
    public static DecimalFormat df = new DecimalFormat("#.##");

    /**
     * Lista de productos que posee la cooperativa.
     */
    public ArrayList<Producto> productos;

    /**
     * Lista de productores que posee la cooperativa.
     */
    public ArrayList<Productor> productores;

    /**
     * Lista de clientes que posee la cooperativa.
     */
    public ArrayList<Cliente> clientes;

    /**
     * Lista de repartidores que posee la cooperativa.
     */
    public ArrayList<Repartidor> repartidores;

    /**
     * Lista de pedidos que posee la cooperativa.
     */
    public ArrayList<Pedido> pedidos;

    /**
     * Lista de resúmenes anuales que posee la cooperativa.
     */
    public ArrayList<ResumenAnual> resumenesAnuales;

    /**
     * HashMap que contiene el beneficio total por producto.
     */
    private HashMap<TipoProducto,Float> beneficioTotalPorProducto;

    /**
     * Constructor de la clase TipoCooperativa.
     * Inicializa los atributos de la cooperativa con listas vacías para productos, productores, clientes,
     * repartidores, pedidos, resúmenes anuales y un mapa vacío para el beneficio total por tipo de producto.
     */
    public TipoCooperativa(){
        this.productos=new ArrayList<Producto>();
        this.productores=new ArrayList<Productor>();
        this.clientes=new ArrayList<Cliente>();
        this.repartidores=new ArrayList<Repartidor>();
        this.pedidos=new ArrayList<Pedido>();
        this.resumenesAnuales=new ArrayList<ResumenAnual>();
        this.beneficioTotalPorProducto=new HashMap<TipoProducto,Float>();
    }

    /**
     * Devuelve la lista de productos de la cooperativa
     * @return lista de productos de la cooperativa
     */
    public ArrayList<Producto> getProductos() {
        return productos;
    }

    /**
     * Devuelve la lista de productores de la cooperativa
     * @return lista de productores de la cooperativa
     */
    public ArrayList<Productor> getProductores() {
        return productores;
    }

    /**
     * Devuelve el beneficio total por producto
     * @return beneficio total por producto
     */
    public HashMap<TipoProducto, Float> getBeneficioTotalPorProducto() {
        return beneficioTotalPorProducto;
    }

    /**
     * Devuelve los resúmenes anuales de la cooperativa
     * @return resúmenes anuales de la cooperativa
     */
    public ArrayList<ResumenAnual> getResumenesAnuales() {
        return resumenesAnuales;
    }

    /**
     * Aplica el IVA a un precio
     * @param precio sin IVA
     * @return precio con IVA aplicado
     */
    public static float aplicarIVA(float precio){
        return precio+(precio*IVA);
    }

    /**
     * Comprueba si un producto está disponible en la cooperativa
     * @param tipo el tipo de producto que se desea comprobar si está disponible
     * @return true si el producto está disponible, false en caso contrario
     */
    public boolean productoDisponible(TipoProducto tipo){
        boolean disponible=false;
        for(Productor p:productores){
            if(p.buscarProducto(tipo)){
                disponible=true;
            }
        }
        return disponible;
    }

    /**
     * Devuelve un producto de la cooperativa del tipo que solicite por parámetro
     * @param tipoProducto el tipo de producto que se desea obtener
     * @return producto el producto que se desea obtener
     */
    public Producto getProducto(TipoProducto tipoProducto){
        Producto producto=null;
        for(Producto p:productos){
            if(p.getTipo()==tipoProducto){
                producto=p;
            }
        }
        return producto;
    }

    /**
     * Añade un producto a la cooperativa
     * @param producto el producto que se desea añadir
     */
    public void addProducto(Producto producto){
        productos.add(producto);
    }

    /**
     * Elimina un producto de la cooperativa
     */
    public void eliminarProducto(Producto producto){
        productos.remove(producto);
    }

    /**
     * Añade un productor a la cooperativa
     * @param productor el productor que se desea añadir
     */
    public void addProductor(Productor productor){
        productores.add(productor);
    }

    /**
     * Elimina un productor de la cooperativa
     * @param productor el productor que se desea eliminar
     */
    public void eliminarProductor(Productor productor){
        productores.remove(productor);
    }

    /**
     * Calcula la cantidad total de un tipo de producto en kilogramos (Kg) que posee la cooperativa, producida por todos los productores
     * @param tipoProducto el tipo de producto del cual se desea conocer la cantidad total producida en Kg
     * @return cantidadProducto la cantidad total del tipo de producto en Kg que posee la cooperativa
     */
    public float calcularCantidadTotalEnKg(TipoProducto tipoProducto){
        float cantidadProducto=0.0f;
        for(Productor p:productores){
            //Si el productor produce el producto, se calcula la cantidad en Kg y se suma a la cantidad total
            if(p.buscarProducto(tipoProducto)){
                cantidadProducto+=p.calcularCantidadProductoEnKg(tipoProducto);
            }
        }
        return cantidadProducto;
    }

    /**
     * Calcular el total de Ha que posee la cooperativa de un tipo de producto
     * @param tipoProducto el tipo de producto del cual se desea conocer la cantidad total de Ha
     * @return haTotal la cantidad total de Ha que posee la cooperativa del tipo de producto
     */
    public float calcularHaTotalProducto(TipoProducto tipoProducto){
        float haTotal=0.0f;
        for(Productor p:productores){
            //Si el productor produce el producto, se calcula la cantidad en Ha y se suma a la cantidad total
            if(p.buscarProducto(tipoProducto)){
                haTotal+=p.getExtensionProducto(tipoProducto);
            }
        }
        return haTotal;
    }

    /**
     * Método que calcula el beneficio por producto de un productor
     * @param pedido el pedido del cual se desea calcular el beneficio del productor
     * @param productor el productor del cual se desea calcular el beneficio
     * @return beneficioProductorPorProducto el beneficio del productor por producto
     */
    public HashMap<TipoProducto, Float> calcularBeneficioProductor(Pedido pedido, Productor productor){
        float beneficioProductor=0.0f;
        HashMap<TipoProducto,Float> beneficioProductorPorProducto=new HashMap<TipoProducto,Float>();
        float cantTotalHa=calcularHaTotalProducto(pedido.getProducto().getTipo());
        for(HashMap.Entry<Producto,Float> entry : productor.getProductos().entrySet()){
            if(entry.getKey().getTipo()==pedido.getProducto().getTipo()){
                beneficioProductor=((entry.getValue()/cantTotalHa)*pedido.getBeneficioProductores());
            }
        }
        beneficioProductorPorProducto.put(pedido.getProducto().getTipo(),beneficioProductor);
        return beneficioProductorPorProducto;
    }

    /**
     * Método que asigna el beneficio obtenido por cada productor tras realizarse un pedido y lo añade a su beneficioTotal
     * @param pedido el pedido del cual se desea calcular el beneficio de los productores
     * @param productores los productores de los cuales se desea calcular el beneficio
     */
    public void asignarBeneficioPorProductor(Pedido pedido, ArrayList<Productor> productores){
        for(Productor p:productores){
            if(p.buscarProducto(pedido.getProducto().getTipo())){
                HashMap<TipoProducto,Float> beneficioProductor=calcularBeneficioProductor(pedido,p);
                for(HashMap.Entry<TipoProducto,Float> entry : beneficioProductor.entrySet()){
                    p.addBeneficioProductor(entry.getKey(),entry.getValue());
                }
            }
        }
    }

    /**
     * Método para calcular el beneficioTotalPorProducto de la cooperativa tras realizarse un pedido
     * @param pedido el pedido del cual se desea calcular el beneficioTotalPorProducto de la cooperativa
     * @return beneficioCooperativa HashMap con el beneficioTotalPorProducto de la cooperativa
     */
    public HashMap<TipoProducto,Float> calcularBeneficioCooperativa(Pedido pedido) {
        HashMap<TipoProducto, Float> beneficioCooperativa = new HashMap<TipoProducto, Float>();
        float beneficioProducto = pedido.getBeneficioCooperativa();
        beneficioCooperativa.put(pedido.getProducto().getTipo(), beneficioProducto);
        return beneficioCooperativa;
    }

    /**
     * Método para añadir el beneficioTotalPorProducto obtenido en cada pedido
     * @param beneficioCooperativa HashMap con el beneficioTotalPorProducto de la cooperativa
     * si la clave existe ya en el HashMap beneficioTotalPorProducto, se suma el valor de la clave, si no existe se añade
     */
    public void addBeneficioCooperativa(HashMap<TipoProducto,Float> beneficioCooperativa){
        for(HashMap.Entry<TipoProducto,Float> entry : beneficioCooperativa.entrySet()){
            if(beneficioTotalPorProducto.containsKey(entry.getKey())){
                beneficioTotalPorProducto.put(entry.getKey(),beneficioTotalPorProducto.get(entry.getKey())+entry.getValue());
            }else{
                beneficioTotalPorProducto.put(entry.getKey(),entry.getValue());
            }
        }
    }

    /**
     * Método que realiza un pedido, lo añade a la lista de pedidos y calcula el beneficio de los productores
     * y de la Cooperativa
     */
    public void realizarPedido(Cliente c, Producto p, float cantCompradaKg, OfertaLogistica o, LocalDate fechaPedido,LocalDate fechaEntrega){
        Pedido pedido = new Pedido(c, p, cantCompradaKg, o, fechaPedido,fechaEntrega);  //Se crea el pedido
        pedidos.add(pedido);                                                //Se añade el pedido a la lista de pedidos
        asignarBeneficioPorProductor(pedido,productores);                   //Se calcula el beneficio de los productores
        addBeneficioCooperativa(calcularBeneficioCooperativa(pedido));      //Se calcula el beneficio de la Cooperativa
        c.addPedido(pedido);                                                //Se añade el pedido al cliente


        boolean resumenEncontrado = false;
        //Se recorre la lista de resumenes anuales, si existe uno con el mismo año que el pedido, se añade el pedido al resumen
        for(ResumenAnual r : resumenesAnuales){
            if(r.getAnno() == pedido.getFechaPedido().getYear()){
                r.addPedido(pedido);
                asignarBeneficioPorProductor(pedido, r.getProductores());
                r.addBeneficioCooperativaPorProducto(calcularBeneficioCooperativa(pedido));
                resumenEncontrado = true;
                break;
            }
        }
        //Si no se ha encontrado el resumen anual, se crea uno nuevo y se añade el pedido
        if(!resumenEncontrado){
            ResumenAnual nuevoResumen = new ResumenAnual(pedido.getFechaPedido().getYear(),this);
            nuevoResumen.addPedido(pedido);
            asignarBeneficioPorProductor(pedido, nuevoResumen.getProductores());
            nuevoResumen.addBeneficioCooperativaPorProducto(calcularBeneficioCooperativa(pedido));
            resumenesAnuales.add(nuevoResumen);
        }
    }

    /**
     * Método para actualizar el precio de un producto tanto en el ArrayList de productos como en los productores que contengan el producto
     * @param tipoProducto el tipo de producto del cual se desea actualizar el precio
     * @param nuevoPrecio el nuevo precio del producto
     */
    public void actualizarPrecioProducto(TipoProducto tipoProducto, float nuevoPrecio, LocalDate fecha) {
        //Actualizamos el precio del producto en el ArrayList de productos
        for (Producto p : productos) {
            if (p.getTipo() == tipoProducto) {
                p.actualizarPrecio(nuevoPrecio, fecha);
            }
        }
        //Actualizamos el precio del producto en los productores
        for (Productor p : productores) {
            p.actualizarPrecioProducto(tipoProducto, nuevoPrecio, fecha);
        }
    }
}
