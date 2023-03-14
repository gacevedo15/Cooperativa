import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Pedido{

    private static int idPedidoActual=0;
    private static float COSTE_KM=1.5f;

    
    private int idPedido;
    
    private Date fechaPedido;
    private Date fechaEntrega;
    
    private float costeProducto;
    private float costeLogistica;
    private float precioFinal;

    private float cantCompradaKg;
    
    private Cliente c;
    private Producto p;
    private Repartidor r;
    private ArrayList<Tramo> tramos;

    //Constructor
    public Pedido(Date fechaPedido, Date fechaEntrega, Cliente c, Producto p, Repartidor r, float cantCompradaKg) {
        this.idPedido = ++idPedidoActual;
        this.fechaPedido = fechaPedido;
        this.fechaEntrega = fechaEntrega;
        this.c = c;
        this.p = p;
        this.r = r;
        this.cantCompradaKg = cantCompradaKg;
        this.tramos = crearTramos();
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
    public float getCantCompradaKg() {
        return cantCompradaKg;
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
        float costePorTramo = calcularCosteTramo();
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
        for(Tramo t: tramos){
            System.out.println(t.toString());
        }
    }

    //Calcular el coste de cada tramo
    public float calcularCosteTramo(){
        return 0.5f*p.getValorReferenciaPorKg()*cantCompradaKg;
    }

    //Calcular coste por Km
    public float calcularCosteDistancia(){
        return COSTE_KM*c.getDistancia();
    }

    //Devuelve la cantidad de tramos de gran logística
    public int numTramosGranLogistica(){
        int numTramos = 0;
        for(Tramo t: tramos){
            if(t.getTipoLogistica() == TipoLogistica.GRAN_LOGISTICA){
                numTramos++;
            }
        }
        return numTramos;
    }

    //Calcular el coste de la gran logística
    public float calcularCosteGranLogistica(){
        return numTramosGranLogistica()*calcularCosteTramo()*calcularCosteDistancia();
    }

    //Calcular el coste de la pequeña logística
    public float calcularCostePequenaLogistica(){
        return calcularCosteDistancia();
    }

    //Calcular el coste total de la logística
    public float calcularCosteTotalLogistica(){
        return calcularCostePequenaLogistica()+calcularCosteGranLogistica();
    }




}
