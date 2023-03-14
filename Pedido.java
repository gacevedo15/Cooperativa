import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Pedido{

    private static int idPedidoActual=0;
    
    private int idPedido;
    
    private Date fechaPedido;
    private Date fechaEntrega;
    
    private float costeProducto;
    private float costeLogistica;
    private float precioFinal;
    
    private float pesoKg;
    
    private Cliente c;
    private Producto p;
    private Repartidor r;
    private ArrayList<Tramo> tramo;

    //Constructor
    public Pedido(Date fechaPedido, Date fechaEntrega, Cliente c, Producto p, Repartidor r) {
        this.idPedido = ++idPedidoActual;
        this.fechaPedido = fechaPedido;
        this.fechaEntrega = fechaEntrega;
        this.c = c;
        this.p = p;
        this.r = r;
        this.tramo = crearTramos();
    }

    /***------------------------------------------------------------***/

    //Getters
    public int getIdPedido() {
        return idPedido;
    }
    public Date getFechaPedido() {
        return fechaPedido;
    }
    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    //Setters
    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    //toString
    public String toString(){
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        return "Pedido: " + idPedido
                + "\nFecha Pedido: " + formatoFecha.format(fechaPedido)
                + "\nFecha Entrega: " + formatoFecha.format(fechaEntrega)
                + "\nCliente: " + c.getNombre()
                + "\nProducto: " + p.getTipo()
                + "\nRepartidor: " + r.getNombre();
    }

    /***------------------------------------------------------------***/

    //Método para crear los tramos
    public ArrayList<Tramo> crearTramos() {
        ArrayList<Tramo> tramos = new ArrayList<Tramo>();
        float distancia = c.getDistancia();

        if (p.esPerecedero()) {
            if (distancia <= 100.0f) {
                tramos.add(new Tramo(distancia,TipoLogistica.PEQUENA_LOGISTICA));
            } else {
                tramos.add(new Tramo(distancia-100,TipoLogistica.GRAN_LOGISTICA));
                tramos.add(new Tramo(100,TipoLogistica.PEQUENA_LOGISTICA));
            }
        } else {
            int numTramosGranLogistica = (int) Math.ceil(distancia / 50);
            float distanciaTramo;
            for (int i = 1; i <= numTramosGranLogistica; i++) {
                distanciaTramo = (i == numTramosGranLogistica) ? distancia % 50 : 50.0f;
                if (i == numTramosGranLogistica){
                    tramos.add(new Tramo(distanciaTramo, TipoLogistica.PEQUENA_LOGISTICA));
                }else{
                    tramos.add(new Tramo(distanciaTramo, TipoLogistica.GRAN_LOGISTICA));
                }
            }
        }
        return tramos;
    }

    //Método para mostrar los tramos y su tipo de logística
    public void mostrarTramos(){
        for(Tramo t:tramo){
            System.out.println(t.toString());
        }
    }

}
