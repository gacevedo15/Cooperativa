import java.util.ArrayList;

/**
 * Write a description of class Logistica here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Logistica{
    private ArrayList<Tramo> tramos;
    private int cantTramosGranLogistica;
    private float costeFijo;
    private float costePorKmGranLogistica;
    private float costePorKmPequenaLogistica;
    private float costeTotalLogistica;

    //Constructor
    public Logistica(Cliente c, Producto p, float cantCompradaKg, OfertaLogistica ofertaLogistica) {
        this.tramos = crearTramos(c.getDistancia(), p.esPerecedero());
        this.costeFijo = ofertaLogistica.getCosteFijo();
        this.costePorKmGranLogistica = ofertaLogistica.getCostePorKmGranLogistica();
        this.costePorKmPequenaLogistica = ofertaLogistica.getCostePorKmPequenaLogistica();
        this.costeFijo = ofertaLogistica.getCosteFijo();

    }

    //Método para crear los tramos
    public ArrayList<Tramo> crearTramos(float distancia, boolean esPerecedero) {
        ArrayList<Tramo> tramos = new ArrayList<Tramo>();
        cantTramosGranLogistica=0;

        if (esPerecedero) {
            if (distancia <= 100.0f) {
                tramos.add(new Tramo(distancia,TipoLogistica.PEQUENA_LOGISTICA));
            } else {
                tramos.add(new Tramo(distancia-100,TipoLogistica.GRAN_LOGISTICA));
                tramos.add(new Tramo(100,TipoLogistica.PEQUENA_LOGISTICA));
                cantTramosGranLogistica=1;
            }
        } else {
            cantTramosGranLogistica = (int) Math.ceil(distancia / 50);
            float distanciaTramo;
            for (int i = 1; i <= cantTramosGranLogistica; i++) {
                distanciaTramo = (i == cantTramosGranLogistica) ? distancia % 50 : 50.0f;
                if (i == cantTramosGranLogistica){
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
        float costeTramosPequenaLogistica = 0;

        float costeTotalGranLogisticaPorViaje = 0;

        float costeTotalGranLogistica = 0;

        costeTotalLogistica = 0;

        float distanciaTotalTramosGranLogistica = 0;

        if (c.getTipoCliente()==TipoCliente.DISTRIBUIDOR){
            int cantViajes = (int) Math.ceil(cantidadComprada / 1000.0f);
            float cantKgUltimoViaje = cantidadComprada % 1000.0f;
            if (cantKgUltimoViaje>0){
                for (int i=1;i<cantViajes;i++){
                    for (Tramo tramo : tramos) {
                       if (tramo.getTipoLogistica()==TipoLogistica.GRAN_LOGISTICA){
                           costeTramosGranLogistica += 0.5f * p.getValorReferenciaPorKg() * 1000.0f;
                           distanciaTotalTramosGranLogistica += tramo.getDistancia();
                       }else{
                            costeTramosPequenaLogistica += 1000.0f*tramo.getDistancia()*costePorKmPequenaLogistica;
                       }
                    }
                    costeTotalGranLogisticaPorViaje += costeTramosGranLogistica+distanciaTotalTramosGranLogistica*costePorKmGranLogistica;
                    costeTramosGranLogistica = 0;
                    distanciaTotalTramosGranLogistica = 0;
                }
                distanciaTotalTramosGranLogistica = 0;
                for (Tramo tramo : tramos) {
                    if (tramo.getTipoLogistica()==TipoLogistica.GRAN_LOGISTICA){
                        costeTramosGranLogistica += 0.5f * p.getValorReferenciaPorKg() * cantKgUltimoViaje;
                        distanciaTotalTramosGranLogistica += tramo.getDistancia();
                    }else{
                        costeTramosPequenaLogistica += cantKgUltimoViaje*tramo.getDistancia()*costePorKmPequenaLogistica;
                    }
                }
                costeTotalGranLogisticaPorViaje += costeTramosGranLogistica+distanciaTotalTramosGranLogistica*costePorKmGranLogistica;
                costeTotalLogistica = costeTotalGranLogisticaPorViaje + costeTramosPequenaLogistica;
                return costeTotalLogistica;
            }else{
                for (Tramo tramo : tramos) {
                    if (tramo.getTipoLogistica()==TipoLogistica.GRAN_LOGISTICA){
                        costeTramosGranLogistica += 0.5f * p.getValorReferenciaPorKg() * 1000.0f;
                        distanciaTotalTramosGranLogistica += tramo.getDistancia();
                    }else{
                        costeTramosPequenaLogistica += cantidadComprada*tramo.getDistancia()*costePorKmPequenaLogistica;
                    }
                }
            }
            costeTotalGranLogisticaPorViaje = costeTramosGranLogistica + distanciaTotalTramosGranLogistica*costePorKmGranLogistica;
            costeTotalGranLogistica = costeTotalGranLogisticaPorViaje*cantViajes;
            costeTotalLogistica = costeTotalGranLogistica + costeTramosPequenaLogistica;
            return costeTotalLogistica;
        }else{
            for (Tramo tramo : tramos) {
                if (tramo.getTipoLogistica()==TipoLogistica.GRAN_LOGISTICA){
                    costeTramosGranLogistica += 0.5f * p.getValorReferenciaPorKg() * cantidadComprada;
                    distanciaTotalTramosGranLogistica += tramo.getDistancia();
                }else{
                    costeTramosPequenaLogistica += cantidadComprada*tramo.getDistancia()*costePorKmPequenaLogistica;
                }
            }
            costeTotalGranLogistica = costeTramosGranLogistica + distanciaTotalTramosGranLogistica*costePorKmGranLogistica;
            costeTotalLogistica = costeTotalGranLogistica + costeTramosPequenaLogistica;
            return costeTotalLogistica;
        }

    }


}
