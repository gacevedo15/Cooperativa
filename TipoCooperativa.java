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

    private float beneficioTotal; //costeTotal del pedido - beneficioTotal del productor

    //Constructor
    public TipoCooperativa(){
        this.productos=new ArrayList<Producto>();
        this.productores=new ArrayList<Productor>();
        this.clientes=new ArrayList<Cliente>();
        this.repartidores=new ArrayList<Repartidor>();
        this.pedidos=new ArrayList<Pedido>();
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
     * Método para añadir el beneficioCooperativa obtenido por la Cooperativa en un pedido
     * @param beneficioCooperativa el beneficioCooperativa obtenido en el pedido
     */
    public void addBeneficioCooperativa(float beneficioCooperativa){
        this.beneficioTotal+=beneficioCooperativa;
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
     * Método que calcula el beneficio obtenido por cada productor tras realizarse un pedido y lo añade a su beneficioTotal
     * @param pedido el pedido del cual se desea calcular el beneficio de los productores
     */
    public void asignarBeneficioPorProductor(Pedido pedido){
        float beneficioProductor;
        float cantTotalHa=calcularHaTotalProducto(pedido.getProducto().getTipo());
        //Recorremos los productores
        for(Productor p:productores){
           /* Si el productor tiene un producto del mismo tipo que el pedido, calculamos el beneficio del productor, para ello:
            * 1. Dividimos la cant de Ha del producto del productor por la cant de Ha total de todos los productores que poseen este producto
            * al resultado le multiplicamos la cantidad de Kg del producto del pedido
            * y al resultado el multiplicamos el pedido.obtenerValorProductoPorKg()
            */
            for(HashMap.Entry<Producto,Float> entry : p.getProductos().entrySet()){
                if(entry.getKey().getTipo()==pedido.getProducto().getTipo()){
                    beneficioProductor=((entry.getValue()/cantTotalHa)*pedido.getBeneficioProductores());
                    p.addBeneficioProductor(beneficioProductor);
                }
            }
        }
    }

    /**
     * Método que calcula el beneficio obtenido por la Cooperativa en un pedido
     * @param pedido el pedido del cual se desea calcular el beneficio de la Cooperativa
     */
    public void asignarBeneficioCooperativa(Pedido pedido){
        float beneficioCooperativa=0.0f;
        addBeneficioCooperativa(pedido.getBeneficioCooperativa());
    }

    /**
     * Método que realiza un pedido, lo añade a la lista de pedidos y calcula el beneficio de los productores
     * y de la Cooperativa
     */
    public void realizarPedido(Cliente c, Producto p, float cantCompradaKg, OfertaLogistica o) {
        Pedido pedido = new Pedido(c, p, cantCompradaKg, o);
        pedidos.add(pedido);
        asignarBeneficioPorProductor(pedido);
        asignarBeneficioCooperativa(pedido);
    }
}
