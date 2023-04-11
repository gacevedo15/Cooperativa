import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase que representa el menu de Resumen Anual de la Cooperativa.
 * @author Gustavo Acevedo Alfonso
 * @version 1.0
 */
public class MenuCooperativaResumenAnual implements IMenu {

    /**
     * Scanner para leer la entrada del usuario.
     */
    private Scanner scanner;

    /**
     * Constructor de la clase MenuCooperativaProductos. Inicializa el Scanner.
     */
    public MenuCooperativaResumenAnual() {
        scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion,anno;
        ResumenAnual resumenAnual;
        do {
            System.out.println("---- MENU COOPERATIVA RESUMEN ANUAL ----");
            anno = solicitarAnno();
            resumenAnual = getResumenAnual(anno);
            if (resumenAnual != null){
                System.out.println("1. Ventas totales de cantidad de productos en un periodo determinado");
                System.out.println("2. Importes obtenidos por cada productor");
                System.out.println("3. Importes obtenidos por cada Logística");
                System.out.println("4. Beneficios de la cooperativa por cada producto");
                System.out.println("5. Evolución de los precios de referencias de cada producto");
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
                        System.out.println("Ventas totales de cantidad de productos en un periodo determinado");
                        mostrarVentasTotalesCantidadProductos(anno, resumenAnual);
                        break;
                    case 2:
                        System.out.println("Importes obtenidos por cada productor");

                        break;
                    case 3:
                        System.out.println("Importes obtenidos por cada Logística");

                        break;
                    case 4:
                        System.out.println("Beneficios de la cooperativa por cada producto");

                        break;
                    case 5:
                        System.out.println("Evolución de los precios de referencias de cada producto");

                        break;
                    case 0:
                        System.out.println("Volviendo al menú principal...");
                        break;
                    default:
                        System.out.println("Opción inválida. Por favor, intente de nuevo.");
                        break;
                }
            } else {
                System.out.println("No hay resumen anual para el año " + anno);
                opcion = 0;
            }


        } while (opcion != 0);
    }

    /**
     * Método para solicitar el año a consultar.
     * El año debe estar comprendido entre 2000 y 3000.
     */
    private int solicitarAnno() {
        int anno;
        do {
            System.out.println("Ingrese el año a consultar (2000-3000): ");
            try {
                anno = scanner.nextInt();
                scanner.nextLine(); // limpiar el buffer de entrada
            } catch (InputMismatchException e) {
                System.out.println("Error: debe ingresar un número entero");
                scanner.nextLine(); // Limpia el buffer de entrada
                anno = -1; // Asigna un valor inválido para que vuelva a mostrar el menú
            }
            if (anno < 2000 || anno > 3000) {
                System.out.println("Error: el año debe estar comprendido entre 2000 y 3000");
            }
        } while (anno < 2000 || anno > 3000);
        return anno;
    }

    /**
     * Método que devuelve el resumen anual del año ingresado.
     * @param anno año a consultar
     */
    private ResumenAnual getResumenAnual(int anno) {
        for (ResumenAnual resumen : Menu.cooperativa.getResumenesAnuales()) {
            if (resumen.getAnno() == anno) {
                return resumen;
            }
        }
        return null;
    }

    /**********************************************************************************************************
     *          Métodos para mostrar las ventas totales de cantidad de productos en un periodo determinado
     **********************************************************************************************************/

    /**
     * Método para mostrar las ventas totales de cantidad de productos en un periodo determinado.
     * Primero se solicitan las fechas de inicio y fin del periodo a consultar.
     * luego si son correctas, se muestra el resultado.
     * @param anno año a consultar
     */
    private void mostrarVentasTotalesCantidadProductos(int anno, ResumenAnual resumenAnual) {
        System.out.println("Ventas totales de cantidad de productos en un periodo determinado");

        // solicitar las fechas de inicio y fin
        int[] fechas = solicitarFechaInicioFin(anno);
        int diaInicio = fechas[0];
        int mesInicio = fechas[1];
        int diaFin = fechas[2];
        int mesFin = fechas[3];

        // Mostrar el resultado
        System.out.println("Ventas totales de cantidad de productos en el periodo " + diaInicio + "/" + mesInicio + " - " + diaFin + "/" + mesFin);
        resumenAnual.printarVentasPorProducto(LocalDate.of(anno, mesInicio, diaInicio), LocalDate.of(anno, mesFin, diaFin));

    }


    /**
     * Método auxiliar para solicitar la fecha de inicio y fin del periodo a consultar (día y mes).
     * @param anno año a consultar
     */
    private int[] solicitarFechaInicioFin(int anno) {
        int diaInicio = 0, mesInicio = 0, diaFin = 0, mesFin = 0;
        LocalDate fechaInicio, fechaFin;
        boolean esFechaCorrecta = false;

        while (!esFechaCorrecta) {
            System.out.println("Ingrese la fecha de inicio (día y mes): ");
            diaInicio = solicitarDia();
            mesInicio = solicitarMes();
        /*
        Ahora hay que comprobar si la fecha es correcta.
        Si no lo es, se muestra mensaje de error y se vuelve a solicitar la fecha.
        */
            try {
                fechaInicio = LocalDate.of(anno, mesInicio, diaInicio);
                esFechaCorrecta = true;
            } catch (DateTimeException e) {
                System.out.println("Error: la fecha ingresada no es válida. Inténtelo nuevamente.");
                continue;
            }

            System.out.println("Ingrese la fecha de fin (día y mes): ");
            diaFin = solicitarDia();
            mesFin = solicitarMes();

            try {
                fechaFin = LocalDate.of(anno, mesFin, diaFin);
                if (fechaInicio.isAfter(fechaFin)) {
                    System.out.println("Error: la fecha de inicio debe ser anterior a la fecha de fin. Inténtelo nuevamente.");
                    esFechaCorrecta = false;
                } else {
                    esFechaCorrecta = true;
                }
            } catch (DateTimeException e) {
                System.out.println("Error: la fecha ingresada no es válida. Inténtelo nuevamente.");
                esFechaCorrecta = false;
            }
        }

        // devolver un array con las fechas de inicio y fin
        int[] fechas = {diaInicio, mesInicio, diaFin, mesFin};
        return fechas;
    }


    /**
     * Método auxiliar para solicitar el día.
     */
    private int solicitarDia() {
        int dia;
        do {
            System.out.println("Ingrese el día (1-31): ");
            try {
                dia = scanner.nextInt();
                scanner.nextLine(); // limpiar el buffer de entrada
            } catch (InputMismatchException e) {
                System.out.println("Error: debe ingresar un número entero");
                scanner.nextLine(); // Limpia el buffer de entrada
                dia = -1; // Asigna un valor inválido para que vuelva a mostrar el menú
            }
            if (dia < 1 || dia > 31) {
                System.out.println("Error: el día debe estar comprendido entre 1 y 31");
            }
        } while (dia < 1 || dia > 31);
        return dia;
    }

    /**
     * Método auxiliar para solicitar el mes.
     */
    private int solicitarMes() {
        int mes;
        do {
            System.out.println("Ingrese el mes (1-12): ");
            try {
                mes = scanner.nextInt();
                scanner.nextLine(); // limpiar el buffer de entrada
            } catch (InputMismatchException e) {
                System.out.println("Error: debe ingresar un número entero");
                scanner.nextLine(); // Limpia el buffer de entrada
                mes = -1; // Asigna un valor inválido para que vuelva a mostrar el menú
            }
            if (mes < 1 || mes > 12) {
                System.out.println("Error: el mes debe estar comprendido entre 1 y 12");
            }
        } while (mes < 1 || mes > 12);
        return mes;
    }

    /**
     * Método auxiliar para comprobar que la fecha ingresada es válida.
     */
    private boolean esFechaCorrecta(int anno, int dia, int mes) {
        try {
            LocalDate.of(anno, mes, dia);
            return true;
        } catch (DateTimeException e) {
            System.out.println("Error: la fecha ingresada no es válida");
            return false;
        }
    }






}
