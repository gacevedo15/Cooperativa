
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
            setCosteFijo(1.05f);    //Si es distribuidor, el coste fijo es 5% más caro (valorReferenciaPorKg del producto * 1.05)
        }else {
            setCosteFijo(1.15f);    //Si es cliente, el coste fijo es 15% más caro (valorReferenciaPorKg del producto * 1.15)
        }
    }

    //Implementación del método abstracto para añadir un descuento a la oferta en porcentaje
    @Override
    public float aplicarDescuento(float precioLogistica, float porcentaje,Cliente c) {
        return precioLogistica - (precioLogistica * porcentaje);
    }
}
