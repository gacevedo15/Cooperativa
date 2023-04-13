/**
 * La clase EnvioPremium representa una oferta de logística premium para el envío de productos.
 * Esta clase hereda de la clase OfertaLogistica e implementa los métodos para calcular el coste
 * de envío y aplicar descuentos (Se tiene en cuenta si el cliente es premium o no).
 * @author Gustavo Acevedo Alfonso
 * @version 1.0
 */
public class EnvioPremium extends OfertaLogistica{

    private final float descuentoPremium = 0.1f;    //Descuento extra del 10% si el cliente es premium.

    /**
     * Constructor de la clase EnvioPremium que inicializa los atributos heredados de la superclase OfertaLogistica
     * y asigna el coste fijo según el tipo de cliente.
     * @param nombre
     * @param costePorKmGranLogistica
     * @param costePorKmPequenaLogistica
     * @param tipoCliente
     */
    public EnvioPremium(String nombre, float costePorKmGranLogistica, float costePorKmPequenaLogistica, TipoCliente tipoCliente) {
        super(nombre, costePorKmGranLogistica, costePorKmPequenaLogistica);
        this.tipoCliente = tipoCliente;
        if (tipoCliente == TipoCliente.DISTRIBUIDOR) {
           setCosteFijo(1.10f);     //Si es distribuidor, el coste fijo es 10% más caro (valorReferenciaPorKg del producto * 1.10)
        }else {
            setCosteFijo(1.20f);    //Si es cliente, el coste fijo es 20% más caro (valorReferenciaPorKg del producto * 1.20)
        }
    }

    /**
     * Aplica un descuento al precio de la logística, además de un descuento extra del 10% si el cliente es premium.
     * @param precioLogistica Precio de la logística.
     * @param porcentaje Porcentaje de descuento.
     * @param c Cliente al que se le aplica el descuento.
     * @return Precio de la logística con el descuento aplicado.
     */
    @Override
    public float aplicarDescuento(float precioLogistica, float porcentaje,Cliente c) {
        porcentaje = porcentaje/100.0f;
        if (c.getEsClientePremium()){
            return precioLogistica - (precioLogistica * (porcentaje + descuentoPremium));
        }else {
            return precioLogistica - (precioLogistica * porcentaje);
        }
    }

    /**
     * ToString de la clase EnvioPremium.
     */
    @Override
    public String toString() {
        return "Nombre: " + getNombre() +
                "\nTipo de logística: Envío Premium" +
                "\nCoste por kilómetro para la gran logística: " + getCostePorKmGranLogistica() +
                "\nCoste por kilómetro para la pequeña logística: " + getCostePorKmPequenaLogistica() +
                "\nCoste fijo: " + getCosteFijo() +
                "\nTipo de cliente: " + getTipoCliente() +
                "\nDescuento extra para clientes Premium: " + descuentoPremium*100 + "%";
    }
    
}
