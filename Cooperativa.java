import java.time.LocalDate;

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
public class cooperativa{
    public static void main(String[] args) {
        //Creamos la cooperativa
        TipoCooperativa c = new TipoCooperativa();

        //Creamos los productos disponibles para los productores
        c.productos.add(new Producto(TipoProducto.ALGODON,1200.0f,0.60f,true));
        c.productos.add(new Producto(TipoProducto.ZANAHORIA,1000.0f,0.50f,false));
        c.productos.add(new Producto(TipoProducto.ACEITUNA,1500.0f,0.70f,true));
        c.productos.add(new Producto(TipoProducto.ACEITE,2000.0f,0.80f,false));

        //Creamos los productores
        c.productores.add(new Productor("ProductorPequeño1",TipoProductor.PEQUENO_PRODUCTOR));
        c.productores.add(new Productor("ProductorPequeño2",TipoProductor.PEQUENO_PRODUCTOR));
        c.productores.add(new Productor("ProductorGrande1",TipoProductor.GRAN_PRODUCTOR));
        c.productores.add(new Productor("ProductorGrande2",TipoProductor.GRAN_PRODUCTOR));

        //Añadimos los productos que tendrá cada productor con su extension
        c.productores.get(0).productos.put(c.productos.get(0), 2.0f);
        c.productores.get(0).productos.put(c.productos.get(1), 2.5f);
        c.productores.get(0).productos.put(c.productos.get(2), 1.0f);
        c.productores.get(0).productos.put(c.productos.get(3), 3.0f);

        c.productores.get(1).productos.put(c.productos.get(1), 1.0f);
        c.productores.get(1).productos.put(c.productos.get(2), 2.0f);
        c.productores.get(1).productos.put(c.productos.get(3), 1.5f);
        c.productores.get(1).productos.put(c.productos.get(0), 1.0f);

        c.productores.get(2).productos.put(c.productos.get(0), 1.8f);
        c.productores.get(2).productos.put(c.productos.get(1), 3.2f);
        c.productores.get(2).productos.put(c.productos.get(2), 4.1f);
        c.productores.get(2).productos.put(c.productos.get(3), 1.9f);

        c.productores.get(3).productos.put(c.productos.get(1), 1.1f);
        c.productores.get(3).productos.put(c.productos.get(2), 2.2f);
        c.productores.get(3).productos.put(c.productos.get(3), 3.3f);
        c.productores.get(3).productos.put(c.productos.get(0), 4.4f);

        //Creamos los clientes
        c.clientes.add(new Cliente("ConsumidorFinal1",TipoCliente.CONSUMIDOR_FINAL,180.0f));
        c.clientes.add(new Cliente("ConsumidorFinal2",TipoCliente.CONSUMIDOR_FINAL,242.0f));

        c.clientes.add(new Cliente("Distribuidor1",TipoCliente.DISTRIBUIDOR,195.0f));
        c.clientes.add(new Cliente("Distribuidor2",TipoCliente.DISTRIBUIDOR,320.0f));

        //Creamos ofertas de logistica de prueba
        OfertaLogistica oferta1=new EnvioEstandar("Oferta1",0.05f,0.01f,TipoCliente.DISTRIBUIDOR);
        OfertaLogistica oferta2=new EnvioEstandar("Oferta2",0.05f,0.01f,TipoCliente.CONSUMIDOR_FINAL);

        //Creamos un resumen anual de 2023
        c.resumenesAnuales.add(new ResumenAnual(2023,c));
        //Creamos un resumen anual de 2024
        c.resumenesAnuales.add(new ResumenAnual(2024,c));

        //Cliente 1 solicita comprar un tipo de producto y una cantidad con su carrito de la compra
        TipoProducto productoCarrito = TipoProducto.MELOCOTON;
        float cantidadCarrito = 180.0f;

        //Se añade el pedido a la lista de pedidos solo si existe el producto y hay la cantidad suficiente
        if (!c.productoDisponible(productoCarrito)){
            System.out.println("No existe el producto en la cooperativa");
        }else if(c.calcularCantidadTotalEnKg(productoCarrito)<cantidadCarrito){
            System.out.println("No hay suficiente cantidad del producto en la cooperativa");
        }else {
            LocalDate fechaEntrega = LocalDate.of(2023, 3, 27);
            //Se realiza el pedido
            c.realizarPedido(c.clientes.get(0), c.getProducto(productoCarrito), cantidadCarrito, oferta2, fechaEntrega,fechaEntrega);
            //Se printa por pantalla los detalles del pedido
            System.out.println("Pedido realizado");
            System.out.println(c.pedidos.get(0).toString());
        }

        //Pedidos de prueba para testear la clase ResumenAnual de 2023
        LocalDate fechaPedido = LocalDate.of(2023, 3, 20);
        LocalDate fechaEntrega = LocalDate.of(2023, 3, 29);
        c.realizarPedido(c.clientes.get(0), c.productos.get(0), 20.0f, oferta2, fechaPedido,fechaEntrega);
        c.realizarPedido(c.clientes.get(0), c.productos.get(1), 40.0f, oferta2, fechaPedido,fechaEntrega);
        c.realizarPedido(c.clientes.get(0), c.productos.get(2), 60.0f, oferta2, fechaPedido,fechaEntrega);
        c.realizarPedido(c.clientes.get(0), c.productos.get(3), 80.0f, oferta2, fechaPedido,fechaEntrega);

        c.productos.get(0).actualizarPrecio(10.5f);
        c.productos.get(1).actualizarPrecio(20.5f);
        c.productos.get(2).actualizarPrecio(30.5f);
        c.productos.get(3).actualizarPrecio(40.5f);

        //Pedidos de prueba para testear la clase ResumenAnual de 2024
        fechaPedido = LocalDate.of(2024, 3, 20);
        fechaEntrega = LocalDate.of(2024, 3, 29);
        c.realizarPedido(c.clientes.get(0), c.productos.get(0), 10.0f, oferta2, fechaPedido, fechaEntrega);
        c.realizarPedido(c.clientes.get(0), c.productos.get(1), 20.0f, oferta2, fechaPedido, fechaEntrega);
        c.realizarPedido(c.clientes.get(0), c.productos.get(2), 30.0f, oferta2, fechaPedido, fechaEntrega);
        c.realizarPedido(c.clientes.get(0), c.productos.get(3), 40.0f, oferta2, fechaPedido, fechaEntrega);

        c.productos.get(0).actualizarPrecio(55.5f);
        c.productos.get(1).actualizarPrecio(45.5f);
        c.productos.get(2).actualizarPrecio(35.5f);
        c.productos.get(3).actualizarPrecio(25.5f);



        //Recorremos los resumenes anuales y los printamos por pantalla indicando el año
        for (ResumenAnual resumenAnual : c.resumenesAnuales) {
            LocalDate fechaInicio = LocalDate.of(resumenAnual.getAnno(), 1, 1);
            LocalDate fechaFin = LocalDate.of(resumenAnual.getAnno(), 12, 31);
            System.out.println("-------------------------Resumen anual de " + resumenAnual.getAnno()+"-------------------------");
            //resumenAnual.printarVentasPorProducto(fechaInicio, fechaFin);
            //resumenAnual.printarImportesPorProductor();
            resumenAnual.printarImportesPorProductor();
            //resumenAnual.printarImportesPorLogistica();
        }


    }
}
