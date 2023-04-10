import java.util.InputMismatchException;
import java.util.Scanner;

class MenuCooperativa implements IMenu {
    private Scanner scanner;

    public MenuCooperativa() {
        scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("---- MENU COOPERATIVA ----");
            System.out.println("1. Ver datos de la cooperativa");
            System.out.println("2. Modificar datos de la cooperativa");
            System.out.println("3. Ver ofertas logísticas");
            System.out.println("4. Modificar ofertas logísticas");
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
                    System.out.println("Mostrando datos de la cooperativa...");
                    break;
                case 2:
                    //modificarCooperativa();
                    break;
                case 3:
                    //mostrarOfertas();
                    break;
                case 4:
                    //modificarOfertas();
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