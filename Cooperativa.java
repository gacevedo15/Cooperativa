import java.util.ArrayList;
import java.util.Date;

public class Cooperativa
{
    //Constantes
    public static float MAX_HA=5.0f;
    public static final int MAX_PRODUCTOS=5;

    
    public static final float MARGEN_DISTRIBUIDOR =1.05f;
    public static final float MIN_KG_DISTRIBUIDOR=1000.0f;
    
    public static final float MARGEN_CONSUMIDOR_FINAL =1.15f;
    public static final float MAX_KG_CONSUMIDOR_FINAL=100.0f;
    
    public static final float IVA=0.1f;
    
    //Atributos
    private ArrayList<Producto> productos;
    private ArrayList<Productor> productores;
    private ArrayList<Cliente> clientes;
    private ArrayList<Repartidor> repartidores;
    private ArrayList<Pedido> pedidos;

    //Constructor
    public Cooperativa(){
        this.productos=new ArrayList<Producto>();
        this.productores=new ArrayList<Productor>();
        this.clientes=new ArrayList<Cliente>();
        this.repartidores=new ArrayList<Repartidor>();
        this.pedidos=new ArrayList<Pedido>();
    }

    //Método para calcular el coste de un producto según el tipo de cliente
    public static float calcularCosteProductoPorKg(Producto p, Cliente c){
        float costeProducto=0.0f;
        if(c.getTipoCliente()==TipoCliente.CONSUMIDOR_FINAL){
            costeProducto=p.getValorReferenciaPorKg()* MARGEN_CONSUMIDOR_FINAL *IVA;
        }else if(c.getTipoCliente()==TipoCliente.DISTRIBUIDOR){
            costeProducto=p.getValorReferenciaPorKg()* MARGEN_DISTRIBUIDOR;
        }
        return costeProducto;
    }

    
    public static void main(String[] args){
        //Creamos la cooperativa
        Cooperativa c=new Cooperativa();

        //Creamos los productos y los añadimos a la cooperativa
        c.productos.add(new Producto(TipoProducto.ACEITUNA,2500.0f,3.0f,false));
        c.productos.add(new Producto(TipoProducto.ZANAHORIA,500.0f,1.5f,true));
        c.productos.add(new Producto(TipoProducto.ALGODON,1000.0f,1.0f,false));
        c.productos.add(new Producto(TipoProducto.ACEITE,100.0f,0.50f,false));

        //Creamos los productores y los añadimos a la cooperativa
        c.productores.add(new Productor("Juan",TipoProductor.PEQUENO_PRODUCTOR));
        c.productores.add(new Productor("Pedro",TipoProductor.GRAN_PRODUCTOR));

        //Creamos los clientes y los añadimos a la cooperativa
        c.clientes.add(new Cliente("Ana",TipoCliente.DISTRIBUIDOR,180f));
        c.clientes.add(new Cliente("Luis",TipoCliente.CONSUMIDOR_FINAL,180f));

        //Creamos los repartidores y los añadimos a la cooperativa
        c.repartidores.add(new Repartidor("Repartidor1"));

        //Añadimos productos a los productores
        c.productores.get(0).addProducto(c.productos.get(3), 2.0f);

        //Date de ejemplo


        //Creamos ofertas de logistica de prueba
        OfertaLogistica oferta1=new EnvioEstandar("Oferta1",0.05f,0.01f,TipoCliente.DISTRIBUIDOR);
        OfertaLogistica oferta2=new EnvioEstandar("Oferta2",0.05f,0.01f,TipoCliente.CONSUMIDOR_FINAL);

        //Creamos pedidos y lo añadimos a la cooperativa
        c.pedidos.add(new Pedido(c.clientes.get(0),c.productos.get(3),c.repartidores.get(0),2000.0f,oferta1));
        c.pedidos.add(new Pedido(c.clientes.get(1),c.productos.get(3),c.repartidores.get(0),90.0f,oferta2));


        //Mostramos el pedido 1
        System.out.println(c.pedidos.get(0).toString());
        //Mostramos los tramos del pedido 1
        c.pedidos.get(0).mostrarTramos();
        //Mostrar coste total logistica del pedido 1
        System.out.println("Coste total logistica: "+c.pedidos.get(0).getCosteLogistica());

        //Mostramos el pedido 2
        System.out.println(c.pedidos.get(1).toString());
        //Mostramos los tramos del pedido 2
        c.pedidos.get(1).mostrarTramos();
        //Mostrar coste total logistica del pedido 2
        System.out.println("Coste total logistica: "+c.pedidos.get(1).getCosteLogistica());

    }
}
