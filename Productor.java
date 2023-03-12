import java.util.ArrayList;

public class Productor {

    private static int idProductorActual = 0;
    private int idProductor;
    private String nombre;
    private TipoProductor tipoProductor;
    private ArrayList<ProductoAsociado> listaProductos;

    //Constructores
    //Constructor sin lista de productos
    public Productor(String nombre, TipoProductor tipoProductor) {
        this.idProductor = ++idProductorActual;
        this.nombre = nombre;
        this.tipoProductor = tipoProductor;
        this.listaProductos = new ArrayList<ProductoAsociado>();
    }

    //Constructor con lista de productos
    public Productor(String nombre, TipoProductor tipoProductor, ArrayList<ProductoAsociado> listaProductos) {
        this.idProductor = ++idProductorActual;
        this.nombre = nombre;
        this.tipoProductor = tipoProductor;
        this.listaProductos = listaProductos;
    }


    //Getters
    public int getIdProductor() {
        return idProductor;
    }
    public String getNombre() {
        return nombre;
    }
    public TipoProductor getTipoProductor() {
        return tipoProductor;
    }
    public ArrayList<ProductoAsociado> getListaProductos() {
        return listaProductos;
    }

    //Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setTipoProductor(TipoProductor tipoProductor) {
        this.tipoProductor = tipoProductor;
    }
    public void setListaProductos(ArrayList<ProductoAsociado> listaProductos) {
        this.listaProductos = listaProductos;
    }

    //ToString
    public String toString() {
        return "Productor{" + "idProductor=" + idProductor + ", nombre=" + nombre + ", tipoProductor=" + tipoProductor + ", listaProductos=" + listaProductos + '}';
    }

    //ToString que muestre el tipo de producto de la lista de productos y la extension de tierra que tiene
    public String toString2() {
        String s = "Productor{" + "idProductor=" + idProductor + ", nombre=" + nombre + ", tipoProductor=" + tipoProductor + ", listaProductos=";
        for (ProductoAsociado p : listaProductos) {
            s += p.getProducto().getTipo() + " " + p.getNumHa() + " ha, ";
        }
        return s + "}";
    }

    //Añadir producto
    public void addProducto(ProductoAsociado p) {
        listaProductos.add(p);
    }
    //Eliminar producto
    public void removeProducto(ProductoAsociado p) {
        listaProductos.remove(p);
    }

}