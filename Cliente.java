import java.util.ArrayList;

/**
 * La clase Cliente representa a un cliente de la empresa de logística.
 * Cada cliente tiene un identificador único, un nombre, un tipo de cliente, una distancia a la que se encuentra y
 * un historial de pedidos.
 * @author Gustavo Acevedo Alfonso
 * @version 1.0
 */
public class Cliente{
    private static int contadorClientes = 0;
    private int idCliente;
    private String nombre;
    private TipoCliente tipoCliente;
    private float distancia;
    private boolean esClientePremium;

    private ArrayList<Pedido> pedidos;

    /**
     * Contructor de la clase Cliente.
     * @param nombre el nombre del cliente.
     * @param tipoCliente el tipo de cliente.
     * @param distancia la distancia a la que se encuentra el cliente.
     */
    public Cliente(String nombre, TipoCliente tipoCliente, float distancia) {
        this.idCliente = ++contadorClientes;
        this.nombre = nombre;
        this.tipoCliente = tipoCliente;
        this.distancia = distancia;
        this.esClientePremium = false;
        this.pedidos = new ArrayList<>();
    }

    /**
     * Devuelve el identificador del cliente.
     * @return el identificador del cliente.
     */
    public int getIdCliente() {
        return this.idCliente;
    }

    /**
     * Devuelve el nombre del cliente.
     * @return el nombre del cliente.
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Devuelve el tipo de cliente.
     * @return el tipo de cliente.
     */
    public TipoCliente getTipoCliente() {
        return this.tipoCliente;
    }

    /**
     * Devuelve la distancia a la que se encuentra el cliente.
     * @return la distancia a la que se encuentra el cliente.
     */
    public float getDistancia() {
        return this.distancia;
    }

    /**
     * Devuelve si el cliente es premium o no.
     * @return si el cliente es premium o no.
     */
    public boolean getEsClientePremium() {
        return this.esClientePremium;
    }

    /**
     * Devuelve los pedidos del cliente.
     * @return los pedidos del cliente.
     */
    public ArrayList<Pedido> getPedidos() {
        return this.pedidos;
    }

    /**
     * Actualiza el nombre del cliente.
     * @param nombre el nuevo nombre del cliente.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Actualiza el tipo de cliente.
     * @param tipoCliente el nuevo tipo de cliente.
     */
    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    /**
     * Actualiza la distancia a la que se encuentra el cliente.
     * @param distancia la nueva distancia a la que se encuentra el cliente.
     */
    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    /**
     * Actualiza si el cliente es premium o no.
     * @param esClientePremium si el cliente es premium o no.
     */
    public void setEsClientePremium(boolean esClientePremium) {
        this.esClientePremium = esClientePremium;
    }

    /**
     * Añade un pedido al historial de pedidos del cliente.
     * @param p el pedido a añadir.
     */
    public void addPedido(Pedido p) {
        this.pedidos.add(p);
    }
}
