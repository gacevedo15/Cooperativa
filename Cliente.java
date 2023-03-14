public class Cliente{
    private static int contadorClientes = 0;
    private int idCliente;
    private String nombre;
    private TipoCliente tipoCliente;
    private float distancia;

    //Constructor
    public Cliente(String nombre, TipoCliente tipoCliente, float distancia) {
        this.idCliente = ++contadorClientes;
        this.nombre = nombre;
        this.tipoCliente = tipoCliente;
        this.distancia = distancia;
    }

    /***------------------------------------------------------------***/

    //Getters
    public int getIdCliente() {
        return idCliente;
    }
    public String getNombre() {
        return nombre;
    }
    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }
    public float getDistancia() {
        return distancia;
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

    /***------------------------------------------------------------***/





}
