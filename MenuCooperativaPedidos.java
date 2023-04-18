import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

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

    /**
     * Implementación del método mostrarMenu() de la interfaz IMenu.
     */
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
                case 1 -> simularPedidoComoCliente();
                case 2 -> verDetallesPedido();
                case 3 -> eliminarPedido();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida. Por favor, intente de nuevo.");
            }
        } while (opcion != 0);
    }

    /*----------------------------------------------------*
     *      Métodos para simular pedidos como cliente
     ----------------------------------------------------*/

    /**
     * Método para simular un pedido como cliente.
     */
    private void simularPedidoComoCliente() {
            System.out.println("---- SIMULAR PEDIDO COMO CLIENTE ----");

            // Selecciona el cliente que realiza el pedido
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

            // Selecciona el producto que se desea comprar
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

            // Comprueba si hay suficiente cantidad de producto en la cooperativa
            System.out.println("Ingrese la cantidad que desea comprar en Kg:");
            float cantCompradaKg = scanner.nextFloat();
            scanner.nextLine();
            try {
                comprobarCantidadProducto(producto, cantCompradaKg);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                return;
            }

            // Comprueba que el tipo de cliente pueda comprar la cantidad que desea
            if (cliente.getTipoCliente() == TipoCliente.CONSUMIDOR_FINAL && cantCompradaKg > 100) {
                System.out.println("Error: Un cliente de tipo consumidor final no puede comprar más de 100Kg.");
                return;
            } else if (cliente.getTipoCliente() == TipoCliente.DISTRIBUIDOR && cantCompradaKg < 1000) {
                System.out.println("Error: Un cliente de tipo distribuidor final debe comprar al menos 1000Kg.");
                return;
            }

            // Selecciona la oferta logística que se puede aplicar a las condiciones que se solicitan
            OfertaLogistica ofertaLogistica = seleccionarOfertaLogistica(cliente);
            if (ofertaLogistica == null) {
                return;
            }

            // Ingresa la fecha de pedido y la fecha de entrega
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

            // Realiza el pedido
            Menu.cooperativa.realizarPedido(cliente, producto, cantCompradaKg, ofertaLogistica, fechaPedido, fechaEntrega);
            System.out.println("Pedido realizado con éxito.");
    }


    /**
     * Método para comprobar que se puede comprar la cantidad de producto que se desea.
     * @param producto Producto que se desea comprar
     * @param cantCompradaKg Cantidad de producto que se desea comprar
     * @return true si se puede comprar la cantidad de producto que se desea, false en caso contrario
     */
    private boolean comprobarCantidadProducto(Producto producto, float cantCompradaKg) {
        return cantCompradaKg <= Menu.cooperativa.calcularCantidadTotalEnKg(producto.getTipo());
    }

    /**
     * Método para seleccionar la oferta logística que se va a utilizar para el pedido.
     * @param cliente Cliente que realiza el pedido
     * @return Oferta logística seleccionada
     */
    private OfertaLogistica seleccionarOfertaLogistica(Cliente cliente) {
        ArrayList<OfertaLogistica> ofertasLogisticas = Menu.cooperativa.getOfertasLogisticas();

        // Si no hay ofertas disponibles, devuelve null y muestra mensaje por pantalla
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

        // Si no hay ofertas disponibles para el tipo de cliente, devuelve null y muestra mensaje por pantalla
        if (ofertasFiltradas.isEmpty()) {
            System.out.println("No existen ofertas disponibles para el tipo de cliente.");
            return null;
        }

        // Muestra las ofertas filtradas y solicita la selección de una de ellas
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

        return null;
    }

    /**
     * Método para introducir una fecha LocalDate.
     * @return Fecha introducida
     */
    public LocalDate introducirFecha() {
        int dia, mes, anno;
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
                        anno = scanner.nextInt();
                        scanner.nextLine();
                        if (anno < 2020) {
                            System.out.println("Error: Debe ingresar un número mayor o igual a 2020.");
                            continue;
                        }
                        fecha = LocalDate.of(anno, mes, dia);
                    } catch (DateTimeException e) {
                        System.out.println("Error: Fecha inválida.");
                    } catch (InputMismatchException e) {
                        System.out.println("Error: Debe ingresar un número entero.");
                        scanner.nextLine();
                    }
                } while (fecha == null);
            } while (fecha == null);
        } while (fecha == null);
        return fecha;
    }


    /*----------------------------------------------------*
     *    Métodos para ver los detalles de un pedido
     ----------------------------------------------------*/

    /**
     * Método para ver los detalles de un pedido.
     */
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


    /*----------------------------------------------*
     *    Métodos para Eliminar pedido
     ----------------------------------------------*/

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