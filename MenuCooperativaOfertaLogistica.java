import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase que representa el menú de la cooperativa de la gestión de las Ofertas Logística.
 * @author Gustavo Acevedo Alfonso
 * @version 1.0
 */
public class MenuCooperativaOfertaLogistica implements IMenu {

    /**
     * Scanner para leer la entrada del usuario.
     */
    private Scanner scanner;

    /**
     * Constructor de la clase MenuCooperativaOfertaLogistica que inicializa el Scanner.
     */
    public MenuCooperativaOfertaLogistica() {
        scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("---- MENU COOPERATIVA OFERTA LOGÍSTICA ----");
            System.out.println("1. Crear Oferta Logística");
            System.out.println("2. Ver ofertas logísticas");
            System.out.println("3. Eliminar Oferta Logística");
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
                    crearOfertaLogistica();
                    break;
                case 2:
                    verOfertasLogisticas();
                    break;
                case 3:
                    eliminarOfertaLogistica();
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

    /********************************************
     * Método para Crear Oferta Logística
     ********************************************/

    /**
     * Método para crear una oferta logística
     */
    private void crearOfertaLogistica(){
        System.out.println("---- CREAR OFERTA ----");
        String nombre = "";
        boolean nombreValido = false;

        // Comprobar que el nombre de la oferta es válido
        while (!nombreValido) {
            System.out.println("Ingrese el nombre de la oferta:");
            nombre = scanner.nextLine().trim();
            if (!nombre.matches("^[a-zA-Z ]{2,50}$")) {
                System.out.println("Error: el nombre debe contener solo letras y espacios, y tener entre 2 y 50 caracteres.");
            } else {
                nombreValido = true;
            }
        }

        if (!comprobarOfertaLogistica(nombre)) {
            float costeKmGranLogistica = solicitarCostePorKm("Gran Logística");
            float costeKmPequenaLogistica = solicitarCostePorKm("Pequeña Logística");
            TipoCliente tipoCliente = new MenuCooperativaClientes().seleccionarTipoCliente();
            // Ahora se debe seleccionar el tipo de oferta
            try {
                System.out.println("Seleccione el tipo de oferta:");
                System.out.println("1. Envío Estándar");
                System.out.println("2. Envío Premium");
                int tipoOferta = scanner.nextInt();
                scanner.nextLine(); // limpiar el buffer de entrada
                switch (tipoOferta) {
                    case 1:
                        OfertaLogistica ofertaLogistica = new EnvioEstandar(nombre, costeKmGranLogistica, costeKmPequenaLogistica, tipoCliente);
                        Menu.cooperativa.addOfertaLogistica(ofertaLogistica);
                        System.out.println("Oferta de tipo Envio Estandar creada con éxito.");
                        break;
                    case 2:
                        ofertaLogistica = new EnvioPremium(nombre, costeKmGranLogistica, costeKmPequenaLogistica, tipoCliente);
                        Menu.cooperativa.addOfertaLogistica(ofertaLogistica);
                        System.out.println("Oferta de tipo Envio Premium creada con éxito.");
                        break;
                    default:
                        System.out.println("Opción inválida. No se creó la oferta.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: debe ingresar un número entero");
                scanner.nextLine(); // Limpia el buffer de entrada
            }
        }else {
            System.out.println("Error: Ya existe una oferta con ese nombre.");
        }
    }


    /**
     * Método para comprobar si la oferta ya existe
     */
    private boolean comprobarOfertaLogistica(String nombre) {
        ArrayList<OfertaLogistica> ofertasLogisticas = Menu.cooperativa.getOfertasLogisticas();
        for (OfertaLogistica ofertaLogistica : ofertasLogisticas) {
            if (ofertaLogistica.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método para solicitar el coste por Km
     */
    private float solicitarCostePorKm(String tipoLogistica) {
        float costePorKm = 0;
        boolean costeKmValido = false;
        while (!costeKmValido) {
            try {
                System.out.println("Ingrese el coste por km de " + tipoLogistica + ":");
                costePorKm = scanner.nextFloat();
                scanner.nextLine();
                costeKmValido = true;
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número decimal válido.");
                scanner.nextLine();
            }
        }
        return costePorKm;
    }

    /********************************************
     * Método para Ver Ofertas Logísticas
     ********************************************/
    private void verOfertasLogisticas(){
        System.out.println("---- VER OFERTAS LOGÍSTICAS ----");
        ArrayList<OfertaLogistica> ofertasLogisticas = Menu.cooperativa.getOfertasLogisticas();
        if (ofertasLogisticas.size() > 0) {
            for (OfertaLogistica ofertaLogistica : ofertasLogisticas) {
                System.out.println(ofertaLogistica.toString());
            }
        } else {
            System.out.println("No hay ofertas disponibles.");
        }
    }

    /********************************************
     * Método para Eliminar Oferta Logística
     ********************************************/

    /**
     * Método para eliminar una oferta logística
     */
    private void eliminarOfertaLogistica() {
        System.out.println("---- ELIMINAR OFERTA LOGÍSTICA ----");
        OfertaLogistica ofertaLogistica = seleccionarOfertaLogistica();
        if (ofertaLogistica != null) {
            Menu.cooperativa.eliminarOfertaLogistica(ofertaLogistica);
            System.out.println("Oferta logística eliminada con éxito.");
        }
    }


    /**
     * Método para seleccionar una oferta logística
     */
    private OfertaLogistica seleccionarOfertaLogistica() {
        ArrayList<OfertaLogistica> ofertasLogisticas = Menu.cooperativa.getOfertasLogisticas();

        if (ofertasLogisticas.isEmpty()) {
            System.out.println("No hay ofertas disponibles.");
            return null;
        }

        System.out.println("Seleccione una oferta logística:");

        // Mostrar las ofertas logísticas
        for (int i = 0; i < ofertasLogisticas.size(); i++) {
            System.out.println((i + 1) + ". " + ofertasLogisticas.get(i).getNombre());
        }

        // Solicitar la selección de una oferta logística
        int opcion = 0;
        boolean opcionValida = false;
        while (!opcionValida) {
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
                if (opcion > 0 && opcion <= ofertasLogisticas.size()) {
                    opcionValida = true;
                } else {
                    System.out.println("Error: Debe ingresar un número entre 1 y " + ofertasLogisticas.size());
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número entero.");
                scanner.nextLine();
            }
        }
        return ofertasLogisticas.get(opcion - 1);
    }





}