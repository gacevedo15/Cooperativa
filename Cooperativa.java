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
    private ArrayList<Productor> productores;
    private ArrayList<Cliente> clientes;
    private ArrayList<Repartidor> repartidores;
    private ArrayList<Pedido> pedidos;
    
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
        Producto producto = new Producto(TipoProducto.ZANAHORIA,1300.0f,2.5f,true);
        System.out.println(producto.getPrecio());
    }
}
