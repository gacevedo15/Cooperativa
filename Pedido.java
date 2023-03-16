import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Pedido{

    private static int idPedidoActual=0;
    private int idPedido;
    private Date fechaPedido;
    private Date fechaEntrega;

    private Cliente c;
    private Producto p;
    private Repartidor r;
    private Logistica l;
    private OfertaLogistica o;

    private float costeProducto;
    private float cantCompradaKg;
    private float costeLogistica;
    private float costeTotal;

    //Constructor
    public Pedido(Cliente c, Producto p, Repartidor r, float cantCompradaKg, OfertaLogistica o) {
        this.idPedido = ++idPedidoActual;
        this.fechaPedido = new Date();
        this.c = c;
        this.p = p;
        this.r = r;
        this.l = new Logistica(c, p, cantCompradaKg,o);
        this.cantCompradaKg = cantCompradaKg;
        this.costeLogistica = l.calcularCosteLogistica(p,c,cantCompradaKg);
    }

    //Getters
    public float getCosteLogistica() {
        return costeLogistica;
    }

    //Mostrar tramos de la logistica
    public void mostrarTramos() {
        l.mostrarTramos();
    }



    //ToString
    public String toString() {
        return "ID Pedido: " + idPedido + " Cliente: " + c.getNombre() + " Producto: " + p.getTipo() + " Repartidor: " + r.getNombre() + " Coste Producto: " + costeProducto + " Coste Logística: " + costeLogistica + " Coste Total: " + costeTotal;
    }

}
