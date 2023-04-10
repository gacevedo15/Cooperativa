import java.io.Serializable;
import java.time.LocalDate;

/**
 * La clase Pedido representa un pedido realizado por un cliente a la cooperativa.
 * Contiene información acerca del cliente, producto, repartidor, logística, oferta logística,
 * costes del pedido y fechas de pedido y entrega.
 * @author Gustavo Acevedo Alfonso
 * @version 1.0
 */
public class Pedido implements Serializable {

    /**
     * El id del pedido actual. Se utiliza para autoincrementar el id del pedido.
     */
    private static int idPedidoActual = 0;

    /**
     * El id del pedido.
     */
    private int idPedido;

    /**
     * La fecha en que se realizó el pedido.
     */
    private LocalDate fechaPedido;

    /**
     * La fecha en que se entregó el pedido.
     */
    private LocalDate fechaEntrega;

    /**
     * El cliente que realizó el pedido.
     */
    private Cliente c;

    /**
     * El producto que se pidió.
     */
    private Producto p;

    /**
     * El repartidor encargado de entregar el pedido.
     */
    private Repartidor r;

    /**
     * La logística utilizada para el pedido.
     */
    private Logistica l;

    /**
     * La oferta logística aplicada al pedido.
     */
    private OfertaLogistica o;

    /**
     * La cantidad comprada del producto en kilogramos.
     */
    private float cantCompradaKg;

    /**
     * El costo del producto.
     */
    private float costeProducto;

    /**
     * El beneficio obtenido por la cooperativa.
     */
    private float beneficioCooperativa;

    /**
     * El beneficio obtenido por los productores.
     */
    private float beneficioProductores;

    /**
     * El coste de la pequeña logística.
     */
    private float costePequenaLogistica;

    /**
     * El coste de la gran logística.
     */
    private float costeGranLogistica;

    /**
     * El coste total de la logística.
     */
    private float costeLogistica;

    /**
     * El coste total del pedido.
     */
    private float costeTotal;


    /**
     * Constructor de la clase Pedido que crea un nuevo pedido con los siguientes datos:
     * @param c Cliente que realiza el pedido.
     * @param p Producto que se solicita.
     * @param cantCompradaKg Cantidad del producto que se solicita en kilogramos.
     * @param o Oferta de logística seleccionada para el envío del producto.
     * @param fechaPedido Fecha en la que se realiza el pedido.
     * @param fechaEntrega Fecha estimada para la entrega del producto.
     */
    public Pedido(Cliente c, Producto p, float cantCompradaKg, OfertaLogistica o, LocalDate fechaPedido,LocalDate fechaEntrega) {
        this.idPedido = ++idPedidoActual; //Se autoincrementa el id del pedido
        this.fechaPedido = fechaPedido; //Se establece la fecha en la que se realiza el pedido
        this.fechaEntrega = fechaEntrega; //Se establece la fecha en la que se entrega el pedido
        this.c = c; //Cliente que realiza el pedido
        this.p = p; //Producto que se solicita
        this.l = new Logistica(c, p, o); //Se crea una logistica para el pedido, con la oferta de logística seleccionada
        this.cantCompradaKg = cantCompradaKg; //Se establece la cantidad del producto que se solicita en kilogramos
        calcularCostes(cantCompradaKg); //Se calculan los costes del pedido
    }

    /**
     * Devuelve la fecha en la que se realizó el pedido.
     * @return La fecha en la que se realizó el pedido.
     */
    public LocalDate getFechaPedido() {
        return this.fechaPedido;
    }

    /**
     * Devuelve la fecha en la que se entregó el pedido.
     * @return La fecha en la que se entregó el pedido.
     */
    public LocalDate getFechaEntrega() {
        return this.fechaEntrega;
    }

    /**
     * Devuelve el producto que se pidió.
     * @return El producto que se pidió.
     */
    public Producto getProducto() {
        return this.p;
    }

    /**
     * Devuelve la cantidad comprada del producto en kilogramos.
     * @return La cantidad comprada del producto en kilogramos.
     */
    public float getCantCompradaKg() {
        return this.cantCompradaKg;
    }

    /**
     * Devuelve el beneficio para los productores.
     * @return El beneficio de los productores.
     */
    public float getBeneficioProductores() {
        return this.beneficioProductores;
    }

    /**
     * Devuelve el beneficio para la cooperativa.
     * @return El beneficio de la cooperativa.
     */
    public float getBeneficioCooperativa() {
        return this.beneficioCooperativa;
    }

    /**
     * Devuelve el coste del producto.
     * @return El coste del producto.
     */
    public float getCosteProducto() {
        return this.costeProducto;
    }

    /**
     * Devuelve el coste de la pequeña logística.
     * @return El coste de la pequeña logística.
     */
    public float getCostePequenaLogistica() {
        return this.costePequenaLogistica;
    }

    /**
     * Devuelve el coste de la gran logística.
     * @return El coste de la gran logística.
     */
    public float getCosteGranLogistica() {
        return this.costeGranLogistica;
    }

    /**
     * Devuelve el coste total de la logística.
     * @return El coste total de la logística.
     */
    public float getCosteLogistica() {
        return this.costeLogistica;
    }

    /**
     * Devuelve el coste total del pedido.
     * @return El coste total del pedido.
     */
    public float getCosteTotal() {
        return this.costeTotal;
    }

    /**
     * Establece la fecha de entrega del pedido.
     * @param fechaEntrega La nueva fecha de entrega del pedido.
     */
    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    /**
     * Muestra los tramos de la logística del pedido.
     */
    public void mostrarTramos() {
        l.mostrarTramos();
    }

    /**
     * Muestra los datos del pedido
     * (id, cliente, producto, coste producto, coste logística, beneficio productores, beneficio cooperativa, coste total).
     * @return Los datos del pedido.
     */
    @Override
    public String toString() {
        return "ID Pedido: " + idPedido
                + "\nCliente: " + c.getNombre() + " - " + c.getTipoCliente()
                + "\nProducto: " + p.getTipo() + " - " + (p.esPerecedero() ? "Perecedero" : "No Perecedero")
                + "\nCoste Producto: " + TipoCooperativa.df.format(costeProducto)  + "€"
                + "\nCoste Logística: " + TipoCooperativa.df.format(costeLogistica)  + "€"
                + "\nBeneficio Productores: " + TipoCooperativa.df.format(beneficioProductores)  + "€"
                + "\nBeneficio Cooperativa: " + TipoCooperativa.df.format(beneficioCooperativa)  + "€"
                + "\nCoste Total: " + TipoCooperativa.df.format(costeTotal)  + "€\n";
    }

    /**
     * Calcula los costes del pedido en función de la cantidad comprada y el tipo de cliente.
     * Se tiene en cuenta el tipo de cliente, puesto que para los consumidores finales, debemos aplicar el IVA.
     * @param cantCompradaKg cantidad en kilogramos del producto comprado.
     */
    public void calcularCostes(float cantCompradaKg) {
        if (c.getTipoCliente() == TipoCliente.DISTRIBUIDOR) {
            this.beneficioProductores= cantCompradaKg * p.getValorReferenciaPorKg(); //30
            this.costeProducto = beneficioProductores * TipoCooperativa.MARGEN_DISTRIBUIDOR; // 34,5
            this.beneficioCooperativa = costeProducto - beneficioProductores; // = 4,5
            this.costeLogistica = l.calcularCosteLogistica(p,c,cantCompradaKg);
            this.costeTotal = this.costeProducto + this.costeLogistica;
        } else {
            this.beneficioProductores= cantCompradaKg * p.getValorReferenciaPorKg(); //30
            this.costeProducto = beneficioProductores * TipoCooperativa.MARGEN_CONSUMIDOR_FINAL;
            this.beneficioCooperativa = costeProducto - beneficioProductores; // = 4,5
            this.costeProducto = TipoCooperativa.aplicarIVA(costeProducto);
            this.costeLogistica = TipoCooperativa.aplicarIVA(l.calcularCosteLogistica(p,c,cantCompradaKg));
            this.costePequenaLogistica = l.getCosteTotalPequenaLogistica();
            this.costeGranLogistica = l.getCosteTotalGranLogistica();
            this.costeTotal = this.costeProducto + this.costeLogistica;
        }
    }

}
