import java.util.ArrayList;
import java.util.Date;

public class Cooperativa
{
    //Constantes
    public static float MAX_HA=5.0f;
    public static final int MAX_PRODUCTOS=5;
    
    public static final float MARGEN_DISTRIBUIDOR=0.05f;
    public static final float MAX_KG_DISTRIBUIDOR=1000.0f;
    
    public static final float MARGEN_CONSUMIDOR_FINAL=0.15f;
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

    //Mostrar pedidos
    public void mostrarPedidos(){
        for(Pedido p:pedidos){
            System.out.println(p.toString());
        }
    }
    
    private static float calcularCosteProducto(TipoCliente tc, Producto p, float numKg){
        float margen;
        if(tc==TipoCliente.CONSUMIDOR_FINAL){
            margen=(1+IVA) * (MARGEN_CONSUMIDOR_FINAL);
        }else{
            margen=MARGEN_DISTRIBUIDOR;
        }
        return (margen*p.getValorReferenciaPorKg()*numKg);
    }
    
    public static void main(String[] args){
        //Creamos la cooperativa
        Cooperativa c=new Cooperativa();

        //Creamos los productos y los añadimos a la cooperativa
        c.productos.add(new Producto(TipoProducto.ACEITUNA,2500.0f,3.0f,false));
        c.productos.add(new Producto(TipoProducto.ZANAHORIA,500.0f,1.5f,true));
        c.productos.add(new Producto(TipoProducto.ALGODON,1000.0f,1.0f,false));

        //Creamos los productores y los añadimos a la cooperativa
        c.productores.add(new Productor("Juan",TipoProductor.PEQUENO_PRODUCTOR));
        c.productores.add(new Productor("Pedro",TipoProductor.GRAN_PRODUCTOR));

        //Creamos los clientes y los añadimos a la cooperativa
        c.clientes.add(new Cliente("Ana",TipoCliente.CONSUMIDOR_FINAL,675.37f));
        c.clientes.add(new Cliente("Luis",TipoCliente.DISTRIBUIDOR,1258.0f));

        //Creamos los repartidores y los añadimos a la cooperativa
        c.repartidores.add(new Repartidor("Repartidor1"));
        c.repartidores.add(new Repartidor("Repartidor2"));

        //Añadimos productos a los productores
        c.productores.get(0).addProducto(c.productos.get(0), 2.0f);
        c.productores.get(0).addProducto(c.productos.get(1), 1.0f);
        c.productores.get(1).addProducto(c.productos.get(2), 3.0f);

        //Date de ejemplo
        Date testFecha=new Date();

        //Creamos pedidos y lo añadimos a la cooperativa
        c.pedidos.add(new Pedido(testFecha,testFecha,c.clientes.get(0),c.productos.get(0),c.repartidores.get(0)));
        c.pedidos.add(new Pedido(testFecha,testFecha,c.clientes.get(1),c.productos.get(1),c.repartidores.get(1)));

        //Mostramos el pedido
        System.out.println(c.pedidos.get(1).toString());

        //Mostramos los tramos del pedido
        c.pedidos.get(1).mostrarTramos();



    }
}
