/**
 * La clase EnvioEstandar representa una oferta de logística estándar para el envío de productos.
 * Esta clase hereda de la clase OfertaLogistica e implementa los métodos para calcular el coste
 * de envío y aplicar descuentos.
 * @author Gustavo Acevedo Alfonso
 * @version 1.0
 */
public class EnvioEstandar extends OfertaLogistica{

    /**
     * Constructor de la clase EnvioEstandar que inicializa los atributos heredados de la superclase OfertaLogistica
     * y asigna el coste fijo según el tipo de cliente.
     * @param nombre Nombre del envío.
     * @param costePorKmGranLogistica Coste por kilómetro para la gran logística.
     * @param costePorKmPequenaLogistica Coste por kilómetro para la pequeña logística.
     * @param tipoCliente Tipo de cliente al que pertenece el envío (Distribuidor o Consumidor Final).
     */
    public EnvioEstandar(String nombre, float costePorKmGranLogistica, float costePorKmPequenaLogistica,TipoCliente tipoCliente) {
        super(nombre, costePorKmGranLogistica, costePorKmPequenaLogistica);
        this.tipoCliente = tipoCliente;
        if (tipoCliente == TipoCliente.DISTRIBUIDOR) {
            setCosteFijo(1.05f);    //Si es distribuidor, el coste fijo es 5% más caro (valorReferenciaPorKg del producto * 1.05)
        }else {
            setCosteFijo(1.15f);    //Si es consumidor final, el coste fijo es 15% más caro (valorReferenciaPorKg del producto * 1.15)
        }
    }

    /**
     * Aplica un descuento al precio de la logística.
     * @param precioLogistica Precio de la logística.
     * @param porcentaje Porcentaje de descuento.
     * @param c Cliente al que se le aplica el descuento.
     * @return Precio de la logística con el descuento aplicado.
     */
    @Override
    public float aplicarDescuento(float precioLogistica, float porcentaje,Cliente c) {
        porcentaje = porcentaje/100.0f;
        return precioLogistica - (precioLogistica * porcentaje);
    }

    /**
     * ToString de la clase EnvioEstandar.
     */
    @Override
    public String toString() {
        return "Nombre: " + getNombre() +
                "\nTipo de logística: Envío Estándar" +
                "\nCoste por kilómetro para la gran logística: " + getCostePorKmGranLogistica() +
                "\nCoste por kilómetro para la pequeña logística: " + getCostePorKmPequenaLogistica() +
                "\nCoste fijo: " + getCosteFijo() +
                "\nTipo de cliente: " + getTipoCliente();
    }
}
