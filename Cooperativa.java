import java.util.ArrayList;

public class Cooperativa
{
    //Constantes
    public static float MAX_HA=5.0f;
    public static final  int MAX_PRODUCTOS=5;
    
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
    
    private static float calcularCosteProducto(TipoCliente tc, Producto p, float numKg){
        float margen;
        if(tc==TipoCliente.CONSUMIDOR_FINAL){
            margen=(1+IVA) * (MARGEN_CONSUMIDOR_FINAL);
        }else{
            margen=MARGEN_DISTRIBUIDOR;
        }
        return (margen*p.getPrecio()*numKg);
    }
    
    public static void main(String[] args){
        //Creamos la cooperativa
        Cooperativa c=new Cooperativa();

        //Creamos los productos y los añadimos a la lista de productos
        c.productos.add(new Producto(TipoProducto.ZANAHORIA, 150f, 1.5f, true));
        c.productos.add(new Producto(TipoProducto.ACEITUNA, 250f, 2.5f, true));
        c.productos.add(new Producto(TipoProducto.ALGODON, 350f, 3.5f, false));
        c.productos.add(new Producto(TipoProducto.CIRUELA, 400f, 4.5f, true));
        c.productos.add(new Producto(TipoProducto.MELOCOTON, 500f, 5.5f, true));

        //Creamos los productores sin lista de productos asociados
        c.productores.add(new Productor("Juan", TipoProductor.PEQUENO_PRODUCTOR));

        //Añadimos los productos asociados a los productores
        c.productores.get(0).getListaProductos().add(new ProductoAsociado(c.productos.get(0), 1.0f));
        c.productores.get(0).getListaProductos().add(new ProductoAsociado(c.productos.get(1), 2.0f));
        c.productores.get(0).getListaProductos().add(new ProductoAsociado(c.productos.get(2), 3.0f));
        c.productores.get(0).getListaProductos().add(new ProductoAsociado(c.productos.get(3), 4.0f));

        //Printamos los productores con sus productos asociados
        System.out.println(c.productores.get(0).toString2());

    }
}
