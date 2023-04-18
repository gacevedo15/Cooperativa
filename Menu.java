import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase que representa un menú.
 * Contiene el menu principal y las opciones que llevan a los distintos menús.
 * @author Gustavo Acevedo Alfonso
 * @version 1.0
 */
public class Menu implements IMenu {

    /**
     * Scanner para leer la entrada del usuario.
     */
    private Scanner scanner;

    /**
     * Objeto de tipo IMenu que representa el menú actual.
     */
    private IMenu menuActual;

    /**
     * Objeto de tipo Cooperativa que representa la cooperativa.
     */
    public static TipoCooperativa cooperativa;

    /**
     * Lista de ofertas de logística.
     */
    public ArrayList<OfertaLogistica> ofertas;

    /**
     * Constructor de la clase Menu.
     * Inicializa el Scanner y carga los datos de la cooperativa y las ofertas logísticas.
     */
    public Menu() {
        scanner = new Scanner(System.in);
        cooperativa = PersistenciaDatos.cargarDatos("cooperativa.txt");
        ofertas = PersistenciaDatos.cargarOfertas("ofertas.txt");
    }



    /**
     * Muestra el menú principal y las opciones que llevan a los distintos menús.
     */
    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("---- MENU ----");
            System.out.println("1. Menu Cooperativa");
            System.out.println("2. Guardar datos");
            System.out.println("0. Salir");

            try {
                opcion = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error: debe ingresar un número entero");
                scanner.nextLine(); // Se limpia el buffer de entrada
                opcion = -1; // Se asigna un valor inválido para que vuelva a mostrar el menú
            }

            switch (opcion) {
                case 1 -> {
                    menuActual = new MenuCooperativa();
                    menuActual.mostrarMenu();
                }
                case 2 -> {
                    PersistenciaDatos.guardarDatos(cooperativa, "cooperativa.txt");
                    PersistenciaDatos.guardarOfertas(ofertas, "ofertas.txt");
                    System.out.println("Datos guardados correctamente.");
                }
                case 0 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción inválida. Por favor, intente de nuevo.");
            }
        } while (opcion != 0);
    }
}