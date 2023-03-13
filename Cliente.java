
/**
 * Write a description of class Cliente here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Cliente{
    private static int contadorClientes = 0;

    private int idCliente;
    private TipoCliente tipoCliente;
    private float distancia;

    //Constructor
    public Cliente(TipoCliente tipoCliente, float distancia) {
        this.idCliente = ++contadorClientes;
        this.tipoCliente = tipoCliente;
        this.distancia = distancia;
    }

    //Getters
    public int getIdCliente() {
        return idCliente;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public float getDistancia() {
        return distancia;
    }

    //Setters
    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }




}
