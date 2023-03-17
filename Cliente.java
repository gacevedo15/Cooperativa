import java.util.ArrayList;

public class Cliente{
    private static int contadorClientes = 0;
    private int idCliente;
    private String nombre;
    private TipoCliente tipoCliente;
    private float distancia;
    private boolean esClientePremium;

    private ArrayList<Pedido> pedidos;

    //Constructor
    public Cliente(String nombre, TipoCliente tipoCliente, float distancia) {
        this.idCliente = ++contadorClientes;
        this.nombre = nombre;
        this.tipoCliente = tipoCliente;
        this.distancia = distancia;
        this.esClientePremium = false;
        this.pedidos = new ArrayList<>();
    }

    /***------------------------------------------------------------***/

    //Getters
    public int getIdCliente() {
        return this.idCliente;
    }
    public String getNombre() {
        return this.nombre;
    }
    public TipoCliente getTipoCliente() {
        return this.tipoCliente;
    }
    public float getDistancia() {
        return this.distancia;
    }
    public boolean getEsClientePremium() {
        return this.esClientePremium;
    }
    public ArrayList<Pedido> getPedidos() {
        return this.pedidos;
    }


    //Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }
    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }
    public void setEsClientePremium(boolean esClientePremium) {
        this.esClientePremium = esClientePremium;
    }

    /***------------------------------------------------------------***/

    //Añadir pedido
    public void addPedido(Pedido p) {
        this.pedidos.add(p);
    }


    /***------------------------------------------------------------***/





}
