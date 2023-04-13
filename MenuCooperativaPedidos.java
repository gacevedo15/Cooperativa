import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

/**
 * Clase que representa la gestión de pedidos de la cooperativa.
 * Se pueden simular pedidos de los clientes a la cooperativa, listarlos y ver los detalles de cada uno.
 * @author Gustavo Acevedo Alfonso
 * @version 1.0
 */
public class MenuCooperativaPedidos implements IMenu {

    /**
     * Scanner para leer la entrada del usuario.
     */
    private Scanner scanner;

    /**
         * Constructor de la clase MenuCooperativaPedidos que inicializa el Scanner.
     */
    public MenuCooperativaPedidos() {
        scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("---- MENU COOPERATIVA PEDIDOS ----");
            System.out.println("1. Simular nuevo pedido como cliente");
            System.out.println("2. Ver detalles de un pedido");
            System.out.println("3. Eliminar pedido");
            System.out.println("0. Volver al MENU COOPERATIVA");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // limpiar el buffer de entrada
            } catch (InputMismatchException e) {
                System.out.println("Error: debe ingresar un número entero");
                scanner.nextLine(); // Limpia el buffer de entrada
                opcion = -1; // Asigna un valor inválido para que vuelva a mostrar el menú
            }
            switch (opcion) {
                case 1:
                    simularPedidoComoCliente();
                    break;
                case 2:
                    verDetallesPedido();
                    break;
                case 3:
                    eliminarPedido();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, intente de nuevo.");
                    break;
            }
        } while (opcion != 0);
    }

    /*****************************************************
     *      Métodos para simular pedidos como cliente
     *****************************************************/

    /**
     * Método para simular un pedido como cliente.
     */
    private void simularPedidoComoCliente() {
            System.out.println("---- SIMULAR PEDIDO COMO CLIENTE ----");

            // Seleccionar el cliente que realiza el pedido
            Cliente cliente = null;
            do {
                System.out.println("Seleccione el cliente que realiza el pedido:");
                try {
                    cliente = new MenuCooperativaClientes().seleccionarCliente();
                } catch (InputMismatchException e) {
                    System.out.println("Error: Debe ingresar un número entero.");
                    scanner.nextLine();
                }
            } while (cliente == null);

            // Seleccionar el producto que se desea comprar
            Producto producto = null;
            do {
                System.out.println("Seleccione el producto que desea comprar:");
                try {
                    producto = new MenuCooperativaProductos().seleccionarProducto();
                    System.out.println("Cantidad disponible: "+Menu.cooperativa.calcularCantidadTotalEnKg(producto.getTipo())+" Kg");
                } catch (InputMismatchException e) {
                    System.out.println("Error: Debe ingresar un número entero.");
                    scanner.nextLine();
                }
            } while (producto == null);

            // Comprobar si hay suficiente cantidad de producto en la cooperativa
            System.out.println("Ingrese la cantidad que desea comprar en Kg:");
            float cantCompradaKg = scanner.nextFloat();
            scanner.nextLine();
            try {
                comprobarCantidadProducto(producto, cantCompradaKg);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                return;
            }

            // Comprobar que el cliente tenga permiso para comprar la cantidad de producto solicitada
            if (cliente.getTipoCliente() == TipoCliente.CONSUMIDOR_FINAL && cantCompradaKg > 100) {
                System.out.println("Error: Un cliente de tipo consumidor final no puede comprar más de 100Kg.");
                return;
            } else if (cliente.getTipoCliente() == TipoCliente.DISTRIBUIDOR && cantCompradaKg < 1000) {
                System.out.println("Error: Un cliente de tipo distribuidor final debe comprar al menos 1000Kg.");
                return;
            }

            // Seleccionar la oferta logística que se puede aplicar a las condiciones que se solicitan
            OfertaLogistica ofertaLogistica = seleccionarOfertaLogistica(cliente, producto);
            if (ofertaLogistica == null) {
                return;
            }

            // Ingresar la fecha de pedido y la fecha de entrega
            LocalDate fechaPedido = null;
            do {
                System.out.println("Ingrese la fecha de pedido: ");
                try {
                    fechaPedido = introducirFecha();
                } catch (DateTimeException e) {
                    System.out.println("Error: Debe ingresar una fecha válida en el formato dd/mm/aaaa.");
                }
            } while (fechaPedido == null);

            LocalDate fechaEntrega = null;
            do {
                System.out.println("Ingrese la fecha de entrega: ");
                try {
                    fechaEntrega = introducirFecha();
                } catch (DateTimeException e) {
                    System.out.println("Error: Debe ingresar una fecha válida en el formato dd/mm/aaaa.");
                }
            } while (fechaEntrega == null);

            //La fecha de entrega no puede ser anterior a la fecha de pedido
            if (fechaEntrega.isBefore(fechaPedido)) {
                System.out.println("Error: La fecha de entrega no puede ser anterior a la fecha de pedido.");
                return;
            }

            // Realizar el pedido
            Menu.cooperativa.realizarPedido(cliente, producto, cantCompradaKg, ofertaLogistica, fechaPedido, fechaEntrega);
            System.out.println("Pedido realizado con éxito.");
    }


    /**
     * Método para comprobar que se puede comprar la cantidad de producto que se desea.
     */
    private boolean comprobarCantidadProducto(Producto producto, float cantCompradaKg) {
        return cantCompradaKg <= Menu.cooperativa.calcularCantidadTotalEnKg(producto.getTipo());
    }

    /**
     * Método para seleccionar la oferta logística que se va a utilizar para el pedido.
     */
    private OfertaLogistica seleccionarOfertaLogistica(Cliente cliente, Producto producto) {
        ArrayList<OfertaLogistica> ofertasLogisticas = Menu.cooperativa.getOfertasLogisticas();

        // Si no hay ofertas disponibles, devolver null y mostrar mensaje
        if (ofertasLogisticas.isEmpty()) {
            System.out.println("No existen ofertas disponibles.");
            return null;
        }

        // Filtrar las ofertas por tipo de cliente
        ArrayList<OfertaLogistica> ofertasFiltradas = new ArrayList<>();
        for (OfertaLogistica oferta : ofertasLogisticas) {
            if (oferta.getTipoCliente() == cliente.getTipoCliente()) {
                ofertasFiltradas.add(oferta);
            }
        }

        // Si no hay ofertas disponibles para el tipo de cliente, devolver null y mostrar mensaje
        if (ofertasFiltradas.isEmpty()) {
            System.out.println("No existen ofertas disponibles para el tipo de cliente.");
            return null;
        }

        // Mostrar las ofertas filtradas y solicitar la selección de una oferta
        System.out.println("Seleccione una oferta logística:");
        for (int i = 0; i < ofertasFiltradas.size(); i++) {
            System.out.println((i + 1) + ". " + ofertasFiltradas.get(i).getNombre());
        }
        int opcion;
        do {
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
                if (opcion < 1 || opcion > ofertasFiltradas.size()) {
                    System.out.println("Error: Debe ingresar un número entre 1 y " + ofertasFiltradas.size() + ".");
                } else {
                    return ofertasFiltradas.get(opcion - 1);
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número entero.");
                scanner.nextLine();
                opcion = 0;
            }
        } while (opcion < 1 || opcion > ofertasFiltradas.size());

        return null; // No debería llegar aquí nunca
    }

    /**
     * Método para introducir una fecha LocalDate.
     */
    private LocalDate introducirFecha() {
        int dia=0, mes=0, anio=0;
        LocalDate fecha = null;
        do {
            System.out.println("Día:");
            try {
                dia = scanner.nextInt();
                scanner.nextLine();
                if (dia < 1 || dia > 31) {
                    System.out.println("Error: Debe ingresar un número entre 1 y 31.");
                    continue;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número entero.");
                scanner.nextLine();
                continue;
            }

            do {
                System.out.println("Mes:");
                try {
                    mes = scanner.nextInt();
                    scanner.nextLine();
                    if (mes < 1 || mes > 12) {
                        System.out.println("Error: Debe ingresar un número entre 1 y 12.");
                        continue;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Debe ingresar un número entero.");
                    scanner.nextLine();
                    continue;
                }

                do {
                    System.out.println("Año:");
                    try {
                        anio = scanner.nextInt();
                        scanner.nextLine();
                        if (anio < 2020) {
                            System.out.println("Error: Debe ingresar un número mayor o igual a 2020.");
                            continue;
                        }
                        fecha = LocalDate.of(anio, mes, dia);
                    } catch (DateTimeException e) {
                        System.out.println("Error: Fecha inválida.");
                        continue;
                    } catch (InputMismatchException e) {
                        System.out.println("Error: Debe ingresar un número entero.");
                        scanner.nextLine();
                        continue;
                    }
                } while (fecha == null || anio < 2020);
            } while (fecha == null || mes < 1 || mes > 12);
        } while (fecha == null || dia < 1 || dia > 31);
        return fecha;
    }


    /****************************************************
     *    Métodos para ver los detalles de un pedido    *
     ****************************************************/
    private void verDetallesPedido() {
        // Solicitar el número de pedido
        int numPedido = solicitarNumPedido();

        // Buscar el pedido
        Pedido pedido = Menu.cooperativa.buscarPedido(numPedido);
        if (pedido == null) {
            System.out.println("No existe ningún pedido con ese número.");
            return;
        }

        // Mostrar los detalles del pedido
        System.out.println(pedido);
    }

    /**
     * Método para solicitar el número de pedido.
     */
    private int solicitarNumPedido() {
        int numPedido;
        do {
            System.out.println("Ingrese el número de pedido:");
            try {
                numPedido = scanner.nextInt();
                scanner.nextLine();
                if (numPedido < 1) {
                    System.out.println("Error: Debe ingresar un número mayor o igual a 1.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número entero.");
                scanner.nextLine();
            }
        } while (true);
        return numPedido;
    }


    /*************************************
     *    Métodos para Eliminar pedido   *
     *************************************/

    /**
     * Método para eliminar un pedido.
     */
    private void eliminarPedido() {
        // Solicitar el número de pedido
        int numPedido = solicitarNumPedido();

        // Buscar el pedido
        Pedido pedido = Menu.cooperativa.buscarPedido(numPedido);
        if (pedido == null) {
            System.out.println("No existe ningún pedido con ese número.");
            return;
        }

        // Eliminar el pedido
        Menu.cooperativa.eliminarPedido(pedido);
        System.out.println("Pedido eliminado con éxito.");
    }





}