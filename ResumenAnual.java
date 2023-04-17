import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Clase que representa un resumen anual
 * Funcionalidades:
 * - Ventas totales en un periodo determinado de cada uno de los productos de la cooperativa.
 * - Importes obtenidos por cada uno de los productores (desglosados por productos)
 * - Importes obtenidos por cada una de las empresas de logística.
 * - Beneficios de la cooperativa por cada uno de los productos.
 * - Evolución de los precios de referencias de cada productos.
 */
public class ResumenAnual implements Serializable {

    /**
     * Año del resumen anual
     */
    private final int anno;

    /**
     * Lista de pedidos del año
     */
    public ArrayList<Pedido> pedidos;

    /**
     * Lista de productores de la cooperativa en ese año
     */
    private final ArrayList<Productor> productores;

    /**
     * Lista de productos de la cooperativa en ese año
     */
    public ArrayList<Producto> productos;

    /**
     * HashMap que almacena las ventas totales por producto
     */
    public HashMap<TipoProducto,Float> ventasTotalesPorProducto;

    /**
     * HashMap que almacena el beneficio de la cooperativa por producto
     */
    public HashMap<TipoProducto,Float> beneficioCooperativaPorProducto;

    /**
     * Clase que representa el resumen anual de una cooperativa para un año específico.
     * @param anno el año del resumen anual.
     * @param cooperativa la cooperativa a la que pertenece el resumen anual.
     */
    public ResumenAnual(int anno, TipoCooperativa cooperativa){
        this.anno = anno; //Año del resumen anual
        this.pedidos = new ArrayList<>(); //Se crea un ArrayList de pedidos vacío
        this.productores = cooperativa.productores; //Se obtienen los productores de la cooperativa
        resetBeneficioTotalProductor(); //Se resetea el beneficio total de cada productor para tenerlo a 0
        this.productos = cooperativa.productos; //Se obtienen los productos de la cooperativa
        //limpiarHistoricoPrecios(); //Se limpia el histórico de precios de cada producto para que no haya precios de años anteriores
        this.ventasTotalesPorProducto = new HashMap<>(); //Se crea un HashMap vacío para almacenar las ventas totales por producto
        this.beneficioCooperativaPorProducto = new HashMap<>(); //Se crea un HashMap vacío para almacenar el beneficio de la cooperativa por producto
    }

    /**
     * Devuelve el año del resumen anual
     * @return el año del resumen anual
     */
    public int getAnno() {
        return anno;
    }

    /**
     * Devuelve la lista de productores de la cooperativa en ese año
     * @return la lista de productores de la cooperativa en ese año
     */
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
     * Método para añadir un pedido al resumen anual
     * @param pedido el pedido a añadir
     */
    public void addPedido(Pedido pedido){
        pedidos.add(pedido);
    }

    /**
     * Método para eliminar un pedido del resumen anual
     * @param pedido el pedido a eliminar
     */
    public void eliminarPedido(Pedido pedido){
        pedidos.remove(pedido);
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
     * Método para printar las ventas por producto en un período determinado.
     * @param fechaInicio la fecha de inicio del periodo
     * @param fechaFin la fecha de fin del periodo
     */
    public void printarVentasPorProducto(LocalDate fechaInicio, LocalDate fechaFin) {

        //Se recorren los pedidos y se actualizan las ventas totales por producto si el pedido está en el período determinado
        for (Pedido pedido : pedidos) {
            if (pedido.getFechaPedido().isAfter(fechaInicio) && pedido.getFechaPedido().isBefore(fechaFin)) {
                actualizarVentasTotalesPorProducto(pedido.getProducto().getTipo(), pedido.getCantCompradaKg());
            }
        }

        System.out.println("Ventas totales por producto entre " + fechaInicio + " y " + fechaFin);

        /* Se recorren los productos, si el tipo de producto está en el HashMap ventasTotalesPorProducto,
         * se printa el TipoProducto y su venta, en caso contrario, se printa el tipo de producto y 0.0f*/
        for (Producto producto : productos) {
            System.out.println(producto.getTipo() + ": " + ventasTotalesPorProducto.getOrDefault(producto.getTipo()+ " Kg", 0.0f));
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
             * se printa el tipo de producto y su beneficio, en caso contrario, se printa el tipo de producto y 0.0f */
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

        float costePequenaLogistica = 0.0f;
        float costeGranLogistica = 0.0f;

        /*Recorremos todos los pedidos y sumamos el costePequenaLogistica y costeGranLogistica, luego printamos
         * el tipo de logística y el coste total */
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
     * Se recorren todos los productos, si el tipo de producto está en el HashMap beneficioCooperativaPorProducto,
     * se printa el TipoProducto y su beneficio, en caso contrario, se printa el tipo de producto y 0.0f
     */
    public void mostrarBeneficiosCooperativaPorProducto(){
        System.out.println("   Beneficio de la cooperativa por producto:   ");
        for (Producto producto : productos) {
            if (beneficioCooperativaPorProducto.containsKey(producto.getTipo())){
                System.out.println("      " + producto.getTipo() + ": " + TipoCooperativa.df.format(beneficioCooperativaPorProducto.get(producto.getTipo())) + " €");
            }else{
                System.out.println("      " + producto.getTipo() + ": 0,00 €");
            }
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
     * Método para imprimir el histórico de precios de todos los productos durante el año del Resumen,
     * Recorre los productos y si el tipo de producto está en el HashMap historialValorReferenciaPorKg y el año de la fecha
     * es el mismo que el año del resumen, se printa la fecha y el precio de referencia, en caso contrario, se printa el tipo de producto y "No hay datos"
     */
    public void printEvolucionPreciosReferencia() {
        System.out.println("   Evolución de los precios de referencia de los productos durante el año " + anno + ":   ");
        for (Producto producto : productos) {
            if (!producto.historialValorReferenciaPorKg.isEmpty()) {
                boolean encontrado = false;
                LocalDate ultimaFecha = null;
                float ultimoPrecio = 0.0f;

                System.out.println("      " + producto.getTipo() + ":");

                // Utilizamos TreeMap para ordenar las fechas, puesto que el HashMap no las ordena
                TreeMap<LocalDate, Float> historialOrdenado = new TreeMap<>(producto.historialValorReferenciaPorKg);

                for (Map.Entry<LocalDate, Float> entry : historialOrdenado.entrySet()) {
                    LocalDate fecha = entry.getKey();
                    float precio = entry.getValue();
                    if (fecha.getYear() == anno) {
                        encontrado = true;
                        System.out.println("         " + fecha + ": " + precio + " €/kg");
                    } else if (fecha.getYear() < anno) {
                        ultimaFecha = fecha;
                        ultimoPrecio = precio;
                    }
                }

                // Si no se ha encontrado ningún precio en el año del resumen, se printa la última fecha y precio
                if (!encontrado) {
                    if (ultimaFecha != null) {
                        System.out.println("         No ha tenido modificaciones durante el año " + anno + ". Última actualización: " + ultimaFecha + ": " + ultimoPrecio + " €/kg");
                    }
                }
            }
        }
    }


}
