import java.time.LocalDate;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * Clase que representa una cooperativa
 * Contiene los atributos y métodos de una cooperativa
 *
 * @author (Gustavo)
 * @version (1.0)
 */
public class TipoCooperativa {
    //Constantes
    public static float MAX_HA=5.0f;
    public static final int MAX_PRODUCTOS=5;

    public static final float MARGEN_DISTRIBUIDOR=1.05f;
    public static final float MARGEN_CONSUMIDOR_FINAL=1.15f;


    public static final float MIN_KG_DISTRIBUIDOR=1000.0f;
    public static final float MAX_KG_CONSUMIDOR_FINAL=100.0f;
    
    public static final float IVA=0.1f;

    public static DecimalFormat df = new DecimalFormat("#.##");
    
    //Atributos
    public ArrayList<Producto> productos;
    public ArrayList<Productor> productores;
    public ArrayList<Cliente> clientes;
    public ArrayList<Repartidor> repartidores;
    public ArrayList<Pedido> pedidos;

    public ArrayList<ResumenAnual> resumenesAnuales;

    private HashMap<TipoProducto,Float> beneficioTotalPorProducto;

    //Constructor
    public TipoCooperativa(){
        this.productos=new ArrayList<Producto>();
        this.productores=new ArrayList<Productor>();
        this.clientes=new ArrayList<Cliente>();
        this.repartidores=new ArrayList<Repartidor>();
        this.pedidos=new ArrayList<Pedido>();
        this.resumenesAnuales=new ArrayList<ResumenAnual>();
        this.beneficioTotalPorProducto=new HashMap<TipoProducto,Float>();
    }

    //Getters
    public HashMap<TipoProducto, Float> getBeneficioTotalPorProducto() {
        return beneficioTotalPorProducto;
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

        /* Con cada pedido que se realice, se actualizan los datos en el ResumenAnual que corresponda al año del pedido

         */
        for(ResumenAnual r:resumenesAnuales){
            if(r.getAnno()==pedido.getFechaPedido().getYear()){
                r.addPedido(pedido);
                r.actualizarVentasTotalesPorProducto(pedido.getProducto().getTipo(),pedido.getCantCompradaKg());
                asignarBeneficioPorProductor(pedido,r.getProductores());
            }
        }
    }

    /**
     * Método para actualizar el precio de un producto tanto en el ArrayList de productos como en los productores que contengan el producto
     * @param tipoProducto el tipo de producto del cual se desea actualizar el precio
     * @param nuevoPrecio el nuevo precio del producto
     */
    public void actualizarPrecioProducto(TipoProducto tipoProducto, float nuevoPrecio) {
        //Actualizamos el precio del producto en el ArrayList de productos
        for (Producto p : productos) {
            if (p.getTipo() == tipoProducto) {
                p.actualizarPrecio(nuevoPrecio);
            }
        }
        //Actualizamos el precio del producto en los productores
        for (Productor p : productores) {
            p.actualizarPrecioProducto(tipoProducto, nuevoPrecio);
        }
    }
}
