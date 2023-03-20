/**
 * cooperativa --- Clase principal que contiene el main
 * 
 * @author (Gustavo)
 * @version (1.0)
 * @since (1.0)
 *
 * Queda pendiente:
 * - Si actualizo el precio de un producto de la cooperativa, se actualiza en cada productor que tenga ese producto?
 *
 * - Ver el tema de las fechas de los pedidos y el precio del producto pasado 10 días:
 * Las compras a la cooperativa se realizan en una determinada fecha y se sirven dentro de un plazo máximo de diez días.
 * Si al realizar la petición, se solicita que se entregue en un plazo superior a diez días, entonces habrá que revisar
 * el valor del producto en el momento de proporcionarlo (siempre diez días antes de la fecha de entrega solicitada).
 *
 * - (Punto 3). Se quiere generar, a final de año, un resumen anual de totales de gestión de la cooperativa
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
        c.productores.get(0).productos.put(c.productos.get(0), 1.0f);
        c.productores.get(0).productos.put(c.productos.get(1), 2.0f);
        c.productores.get(0).productos.put(c.productos.get(2), 1.5f);

        c.productores.get(1).productos.put(c.productos.get(1), 1.0f);
        c.productores.get(1).productos.put(c.productos.get(2), 2.0f);
        c.productores.get(1).productos.put(c.productos.get(3), 1.5f);

        c.productores.get(2).productos.put(c.productos.get(0), 1.0f);
        c.productores.get(2).productos.put(c.productos.get(1), 2.0f);
        c.productores.get(2).productos.put(c.productos.get(2), 1.5f);

        c.productores.get(3).productos.put(c.productos.get(1), 1.0f);
        c.productores.get(3).productos.put(c.productos.get(2), 2.0f);
        c.productores.get(3).productos.put(c.productos.get(3), 1.5f);

        //Creamos los clientes
        c.clientes.add(new Cliente("ConsumidorFinal1",TipoCliente.CONSUMIDOR_FINAL,180.0f));
        c.clientes.add(new Cliente("ConsumidorFinal2",TipoCliente.CONSUMIDOR_FINAL,242.0f));

        c.clientes.add(new Cliente("Distribuidor1",TipoCliente.DISTRIBUIDOR,195.0f));
        c.clientes.add(new Cliente("Distribuidor2",TipoCliente.DISTRIBUIDOR,320.0f));

        //Creamos ofertas de logistica de prueba
        OfertaLogistica oferta1=new EnvioEstandar("Oferta1",0.05f,0.01f,TipoCliente.DISTRIBUIDOR);
        OfertaLogistica oferta2=new EnvioEstandar("Oferta2",0.05f,0.01f,TipoCliente.CONSUMIDOR_FINAL);

        //Cliente 1 solicita comprar un tipo de producto y una cantidad con su carrito de la compra
        TipoProducto productoCarrito = TipoProducto.MELOCOTON;
        float cantidadCarrito = 180.0f;

        //Se añade el pedido a la lista de pedidos solo si existe el producto y hay la cantidad suficiente
        if (!c.productoDisponible(productoCarrito)){
            System.out.println("No existe el producto en la cooperativa");
        }else if(c.calcularCantidadTotalEnKg(productoCarrito)<cantidadCarrito){
            System.out.println("No hay suficiente cantidad del producto en la cooperativa");
        }else {
            //Se realiza el pedido
            c.realizarPedido(c.clientes.get(0), c.getProducto(productoCarrito), cantidadCarrito, oferta2);
            //Se añade el pedido a la lista de pedidos del cliente
            c.clientes.get(0).addPedido(c.pedidos.get(0));
            //Se printa por pantalla los detalles del pedido
            System.out.println("Pedido realizado");
            System.out.println(c.pedidos.get(0).toString());
        }
    }
}
