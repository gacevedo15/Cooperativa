import java.util.ArrayList;
import java.util.Date;
import java.text.DecimalFormat;


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

    public static DecimalFormat df = new DecimalFormat("#.##");
    
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

    //Método para añadir IVA a un precio
    public static float aplicarIVA(float precio){
        return precio+(precio*IVA);
    }

    
    public static void main(String[] args){
        //Creamos la cooperativa
        Cooperativa c=new Cooperativa();

        //Creamos los productos y los añadimos a la cooperativa
        c.productos.add(new Producto(TipoProducto.ACEITUNA,2500.0f,3.0f,false));
        c.productos.add(new Producto(TipoProducto.ZANAHORIA,500.0f,1.5f,true));
        c.productos.add(new Producto(TipoProducto.ALGODON,1000.0f,1.0f,false));
        c.productos.add(new Producto(TipoProducto.ACEITE,100.0f,0.60f,false));

        //Creamos los productores y los añadimos a la cooperativa
        c.productores.add(new Productor("Juan",TipoProductor.PEQUENO_PRODUCTOR));
        c.productores.add(new Productor("Pedro",TipoProductor.GRAN_PRODUCTOR));

        //Creamos los clientes y los añadimos a la cooperativa
        c.clientes.add(new Cliente("Ana",TipoCliente.DISTRIBUIDOR,180.0f));
        c.clientes.add(new Cliente("Luis149",TipoCliente.CONSUMIDOR_FINAL,350.01f));
        c.clientes.add(new Cliente("Luis150",TipoCliente.CONSUMIDOR_FINAL,249.99f));

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
        c.pedidos.add(new Pedido(c.clientes.get(1),c.productos.get(3),c.repartidores.get(0),50.0f,oferta2));
        c.pedidos.add(new Pedido(c.clientes.get(2),c.productos.get(3),c.repartidores.get(0),50.0f,oferta2));


        //Mostramos el pedido 1
        System.out.println(c.pedidos.get(0).toString());
        //Mostramos los tramos del pedido 1
        c.pedidos.get(0).mostrarTramos();


        //Mostramos el pedido 2
        System.out.println(c.pedidos.get(1).toString());

        //Mostramos los tramos del pedido 2
        c.pedidos.get(1).mostrarTramos();

        //Mostramos el pedido 3
        System.out.println(c.pedidos.get(2).toString());

        //Mostramos los tramos del pedido 3
        c.pedidos.get(2).mostrarTramos();



    }
}
