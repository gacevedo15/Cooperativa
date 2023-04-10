import java.io.Serializable;
import java.util.ArrayList;

/**
 * La clase Logistica se encarga de calcular el costo total de la logística para un producto y un cliente dados,
 * considerando la cantidad comprada. El costo se divide en tramos de gran logística y pequeña logística.
 * @author Gustavo Acevedo Alfonso
 * @version 1.0
 */
public class Logistica implements Serializable {

    /**
     * ArrayList que contiene los tramos de la ruta.
     */
    private ArrayList<Tramo> tramos;

    /**
     * Cantidad total de tramos en la ruta.
     */
    private int cantTramos;

    /**
     * Coste fijo de la ruta.
     */
    private float costeFijo;

    /**
     * Coste por kilómetro para la logística de gran tamaño.
     */
    private float costePorKmGranLogistica;

    /**
     * Coste por kilómetro para la logística de pequeño tamaño.
     */
    private float costePorKmPequenaLogistica;

    /**
     * Coste total de la logística de pequeño tamaño.
     */
    private float costeTotalPequenaLogistica;

    /**
     * Coste total de la logística de gran tamaño.
     */
    private float costeTotalGranLogistica;

    /**
     * Coste total de toda la logística.
     */
    private float costeTotalLogistica;


    /**
     * Constructor que crea una instancia de Logistica con la información de un cliente, un producto y una oferta logística.
     * @param c Cliente que realizará el pedido.
     * @param p Producto que se va a transportar.
     * @param ofertaLogistica Oferta logística que se aplicará al transporte del producto.
     */
    public Logistica(Cliente c, Producto p, OfertaLogistica ofertaLogistica) {
        this.tramos = crearTramos(c.getDistancia(), p.esPerecedero());
        this.costeFijo = ofertaLogistica.getCosteFijo();
        this.costePorKmGranLogistica = ofertaLogistica.getCostePorKmGranLogistica();
        this.costePorKmPequenaLogistica = ofertaLogistica.getCostePorKmPequenaLogistica();
        this.costeFijo = ofertaLogistica.getCosteFijo();
    }

    /**
     * Devuelve el coste total de la pequeña logística.
     * @return Coste total de la pequeña logística.
     */
    public float getCosteTotalPequenaLogistica() {
        return this.costeTotalPequenaLogistica;
    }

    /**
     * Devuelve el coste total de la gran logística.
     * @return Coste total de la gran logística.
     */
    public float getCosteTotalGranLogistica() {
        return this.costeTotalGranLogistica;
    }

    /**
     * Crea una lista de tramos en función de la distancia y si el producto es perecedero o no.
     * Si el producto es perecedero y la distancia es menor o igual a 100 km, se crea un tramo de pequeña logística
     * con la distancia dada.
     * Si el producto es perecedero y la distancia es mayor a 100 km, se crea un tramo de gran logística con la
     * distancia total menos 100 km y un tramo de pequeña logística con 100 km.
     * Si el producto es no perecedero, se crean tramos de gran logística de 50 km hasta el penúltimo tramo y
     * un tramo de pequeña logística con la distancia restante.
     * @param distancia La distancia total a recorrer en km.
     * @param esPerecedero Indica si el producto es perecedero o no.
     * @return Una lista de tramos creada en función de los parámetros dados.
     */
    public ArrayList<Tramo> crearTramos(float distancia, boolean esPerecedero) {

        /**
         * ArrayList que contiene los tramos de la ruta.
         */
        ArrayList<Tramo> tramos = new ArrayList<Tramo>();

        /**
         * Inicializa la cantidad de tramos a 0.
         */
        cantTramos = 0;

        if (esPerecedero) {
            if (distancia <= 100.0f) {
                tramos.add(new Tramo(distancia,TipoLogistica.PEQUENA_LOGISTICA));
            } else {
                tramos.add(new Tramo(distancia-100,TipoLogistica.GRAN_LOGISTICA));
                tramos.add(new Tramo(100,TipoLogistica.PEQUENA_LOGISTICA));
                cantTramos = 1;
            }
        } else {
            cantTramos = (int) Math.ceil(distancia / 50);
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

    /**
     * Muestra los tramos de logística.
     * Imprime la distancia y el tipo de logística de cada tramo.
     */
    public void mostrarTramos() {
        for (Tramo tramo : tramos) {
            System.out.println("Distancia: " + tramo.getDistancia() + " Tipo de logística: " + tramo.getTipoLogistica());
        }
        System.out.println("\n");
    }


    /**
     * Calcula el coste de la logística para un producto y un cliente dados, en función de la cantidad comprada.
     * @param p Producto comprado por el cliente
     * @param c Cliente que realiza la compra
     * @param cantidadComprada Cantidad de producto comprada por el cliente
     * @return El coste total de la logística para la compra realizada por el cliente
     */
    public float calcularCosteLogistica(Producto p, Cliente c, float cantidadComprada) {

        /**
         * Se inicializan a 0 todos los costes.
         */
        float costeTramosGranLogistica = 0;
        costeTotalPequenaLogistica = 0;
        float costeTotalGranLogisticaPorViaje = 0;
        costeTotalGranLogistica = 0;
        costeTotalLogistica = 0;
        float distanciaTotalTramosGranLogistica = 0;

        if (c.getTipoCliente()==TipoCliente.DISTRIBUIDOR){      //Si el cliente es un distribuidor
            int cantViajes = (int) Math.ceil(cantidadComprada / TipoCooperativa.MIN_KG_DISTRIBUIDOR);   //Se calcula la cantidad de viajes
            float cantKgUltimoViaje = cantidadComprada % TipoCooperativa.MIN_KG_DISTRIBUIDOR;   //Se asigna la cantidad de Kg del último viaje
            if (cantKgUltimoViaje>0){   //Si la cantidad de Kg del último viaje es mayor a 0
                for (int i=1;i<cantViajes;i++){   //Se recorren todos los viajes excepto el último
                    for (Tramo tramo : tramos) {   //Se recorren todos los tramos
                       if (tramo.getTipoLogistica()==TipoLogistica.GRAN_LOGISTICA){   //Se calcula el coste de los tramos de gran logística
                           costeTramosGranLogistica += costeFijo * p.getValorReferenciaPorKg() * TipoCooperativa.MIN_KG_DISTRIBUIDOR;
                           distanciaTotalTramosGranLogistica += tramo.getDistancia();
                       }else{   //Se calcula el coste de los tramos de pequeña logística
                            costeTotalPequenaLogistica += TipoCooperativa.MIN_KG_DISTRIBUIDOR*tramo.getDistancia()*costePorKmPequenaLogistica;
                       }
                    }
                    costeTotalGranLogisticaPorViaje += costeTramosGranLogistica+distanciaTotalTramosGranLogistica*costePorKmGranLogistica;
                    costeTramosGranLogistica = 0;
                    distanciaTotalTramosGranLogistica = 0;
                }
                distanciaTotalTramosGranLogistica = 0;
                for (Tramo tramo : tramos) {   //Se recorren todos los tramos del último viaje
                    if (tramo.getTipoLogistica()==TipoLogistica.GRAN_LOGISTICA){   //Se calcula el coste de los tramos de gran logística
                        costeTramosGranLogistica += costeFijo * p.getValorReferenciaPorKg() * cantKgUltimoViaje;
                        distanciaTotalTramosGranLogistica += tramo.getDistancia();
                    }else{   //Se calcula el coste de los tramos de pequeña logística
                        costeTotalPequenaLogistica += cantKgUltimoViaje*tramo.getDistancia()*costePorKmPequenaLogistica;
                    }
                }
                costeTotalGranLogisticaPorViaje += costeTramosGranLogistica+distanciaTotalTramosGranLogistica*costePorKmGranLogistica;
                costeTotalLogistica = costeTotalGranLogisticaPorViaje + costeTotalPequenaLogistica;
                return costeTotalLogistica;
            }else{   //Si la cantidad de Kg del último viaje es 0
                for (Tramo tramo : tramos) {    //Se recorren todos los tramos
                    if (tramo.getTipoLogistica()==TipoLogistica.GRAN_LOGISTICA){   //Se calcula el coste de los tramos de gran logística
                        costeTramosGranLogistica += costeFijo * p.getValorReferenciaPorKg() * TipoCooperativa.MIN_KG_DISTRIBUIDOR;
                        distanciaTotalTramosGranLogistica += tramo.getDistancia();
                    }else{   //Se calcula el coste de los tramos de pequeña logística
                        costeTotalPequenaLogistica += cantidadComprada*tramo.getDistancia()*costePorKmPequenaLogistica;
                    }
                }
            }
            costeTotalGranLogisticaPorViaje = costeTramosGranLogistica + distanciaTotalTramosGranLogistica*costePorKmGranLogistica;
            costeTotalGranLogistica = costeTotalGranLogisticaPorViaje*cantViajes;
            costeTotalLogistica = costeTotalGranLogistica + costeTotalPequenaLogistica;
            return costeTotalLogistica;
        }else{   //Si el cliente es un consumidor final
            for (Tramo tramo : tramos) {   //Se recorren todos los tramos
                if (tramo.getTipoLogistica()==TipoLogistica.GRAN_LOGISTICA){   //Se calcula el coste de los tramos de gran logística
                    costeTramosGranLogistica += costeFijo * p.getValorReferenciaPorKg() * cantidadComprada;
                    distanciaTotalTramosGranLogistica += tramo.getDistancia();
                }else{   //Se calcula el coste de los tramos de pequeña logística
                    costeTotalPequenaLogistica += cantidadComprada*tramo.getDistancia()*costePorKmPequenaLogistica;
                }
            }
            costeTotalGranLogistica = costeTramosGranLogistica + distanciaTotalTramosGranLogistica * costePorKmGranLogistica;
            costeTotalLogistica = costeTotalGranLogistica + costeTotalPequenaLogistica;
            return costeTotalLogistica;
        }
    }
}
