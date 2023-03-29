import java.util.ArrayList;

/**
 * Write a description of class Logistica here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Logistica{

    //Constante
    private ArrayList<Tramo> tramos;
    private int cantTramos;
    private float costeFijo;
    private float costePorKmGranLogistica;
    private float costePorKmPequenaLogistica;
    private float costeTotalPequenaLogistica;
    private float costeTotalGranLogistica;
    private float costeTotalLogistica;

    //Constructor
    public Logistica(Cliente c, Producto p, float cantCompradaKg, OfertaLogistica ofertaLogistica) {
        this.tramos = crearTramos(c.getDistancia(), p.esPerecedero());
        this.costeFijo = ofertaLogistica.getCosteFijo();
        this.costePorKmGranLogistica = ofertaLogistica.getCostePorKmGranLogistica();
        this.costePorKmPequenaLogistica = ofertaLogistica.getCostePorKmPequenaLogistica();
        this.costeFijo = ofertaLogistica.getCosteFijo();

    }

    //Getters
    public float getCosteTotalPequenaLogistica() {
        return this.costeTotalPequenaLogistica;
    }
    public float getCosteTotalGranLogistica() {
        return this.costeTotalGranLogistica;
    }

    //Método para crear los tramos
    public ArrayList<Tramo> crearTramos(float distancia, boolean esPerecedero) {
        ArrayList<Tramo> tramos = new ArrayList<Tramo>();
        cantTramos =0;

        if (esPerecedero) {
            if (distancia <= 100.0f) {
                tramos.add(new Tramo(distancia,TipoLogistica.PEQUENA_LOGISTICA));
            } else {
                tramos.add(new Tramo(distancia-100,TipoLogistica.GRAN_LOGISTICA));
                tramos.add(new Tramo(100,TipoLogistica.PEQUENA_LOGISTICA));
                cantTramos =1;
            }
        } else {
            cantTramos = (int) Math.ceil(distancia / 50);
            int cantTramosGranLogistica = cantTramos-1;
            float distanciaTramo;
            for (int i = 1; i <= cantTramos; i++) {
                distanciaTramo = (i == cantTramos) ? distancia % 50 : 50.0f;
                if (i == cantTramos){
                    if (distanciaTramo == 0.0f){
                        distanciaTramo = 50.0f;
                    }
                    tramos.add(new Tramo(distanciaTramo, TipoLogistica.PEQUENA_LOGISTICA));
                }else{
                    tramos.add(new Tramo(distanciaTramo, TipoLogistica.GRAN_LOGISTICA));
                }
            }
        }
        return tramos;
    }

    //Mostrar tramos (distancia y tipo de logística)
    public void mostrarTramos() {
        for (Tramo tramo : tramos) {
            System.out.println("Distancia: " + tramo.getDistancia() + " Tipo de logística: " + tramo.getTipoLogistica());
        }
        System.out.println("\n");
    }


    /**
     Calcula el costo total de la logística para un producto y un cliente dados, considerando la cantidad comprada. El costo se divide en tramos de gran logística y pequeña logística.
     @param p Producto que se desea comprar.
     @param c Cliente que realiza la compra.
     @param cantidadComprada Cantidad de producto que se desea comprar.
     @return El costo total de la logística para la cantidad de producto especificada.
     */
    public float calcularCosteLogistica(Producto p, Cliente c, float cantidadComprada) {

        float costeTramosGranLogistica = 0;
        costeTotalPequenaLogistica = 0;
        float costeTotalGranLogisticaPorViaje = 0;
        costeTotalGranLogistica = 0;
        costeTotalLogistica = 0;
        float distanciaTotalTramosGranLogistica = 0;

        if (c.getTipoCliente()==TipoCliente.DISTRIBUIDOR){
            int cantViajes = (int) Math.ceil(cantidadComprada / TipoCooperativa.MIN_KG_DISTRIBUIDOR);
            float cantKgUltimoViaje = cantidadComprada % TipoCooperativa.MIN_KG_DISTRIBUIDOR;
            if (cantKgUltimoViaje>0){
                for (int i=1;i<cantViajes;i++){
                    for (Tramo tramo : tramos) {
                       if (tramo.getTipoLogistica()==TipoLogistica.GRAN_LOGISTICA){
                           costeTramosGranLogistica += costeFijo * p.getValorReferenciaPorKg() * TipoCooperativa.MIN_KG_DISTRIBUIDOR;
                           distanciaTotalTramosGranLogistica += tramo.getDistancia();
                       }else{
                            costeTotalPequenaLogistica += TipoCooperativa.MIN_KG_DISTRIBUIDOR*tramo.getDistancia()*costePorKmPequenaLogistica;
                       }
                    }
                    costeTotalGranLogisticaPorViaje += costeTramosGranLogistica+distanciaTotalTramosGranLogistica*costePorKmGranLogistica;
                    costeTramosGranLogistica = 0;
                    distanciaTotalTramosGranLogistica = 0;
                }
                distanciaTotalTramosGranLogistica = 0;
                for (Tramo tramo : tramos) {
                    if (tramo.getTipoLogistica()==TipoLogistica.GRAN_LOGISTICA){
                        costeTramosGranLogistica += costeFijo * p.getValorReferenciaPorKg() * cantKgUltimoViaje;
                        distanciaTotalTramosGranLogistica += tramo.getDistancia();
                    }else{
                        costeTotalPequenaLogistica += cantKgUltimoViaje*tramo.getDistancia()*costePorKmPequenaLogistica;
                    }
                }
                costeTotalGranLogisticaPorViaje += costeTramosGranLogistica+distanciaTotalTramosGranLogistica*costePorKmGranLogistica;
                costeTotalLogistica = costeTotalGranLogisticaPorViaje + costeTotalPequenaLogistica;
                return costeTotalLogistica;
            }else{
                for (Tramo tramo : tramos) {
                    if (tramo.getTipoLogistica()==TipoLogistica.GRAN_LOGISTICA){
                        costeTramosGranLogistica += costeFijo * p.getValorReferenciaPorKg() * TipoCooperativa.MIN_KG_DISTRIBUIDOR;
                        distanciaTotalTramosGranLogistica += tramo.getDistancia();
                    }else{
                        costeTotalPequenaLogistica += cantidadComprada*tramo.getDistancia()*costePorKmPequenaLogistica;
                    }
                }
            }
            costeTotalGranLogisticaPorViaje = costeTramosGranLogistica + distanciaTotalTramosGranLogistica*costePorKmGranLogistica;
            costeTotalGranLogistica = costeTotalGranLogisticaPorViaje*cantViajes;
            costeTotalLogistica = costeTotalGranLogistica + costeTotalPequenaLogistica;
            return costeTotalLogistica;
        }else{
            for (Tramo tramo : tramos) {
                if (tramo.getTipoLogistica()==TipoLogistica.GRAN_LOGISTICA){
                    costeTramosGranLogistica += costeFijo * p.getValorReferenciaPorKg() * cantidadComprada;
                    distanciaTotalTramosGranLogistica += tramo.getDistancia();
                }else{
                    costeTotalPequenaLogistica += cantidadComprada*tramo.getDistancia()*costePorKmPequenaLogistica;
                }
            }
            costeTotalGranLogistica = costeTramosGranLogistica + distanciaTotalTramosGranLogistica * costePorKmGranLogistica;
            costeTotalLogistica = costeTotalGranLogistica + costeTotalPequenaLogistica;
            return costeTotalLogistica;
        }

    }


}
