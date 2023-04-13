import java.util.InputMismatchException;
import java.util.Scanner;

class MenuCooperativa implements IMenu {
    private Scanner scanner;

    /**
     * Objeto de tipo IMenu que representa el menú actual.
     */
    private IMenu menuActual;

    public MenuCooperativa() {
        scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("---- MENU COOPERATIVA ----");
            System.out.println("1. PRODUCTOS");
            System.out.println("2. PRODUCTORES");
            System.out.println("3. CLIENTES");
            System.out.println("4. OFERTA LOGÍSTICA");
            System.out.println("5. PEDIDOS");
            System.out.println("6. RESUMEN ANUAL");
            System.out.println("0. Volver al menú principal");

            try {
                opcion = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error: debe ingresar un número entero");
                scanner.nextLine(); // Limpia el buffer de entrada
                opcion = -1; // Asigna un valor inválido para que vuelva a mostrar el menú
            }
            switch (opcion) {
                case 1:
                    menuActual = new MenuCooperativaProductos();
                    menuActual.mostrarMenu();
                    break;
                case 2:
                    menuActual = new MenuCooperativaProductores();
                    menuActual.mostrarMenu();
                    break;
                case 3:
                    menuActual = new MenuCooperativaClientes();
                    menuActual.mostrarMenu();
                    break;
                case 4:
                    menuActual = new MenuCooperativaOfertaLogistica();
                    menuActual.mostrarMenu();
                    break;
                case 5:
                    menuActual = new MenuCooperativaPedidos();
                    menuActual.mostrarMenu();
                    break;
                case 6:
                    menuActual = new MenuCooperativaResumenAnual();
                    menuActual.mostrarMenu();
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
}