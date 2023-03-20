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
    private float cantCompradaKg;

    private float costeProducto;
    private float beneficioCooperativa;
    private float beneficioProductores;
    private float costeLogistica;
    private float costeTotal;

    //Constructor
    public Pedido(Cliente c, Producto p, float cantCompradaKg, OfertaLogistica o) {
        this.idPedido = ++idPedidoActual;
        this.fechaPedido = LocalDate.now();
        this.c = c;
        this.p = p;
        this.l = new Logistica(c, p, cantCompradaKg,o);
        this.cantCompradaKg = cantCompradaKg;
        calcularCostes(cantCompradaKg);
    }

    //Getters
    public Producto getProducto() {
        return this.p;
    }
    public float getCantCompradaKg() {
        return this.cantCompradaKg;
    }
    public float getBeneficioProductores() {
        return this.beneficioProductores;
    }
    public float getBeneficioCooperativa() {
        return this.beneficioCooperativa;
    }

    public float getCosteProducto() {
        return this.costeProducto;
    }
    public float getCosteLogistica() {
        return this.costeLogistica;
    }
    public float getCosteTotal() {
        return this.costeTotal;
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
                + "\nProducto: " + p.getTipo() + " - " + (p.esPerecedero() ? "Perecedero" : "No Perecedero")
                + "\nCoste Producto: " + TipoCooperativa.df.format(costeProducto)  + "€"
                + "\nCoste Logística: " + TipoCooperativa.df.format(costeLogistica)  + "€"
                + "\nBeneficio Productores: " + TipoCooperativa.df.format(beneficioProductores)  + "€"
                + "\nBeneficio Cooperativa: " + TipoCooperativa.df.format(beneficioCooperativa)  + "€"
                + "\nCoste Total: " + TipoCooperativa.df.format(costeTotal)  + "€\n";
    }

    //Método para obtener el valor del producto en dependencia de la fecha de entrega
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

    //Método para calcular los costes del pedido
    public void calcularCostes(float cantCompradaKg) {
        if (c.getTipoCliente() == TipoCliente.DISTRIBUIDOR) {
            this.beneficioProductores= cantCompradaKg * obtenerValorProductoPorKg(); //30
            this.costeProducto = beneficioProductores * TipoCooperativa.MARGEN_DISTRIBUIDOR; // 34,5
            this.beneficioCooperativa = costeProducto - beneficioProductores; // = 4,5
            this.costeLogistica = l.calcularCosteLogistica(p,c,cantCompradaKg);
            this.costeTotal = this.costeProducto + this.costeLogistica;
        } else {
            this.beneficioProductores= cantCompradaKg * obtenerValorProductoPorKg(); //30
            this.costeProducto = beneficioProductores * TipoCooperativa.MARGEN_CONSUMIDOR_FINAL;
            this.beneficioCooperativa = costeProducto - beneficioProductores; // = 4,5
            this.costeProducto = TipoCooperativa.aplicarIVA(costeProducto);
            this.costeLogistica = TipoCooperativa.aplicarIVA(l.calcularCosteLogistica(p,c,cantCompradaKg));
            this.costeTotal = this.costeProducto + this.costeLogistica;
        }
    }



}
