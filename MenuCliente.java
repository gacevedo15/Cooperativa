import java.util.InputMismatchException;
import java.util.Scanner;

class MenuCliente implements IMenu {
    private Scanner scanner;

    public MenuCliente() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("---- MENU CLIENTE ----");
            System.out.println("1. Ver lista de ofertas");
            System.out.println("2. Realizar pedido");
            System.out.println("3. Ver mis pedidos");
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
                    System.out.println("--- LISTA DE OFERTAS ---");
                    /*for (OfertaLogistica oferta : ofertas) {
                        System.out.println(oferta);
                    }*/
                    break;
                case 2:
                    System.out.println("--- REALIZAR PEDIDO ---");
                    // Lógica para realizar un pedido
                    break;
                case 3:
                    System.out.println("--- MIS PEDIDOS ---");
                    // Lógica para mostrar los pedidos del cliente
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

    private void mostrarProductos() {
        // Aquí va el código para mostrar los productos disponibles
    }
}