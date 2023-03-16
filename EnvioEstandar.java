
/**
 * Write a description of class EnvioEstandar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EnvioEstandar extends OfertaLogistica{

    //Constructor
    public EnvioEstandar(String nombre, float costePorKmGranLogistica, float costePorKmPequenaLogistica,TipoCliente tipoCliente) {
        super(nombre, costePorKmGranLogistica, costePorKmPequenaLogistica);
        if (tipoCliente == TipoCliente.DISTRIBUIDOR) {
            setCosteFijo(0.5f);
        }else {
            setCosteFijo(1.5f);
        }
    }

    //Implementación del método abstracto para añadir un descuento a la oferta en porcentaje
    @Override
    public float aplicarDescuento(float precioLogistica, float porcentaje,Cliente c) {
        return precioLogistica - (precioLogistica * porcentaje);
    }
}
