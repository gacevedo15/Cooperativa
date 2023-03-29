import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Clase que representa un resumen anual
 * Debe implementar:
 * - Ventas totales en un periodo determinado de cada uno de los productos de la cooperativa. Listo
 * - Importes obtenidos por cada uno de los productores (desglosados por productos)
 * - Importes obtenidos por cada una de las empresas de logística.
 * - Beneficios de la cooperativa por cada uno de los productos.
 * - Evolución de los precios de referencias de cada productos.
 */
public class ResumenAnual{
    private int anno;
    public ArrayList<Pedido> pedidos;
    private ArrayList<Productor> productores;
    public ArrayList<Producto> productos;

    public HashMap<TipoProducto,Float> ventasTotalesPorProducto;
    public HashMap<TipoProducto,Float> beneficioCooperativaPorProducto;


    public ResumenAnual(int anno, TipoCooperativa cooperativa){
        this.anno = anno;
        this.pedidos = new ArrayList<>();
        this.productores = cooperativa.productores;
        resetBeneficioTotalProductor();
        this.productos = cooperativa.productos;
        limpiarHistoricoPrecios();
        this.ventasTotalesPorProducto = new HashMap<>();
        this.beneficioCooperativaPorProducto = new HashMap<>();
    }

    //Getters
    public int getAnno() {
        return anno;
    }
    public ArrayList<Productor> getProductores() {
        return productores;
    }

    /**
     * Método para actualizar las ventasTotalesPorProducto del resumen anual
     * @param tipoProducto el tipo de producto
     * @param cantidad la cantidad de Kg del producto
     */
    public void actualizarVentasTotalesPorProducto(TipoProducto tipoProducto, float cantidad){
        if (ventasTotalesPorProducto.containsKey(tipoProducto)) {
            ventasTotalesPorProducto.put(tipoProducto, ventasTotalesPorProducto.get(tipoProducto) + cantidad);
        } else {
            ventasTotalesPorProducto.put(tipoProducto, cantidad);
        }
    }

    /**
     * Método para actualizar el
     */

    /**
     * Método para añadir un pedido al resumen anual
     * @param pedido el pedido a añadir
     */
    public void addPedido(Pedido pedido){
        pedidos.add(pedido);
    }

    /**
     * Método auxiliar para poner a 0 el beneficio total de cada productor
     * A cada productor le sobrescribe el HashMap beneficioTotalPorProducto con un nuevo HashMap vacío
     */
    public void resetBeneficioTotalProductor() {
        for (Productor productor : productores) {
            productor.beneficioTotalPorProducto = new HashMap<>();
        }
    }

    /**
     * Método para printar las ventas por producto
     * @param fechaInicio la fecha de inicio del periodo
     * @param fechaFin la fecha de fin del periodo
     * Se recorren los productos que tiene el ArrayList productos y si el tipo de producto coincide con alguna clave
     * del HashMap ventasTotalesPorProducto, se printa el tipo de producto y su cantidad almacenada en el valor,
     * en caso contrario, se printa el tipo de producto y 0.0f
     */
    public void printarVentasPorProducto(LocalDate fechaInicio, LocalDate fechaFin) {
        System.out.println("Ventas totales por producto entre " + fechaInicio + " y " + fechaFin);
        for (Producto producto : productos) {
            if (ventasTotalesPorProducto.containsKey(producto.getTipo())){
                System.out.println(producto.getTipo() + ": " + ventasTotalesPorProducto.get(producto.getTipo())+" Kg");
            }else{
                System.out.println(producto.getTipo() + ": 0,00 Kg");
            }
        }
    }

    /**
     * Método para printar los Importes obtenidos por cada uno de los productores (desglosados por productos)
     * Si el productor tiene productos que sean del mismo tipo que la clave del HashMap beneficioTotalPorProducto,
     * se printa el TipoProducto y su beneficio, en caso contrario, se printa el tipo de producto y 0.0f
     */
    public void printarImportesPorProductor(){
        for (Productor productor : productores) {
            System.out.println("Importe obtenido por " + productor.getNombre()+":");
            /* Se recorren los productos del productor, si el tipo de producto está en el HashMap beneficioTotalPorProducto
             * se printa el tipo de producto y su beneficio, en caso contrario, se printa el tipo de producto y 0.0f
             */
            for (Producto producto : productor.getProductos().keySet()) {
                if (productor.beneficioTotalPorProducto.containsKey(producto.getTipo())){
                    System.out.println("  "+producto.getTipo() + ": " + TipoCooperativa.df.format(productor.beneficioTotalPorProducto.get(producto.getTipo()))+" €");
                }else{
                    System.out.println("  "+producto.getTipo() + ": 0,00 €");
                }

            }
            System.out.println();
        }
    }

    /**
     * Método para calcular los Importes obtenidos por cada una de las empresas de logística y printarlos
     */
    public void printarImportesPorLogistica(){
        /*Recorremos todos los pedidos y sumamos el costePequenaLogistica y costeGranLogistica, luego printamos
         * el tipo de logística y el coste total
         */
        float costePequenaLogistica = 0.0f;
        float costeGranLogistica = 0.0f;
        for (Pedido pedido : pedidos) {
            costePequenaLogistica += pedido.getCostePequenaLogistica();
            costeGranLogistica += pedido.getCosteGranLogistica();
        }
        System.out.println("Importe obtenido por Pequeña Logística: " + costePequenaLogistica + " €");
        System.out.println("Importe obtenido por Gran Logística: " + costeGranLogistica + " €");
    }

    /**
     * Método para añadir el beneficio de la cooperativa
     * @param beneficioPorProducto HashMap con el beneficio de la cooperativa
     * Si el tipo de producto ya está en el HashMap, se actualiza el valor, si no, se añade una nueva entrada
     */
    public void addBeneficioCooperativaPorProducto(HashMap<TipoProducto, Float> beneficioPorProducto){
        for (TipoProducto tipoProducto : beneficioPorProducto.keySet()) {
            if (beneficioCooperativaPorProducto.containsKey(tipoProducto)){
                beneficioCooperativaPorProducto.put(tipoProducto, beneficioCooperativaPorProducto.get(tipoProducto) + beneficioPorProducto.get(tipoProducto));
            }else{
                beneficioCooperativaPorProducto.put(tipoProducto, beneficioPorProducto.get(tipoProducto));
            }
        }
    }

    /**
     * Método para mostrar el beneficio de la cooperativa por producto
     */
    public void mostrarBeneficiosCooperativaPorProducto(){
        for (TipoProducto tipoProducto : beneficioCooperativaPorProducto.keySet()) {
            System.out.println("Producto: " + tipoProducto + " - Beneficio: " + beneficioCooperativaPorProducto.get(tipoProducto));
        }
    }

    /**
     * Método para dejar solamente el histórico de precios del mismo año que el resumen anual
     */
    public void limpiarHistoricoPrecios(){
        for (Producto producto : productos) {
            producto.historialValorReferenciaPorKg.entrySet().removeIf(entry -> entry.getKey().getYear() != anno);
        }
    }

    /**
     * Método para imprimir el histórico de precios de todos los productos
     */
    public void printEvolucionPreciosReferencia(){
        for (Producto producto : productos) {
            System.out.println("Producto: " + producto.getTipo());
            for (LocalDate fecha : producto.historialValorReferenciaPorKg.keySet()) {
                System.out.println("Fecha: " + fecha + " - Precio: " + producto.historialValorReferenciaPorKg.get(fecha));
            }
        }
    }

}
