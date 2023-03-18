/**
 * Envío Premium le aplicará un 10% extra al coste de la Gran Logística y un 5% extra al coste de la Pequeña Logística.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EnvioPremium extends OfertaLogistica{

    private static final float porcentajeExtraGranLogistica = 1.1f;
    private static final float porcentajeExtraPequenaLogistica = 1.05f;

    //Constructor
    public EnvioPremium(String nombre, float costePorKmGranLogistica, float costePorKmPequenaLogistica, TipoCliente tipoCliente) {
        super(nombre, costePorKmGranLogistica, costePorKmPequenaLogistica);
        if (tipoCliente == TipoCliente.DISTRIBUIDOR) {
           setCosteFijo(1.10f); //Si es distribuidor, el coste fijo es 10% más caro (valorReferenciaPorKg del producto * 1.10)
        }else {
            setCosteFijo(1.20f); //Si es cliente, el coste fijo es 20% más caro (valorReferenciaPorKg del producto * 1.20)
        }
    }

    //Implementación del método abstracto para añadir un descuento a la oferta en porcentaje
    @Override
    public float aplicarDescuento(float precioLogistica, float porcentaje,Cliente c) {
        porcentaje = porcentaje/100.0f;
        if (c.getEsClientePremium()){
            float descuentoPremium = 0.1f;
            return precioLogistica - (precioLogistica * (porcentaje + descuentoPremium));
        }else {
            return precioLogistica - (precioLogistica * porcentaje);
        }
    }
    
}
