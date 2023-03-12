
/**
 * Write a description of class ProductoAsociado here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ProductoAsociado
{
    private Producto producto;
    private float numHa;

    public ProductoAsociado(Producto producto, float numHa) {
        this.producto = producto;
        this.numHa = numHa;
    }

    //Getters
    public Producto getProducto() {
        return producto;
    }
    public float getNumHa() {
        return numHa;
    }

    //Setters
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    public void setNumHa(float numHa) {
        this.numHa = numHa;
    }
}
