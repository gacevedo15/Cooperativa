import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Clase que representa el menú de la cooperativa de la gestión de Clientes.
 * @author Gustavo Acevedo Alfonso
 * @version 1.0
 */
public class MenuCooperativaClientes implements IMenu {

    /**
     * Scanner para leer la entrada del usuario.
     */
    private Scanner scanner;

    /**
     * Constructor de la clase MenuCooperativaProductos. Inicializa el Scanner.
     */
    public MenuCooperativaClientes() {
        scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("---- MENU COOPERATIVA CLIENTES ----");
            System.out.println("1. Crear Cliente");
            System.out.println("2. Ver detalles de un Cliente");
            System.out.println("3. Cambiar tipo de Cliente");
            System.out.println("4. Hacer VIP a un Cliente");
            System.out.println("5. Ver pedidos de un Cliente");
            System.out.println("6. Eliminar Cliente");
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
                    crearCliente();
                    break;
                case 2:
                    verDetallesCliente();
                    break;
                case 3:
                    cambiarTipoCliente();
                    break;
                case 4:
                    hacerVIPCliente();
                    break;
                case 5:
                    verPedidosCliente();
                    break;
                case 6:
                    eliminarCliente();
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

    /*********************************************
     *      Métodos para crear Clientes
     *********************************************/

    /**
     * Método para crear un cliente.
     */
    /**
     * Método para crear un cliente.
     */
    private void crearCliente() {
        System.out.println("---- CREAR CLIENTE ----");
        System.out.println("Ingrese el nombre del cliente:");

        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("Error: el nombre no puede estar vacío.");
            return;
        }
        if (!nombre.matches("[a-zA-Z ]+")) {
            System.out.println("Error: el nombre solo puede contener letras y espacios.");
            return;
        }
        if (existeCliente(nombre)) {
            System.out.println("Error: ya existe un cliente con ese nombre.");
            return;
        }

        float distancia;
        try {
            System.out.println("Ingrese la distancia a la que se encuentra el cliente:");
            distancia = Float.parseFloat(scanner.nextLine());
            if (distancia <= 0 || distancia >= 10000) {
                System.out.println("Error: la distancia debe estar entre 0 y 10000.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: la distancia debe ser un número válido.");
            return;
        }
        TipoCliente tipoCliente = seleccionarTipoCliente();

        Cliente cliente = new Cliente(nombre, tipoCliente, distancia);
        Menu.cooperativa.addCliente(cliente);
        System.out.println("El cliente " + nombre + " ha sido creado con éxito.");
    }


    /**
     * Método para comprobar si existe un cliente.
     * @param nombre el nombre del cliente a buscar.
     * @return true si existe un cliente con ese nombre, false en caso contrario.
     */
    private boolean existeCliente(String nombre) {
        for (Cliente cliente : Menu.cooperativa.getClientes()) {
            if (cliente.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método para mostrar los tipos de cliente y seleccionar uno.
     * @return el tipo de cliente seleccionado.
     */
    public TipoCliente seleccionarTipoCliente() {
        System.out.println("Seleccione el tipo de cliente: ");
        int i = 1;
        for (TipoCliente tipoCliente : TipoCliente.values()) {
            System.out.println(i + ". " + tipoCliente);
            i++;
        }
        int input;
        try {
            input = scanner.nextInt();
            scanner.nextLine(); // limpiar el buffer de entrada
            if (input < 1 || input > TipoCliente.values().length) {
                System.out.println("Error: seleccione un número válido.");
                return null;
            } else {
                return TipoCliente.values()[input - 1];
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: seleccione un número válido.");
            scanner.nextLine(); // limpiar el buffer de entrada
            return null;
        }
    }


    /************************************************
     *      Métodos para ver detalles de un Cliente
     ***********************************************/

    /**
     * Método para ver los detalles de un cliente.
     */
     private void verDetallesCliente() {
         System.out.println("Detalles de Cliente: ");
         Cliente cliente = seleccionarCliente();
         if (cliente != null) {
             System.out.println(cliente);
         }
     }

    /**
     * Método para mostrar una lista de clientes disponibles. Se selecciona uno y se devuelve.
     * @return el cliente seleccionado, o null si no hay clientes disponibles.
     */
    public Cliente seleccionarCliente() {
        List<Cliente> clientes = Menu.cooperativa.getClientes();
        if (clientes.isEmpty()) {
            System.out.println("Error: no hay clientes disponibles.");
            return null;
        }

        System.out.println("Seleccione el Cliente: ");
        int i = 1;
        for (Cliente cliente : clientes) {
            System.out.println(i + ". " + cliente.getNombre() + " - " + cliente.getTipoCliente());
            i++;
        }

        int input;
        try {
            input = scanner.nextInt();
            scanner.nextLine(); // limpiar el buffer de entrada
            if (input < 1 || input > clientes.size()) {
                System.out.println("Error: seleccione un número válido.");
            } else {
                return clientes.get(input - 1);
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: seleccione un número válido.");
            scanner.nextLine(); // limpiar el buffer de entrada
        }

        return null;
    }

    /************************************************
     *      Métodos para cambiar tipo de Cliente
     ***********************************************/

    /**
     * Método para cambiar el tipo de un cliente.
     */
    /**
     * Método para cambiar el tipo de un cliente.
     */
    private void cambiarTipoCliente() {
        System.out.println("---- CAMBIAR TIPO DE CLIENTE ----");
        Cliente cliente = seleccionarCliente();
        if (cliente == null) {
            return;
        }
        TipoCliente tipoCliente = seleccionarTipoCliente();
        if (tipoCliente == null) {
            return;
        }
        // Verificar que el cliente no sea del tipo seleccionado
        if (cliente.getTipoCliente() == tipoCliente) {
            System.out.println("Error: el cliente ya es del tipo seleccionado.");
            return;
        }
        try {
            cliente.setTipoCliente(tipoCliente);
            System.out.println("El tipo de cliente ha sido cambiado con éxito a " + tipoCliente + ".");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /************************************************
     *      Métodos para Hacer VIP a un Cliente
     ***********************************************/

    /**
     * Método para hacer VIP a un cliente.
     */
    private void hacerVIPCliente() {
        System.out.println("---- HACER VIP A UN CLIENTE ----");
        Cliente cliente = seleccionarCliente();
        if (cliente == null) {
            return;
        }
        // Verificar que el cliente no sea VIP
        if (cliente.getEsClientePremium()) {
            System.out.println("Error: el cliente ya es VIP.");
            return;
        }
        try {
            cliente.setEsClientePremium(true);
            System.out.println("El cliente "+ cliente.getNombre() +" ahora es VIP.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /****************************************************************
     *      Métodos para ver los pedidos de un Cliente
     ***************************************************************/

    /**
     * Método para ver los pedidos de un cliente.
     */
    private void verPedidosCliente() {
        System.out.println("---- VER PEDIDOS DE UN CLIENTE ----");
        Cliente cliente = seleccionarCliente();
        if (cliente == null) {
            return;
        }
        List<Pedido> pedidos = cliente.getPedidos();
        if (pedidos.isEmpty()) {
            System.out.println("El cliente " + cliente.getNombre() + " no tiene pedidos.");
            return;
        }
        System.out.println("Pedidos de " + cliente.getNombre() + ":");
        for (Pedido pedido : pedidos) {
            System.out.println(pedido.toStringCliente());
        }
    }

    /****************************************************************
     *      Métodos para eliminar un Cliente
     ***************************************************************/

    /**
     * Método para eliminar un cliente.
     */
    private void eliminarCliente() {
        System.out.println("---- ELIMINAR UN CLIENTE ----");
        Cliente cliente = seleccionarCliente();
        if (cliente == null) {
            return;
        }
        Menu.cooperativa.eliminarCliente(cliente);
        System.out.println("El cliente " + cliente.getNombre() + " ha sido eliminado con éxito.");
    }







}