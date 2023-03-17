import java.time.LocalDate;

public class Pedido{

    private static int idPedidoActual=0;
    private int idPedido;
    private LocalDate fechaPedido;
    private LocalDate fechaEntrega;

    private Cliente c;
    private Producto p;
    private Repartidor r;
    private Logistica l;
    private OfertaLogistica o;

    private float costeProducto;
    private float costeProductoPorKg;
    private float cantCompradaKg;
    private float costeLogistica;
    private float costeTotal;

    //Constructor
    public Pedido(Cliente c, Producto p, Repartidor r, float cantCompradaKg, OfertaLogistica o) {
        this.idPedido = ++idPedidoActual;
        this.fechaPedido = LocalDate.now();
        this.c = c;
        this.p = p;
        this.r = r;
        this.l = new Logistica(c, p, cantCompradaKg,o);
        this.cantCompradaKg = cantCompradaKg;
        if (c.getTipoCliente() == TipoCliente.DISTRIBUIDOR) {
            this.costeProducto = p.getValorReferenciaPorKg()*o.getCosteFijo();
            this.costeLogistica = this.costeTotal = l.calcularCosteLogistica(p,c,cantCompradaKg);
            this.costeProductoPorKg = this.costeTotal / cantCompradaKg;
        } else {
            this.costeProductoPorKg = p.getValorReferenciaPorKg()*o.getCosteFijo();
            this.costeProducto = Cooperativa.aplicarIVA(this.costeProductoPorKg * cantCompradaKg);
            this.costeLogistica = Cooperativa.aplicarIVA(l.calcularCosteLogistica(p,c,cantCompradaKg));
            this.costeTotal = this.costeProducto + this.costeLogistica;
        }
    }

    //Getters
    public float getCosteLogistica() {
        return costeLogistica;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    //Mostrar tramos de la logistica
    public void mostrarTramos() {
        l.mostrarTramos();
    }

    //ToString
    public String toString() {
        return "ID Pedido: " + idPedido
                + "\nCliente: " + c.getNombre() + " - " + c.getTipoCliente()
                + "\nProducto: " + p.getTipo() + " - " + p.esPerecedero()
                + "\nRepartidor: " + r.getNombre()
                + "\nCoste Producto: " + Cooperativa.df.format(costeProducto)  + "€"
                + "\nCoste Logística: " + Cooperativa.df.format(costeLogistica)  + "€"
                + "\nCoste Total: " + Cooperativa.df.format(costeTotal)  + "€\n";
    }

    //Método para obtener el valor del producto
    public float obtenerValorProductoPorKg() {
        float precio;
        LocalDate fechaPrecio = this.fechaEntrega != null && this.fechaEntrega.isAfter(this.fechaPedido.plusDays(10)) ? this.fechaEntrega.minusDays(10) : this.fechaPedido;
        if (this.p.getFechaUltimaActualizacion().isBefore(fechaPrecio)) {
            precio = this.p.getValorReferenciaPorKgAnterior();
        } else {
            precio = this.p.getValorReferenciaPorKg();
        }
        return precio;
    }



}
