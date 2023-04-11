import java.time.LocalDate;
import java.util.ArrayList;

/**
 * cooperativa --- Clase principal que contiene el main
 * 
 * @author (Gustavo)
 * @version (1.0)
 * @since (1.0)
 *
 * Queda pendiente:
 *
 * - Importes obtenidos por cada uno de los productores (desglosados por productos)
 * No se si sacar esta información directamente de los productores o si en mi resumen Anual guardo la información
 */
public class Cooperativa{
    public static void main(String[] args) {

        Menu menu = new Menu();
        menu.mostrarMenu();

/*
        //Creamos la cooperativa
        TipoCooperativa cooperativa = new TipoCooperativa();

        //Creamos los productos disponibles para los productores
        cooperativa.productos.add(new Producto(TipoProducto.ALGODON,1200.0f,0.60f,true));
        cooperativa.productos.add(new Producto(TipoProducto.ZANAHORIA,1000.0f,0.50f,false));
        cooperativa.productos.add(new Producto(TipoProducto.ACEITUNA,1500.0f,0.70f,true));
        cooperativa.productos.add(new Producto(TipoProducto.ACEITE,2000.0f,0.80f,false));

        //Creamos los productores
        cooperativa.productores.add(new Productor("ProductorPequeño1",TipoProductor.PEQUENO_PRODUCTOR));
        cooperativa.productores.add(new Productor("ProductorPequeño2",TipoProductor.PEQUENO_PRODUCTOR));
        cooperativa.productores.add(new Productor("ProductorGrande1",TipoProductor.GRAN_PRODUCTOR));
        cooperativa.productores.add(new Productor("ProductorGrande2",TipoProductor.GRAN_PRODUCTOR));

        //Añadimos los productos que tendrá cada productor con su extension
        cooperativa.productores.get(0).productos.put(cooperativa.productos.get(0), 2.0f);
        cooperativa.productores.get(0).productos.put(cooperativa.productos.get(1), 2.5f);
        cooperativa.productores.get(0).productos.put(cooperativa.productos.get(2), 1.0f);
        cooperativa.productores.get(0).productos.put(cooperativa.productos.get(3), 3.0f);

        cooperativa.productores.get(1).productos.put(cooperativa.productos.get(1), 1.0f);
        cooperativa.productores.get(1).productos.put(cooperativa.productos.get(2), 2.0f);
        cooperativa.productores.get(1).productos.put(cooperativa.productos.get(3), 1.5f);
        cooperativa.productores.get(1).productos.put(cooperativa.productos.get(0), 1.0f);

        cooperativa.productores.get(2).productos.put(cooperativa.productos.get(0), 1.8f);
        cooperativa.productores.get(2).productos.put(cooperativa.productos.get(1), 3.2f);
        cooperativa.productores.get(2).productos.put(cooperativa.productos.get(2), 4.1f);
        cooperativa.productores.get(2).productos.put(cooperativa.productos.get(3), 1.9f);

        cooperativa.productores.get(3).productos.put(cooperativa.productos.get(1), 1.1f);
        cooperativa.productores.get(3).productos.put(cooperativa.productos.get(2), 2.2f);
        cooperativa.productores.get(3).productos.put(cooperativa.productos.get(3), 3.3f);
        cooperativa.productores.get(3).productos.put(cooperativa.productos.get(0), 4.4f);

        //Creamos los clientes
        cooperativa.clientes.add(new Cliente("ConsumidorFinal1",TipoCliente.CONSUMIDOR_FINAL,180.0f));
        cooperativa.clientes.add(new Cliente("ConsumidorFinal2",TipoCliente.CONSUMIDOR_FINAL,242.0f));

        cooperativa.clientes.add(new Cliente("Distribuidor1",TipoCliente.DISTRIBUIDOR,195.0f));
        cooperativa.clientes.add(new Cliente("Distribuidor2",TipoCliente.DISTRIBUIDOR,320.0f));

        //Creamos ofertas de logistica de prueba
        OfertaLogistica oferta1=new EnvioEstandar("Oferta1",0.05f,0.01f,TipoCliente.DISTRIBUIDOR);
        OfertaLogistica oferta2=new EnvioEstandar("Oferta2",0.05f,0.01f,TipoCliente.CONSUMIDOR_FINAL);

        //Pedidos de prueba para testear la clase ResumenAnual de 2023
        LocalDate fechaPedido = LocalDate.of(2023, 3, 20);
        LocalDate fechaEntrega = LocalDate.of(2023, 3, 29);
        cooperativa.realizarPedido(cooperativa.clientes.get(0), cooperativa.productos.get(0), 20.0f, oferta2, fechaPedido,fechaEntrega);
        cooperativa.realizarPedido(cooperativa.clientes.get(0), cooperativa.productos.get(1), 40.0f, oferta2, fechaPedido,fechaEntrega);
        cooperativa.realizarPedido(cooperativa.clientes.get(0), cooperativa.productos.get(2), 60.0f, oferta2, fechaPedido,fechaEntrega);
        cooperativa.realizarPedido(cooperativa.clientes.get(0), cooperativa.productos.get(3), 80.0f, oferta2, fechaPedido,fechaEntrega);

        //Actualizar el precio de productos
        cooperativa.actualizarPrecioProducto(cooperativa.productos.get(0).getTipo(), 1.20f,fechaPedido);
        cooperativa.actualizarPrecioProducto(cooperativa.productos.get(1).getTipo(), 2.30f,fechaPedido);
        cooperativa.actualizarPrecioProducto(cooperativa.productos.get(2).getTipo(), 1.35f,fechaPedido);
        cooperativa.actualizarPrecioProducto(cooperativa.productos.get(3).getTipo(), 2.25f,fechaPedido);

        fechaPedido = LocalDate.of(2023, 4, 20);
        fechaEntrega = LocalDate.of(2023, 4, 29);
        cooperativa.actualizarPrecioProducto(cooperativa.productos.get(0).getTipo(), 7.20f,fechaPedido);
        cooperativa.actualizarPrecioProducto(cooperativa.productos.get(1).getTipo(), 8.30f,fechaPedido);
        cooperativa.actualizarPrecioProducto(cooperativa.productos.get(2).getTipo(), 9.35f,fechaPedido);

        //Pedidos de prueba para testear la clase ResumenAnual de 2024
        fechaPedido = LocalDate.of(2024, 3, 20);
        fechaEntrega = LocalDate.of(2024, 3, 29);
        cooperativa.realizarPedido(cooperativa.clientes.get(0), cooperativa.productos.get(0), 10.0f, oferta2, fechaPedido, fechaEntrega);

        fechaPedido = LocalDate.of(2024, 4, 20);
        fechaEntrega = LocalDate.of(2024, 4, 29);
        cooperativa.realizarPedido(cooperativa.clientes.get(0), cooperativa.productos.get(1), 20.0f, oferta2, fechaPedido, fechaEntrega);

        fechaPedido = LocalDate.of(2024, 5, 20);
        fechaEntrega = LocalDate.of(2024, 5, 29);
        cooperativa.realizarPedido(cooperativa.clientes.get(0), cooperativa.productos.get(2), 30.0f, oferta2, fechaPedido, fechaEntrega);

        fechaPedido = LocalDate.of(2024, 6, 20);
        fechaEntrega = LocalDate.of(2024, 6, 29);
        cooperativa.realizarPedido(cooperativa.clientes.get(0), cooperativa.productos.get(3), 40.0f, oferta2, fechaPedido, fechaEntrega);


        //actualizamos el precio de los productos
        cooperativa.actualizarPrecioProducto(cooperativa.productos.get(0).getTipo(), 2.20f,fechaPedido);
        cooperativa.actualizarPrecioProducto(cooperativa.productos.get(1).getTipo(), 3.30f,fechaPedido);
        cooperativa.actualizarPrecioProducto(cooperativa.productos.get(2).getTipo(), 4.35f,fechaPedido);
        cooperativa.actualizarPrecioProducto(cooperativa.productos.get(3).getTipo(), 5.25f,fechaPedido);


        fechaPedido = LocalDate.of(2024, 7, 20);
        fechaEntrega = LocalDate.of(2024, 7, 29);


        //Actualizamos el precio de los productos
        cooperativa.actualizarPrecioProducto(cooperativa.productos.get(0).getTipo(), 3.20f,fechaPedido);
        cooperativa.actualizarPrecioProducto(cooperativa.productos.get(1).getTipo(), 4.30f,fechaPedido);
        cooperativa.actualizarPrecioProducto(cooperativa.productos.get(2).getTipo(), 5.35f,fechaPedido);
        cooperativa.actualizarPrecioProducto(cooperativa.productos.get(3).getTipo(), 6.25f,fechaPedido);


        //Recorremos los resumenes anuales y los printamos por pantalla indicando el año
        for (ResumenAnual resumenAnual : cooperativa.resumenesAnuales) {
            LocalDate fechaInicio = LocalDate.of(resumenAnual.getAnno(), 3, 1);
            LocalDate fechaFin = LocalDate.of(resumenAnual.getAnno(), 4, 30);
            System.out.println("-------------------------Resumen anual de " + resumenAnual.getAnno()+"-------------------------");
            //resumenAnual.printarVentasPorProducto(fechaInicio, fechaFin);
            //resumenAnual.printarImportesPorProductor();
            //resumenAnual.printarImportesPorLogistica();
            //resumenAnual.mostrarBeneficiosCooperativaPorProducto();
            resumenAnual.printEvolucionPreciosReferencia();
        }

        //Se crea un arraylist con las ofertas de logistica
        ArrayList<OfertaLogistica> ofertas = new ArrayList<>();
        ofertas.add(oferta1);
        ofertas.add(oferta2);

        PersistenciaDatos persistencia = new PersistenciaDatos();
        persistencia.guardarOfertas(ofertas, "ofertas.txt");
        persistencia.guardarDatos(cooperativa, "cooperativa.txt");
*/

    }
}
