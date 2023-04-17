import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase que representa el menu de Resumen Anual de la Cooperativa.
 * Permite mostrar las estadísticas anuales de la cooperativa, ver las ventas
 * en un período determinado, los importes obtenidos por productores, logística y Cooperativa.
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

    /**
     * Implementación del método mostrarMenu de la interfaz IMenu.
     */
    public void mostrarMenu() {
        int opcion;
        ResumenAnual resumenAnual;
        int annoActual = -1;
        do {
            if (annoActual == -1) {
                System.out.println("---- MENU COOPERATIVA RESUMEN ANUAL ----");
                annoActual = solicitarAnno();
            } else {
                System.out.println("---- MENU COOPERATIVA RESUMEN ANUAL " + annoActual + " ----");
            }
            if (annoActual < 2000 || annoActual > 3000) {
                System.out.println("Error: el año debe estar comprendido entre 2000 y 3000");
                opcion = -1;
                continue;
            }
            resumenAnual = getResumenAnual(annoActual);
            if (resumenAnual != null){
                System.out.println("1. Ventas totales de cantidad de productos en un periodo determinado");
                System.out.println("2. Importes obtenidos por cada productor");
                System.out.println("3. Importes obtenidos por cada Logística");
                System.out.println("4. Beneficios de la cooperativa por cada producto");
                System.out.println("5. Evolución de los precios de referencias de cada producto");
                System.out.println("6. Comprobar otro año");
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
                    case 1 -> mostrarVentasTotalesCantidadProductos(annoActual, resumenAnual);
                    case 2 -> mostrarImportesObtenidosPorProductor(resumenAnual);
                    case 3 -> mostrarImportesObtenidosPorLogistica(resumenAnual);
                    case 4 -> mostrarBeneficiosCooperativaPorProducto(resumenAnual);
                    case 5 -> mostrarEvolucionPreciosReferencia(resumenAnual);
                    case 6 -> {
                        annoActual = solicitarAnno(); // Pide un nuevo año
                        opcion = -1; // Vuelve a mostrar el menú con el nuevo año
                    }
                    case 0 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("Opción inválida. Por favor, intente de nuevo.");
                }
            } else {
                System.out.println("No hay resumen anual para el año " + annoActual);
                opcion = 0;
            }


        } while (opcion != 0);
    }

    /**
     * Método para solicitar el año a consultar.
     * El año debe estar comprendido entre 2000 y 3000.
     * @return año a consultar
     */
    private int solicitarAnno() {
        int anno;

        System.out.println("Ingrese el año a consultar (2000-3000): ");
        try {
            anno = scanner.nextInt();
            scanner.nextLine(); // limpiar el buffer de entrada
        } catch (InputMismatchException e) {
            System.out.println("Error: debe ingresar un número entero");
            scanner.nextLine(); // Limpia el buffer de entrada
            anno = -1; // Asigna un valor inválido para que vuelva a mostrar el menú
        }
        return anno;
    }

    /**
     * Método que devuelve el resumen anual del año ingresado.
     * @param anno año a consultar
     * @return resumen anual del año ingresado
     */
    private ResumenAnual getResumenAnual(int anno) {
        for (ResumenAnual resumen : Menu.cooperativa.getResumenesAnuales()) {
            if (resumen.getAnno() == anno) {
                return resumen;
            }
        }
        return null;
    }

    /*---------------------------------------------------------------------------------------------------------------*
     *          Métodos para mostrar las ventas totales de cantidad de productos en un periodo determinado
     ---------------------------------------------------------------------------------------------------------------*/

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
     * @return array con las fechas de inicio y fin
     */
    private int[] solicitarFechaInicioFin(int anno) {
        int diaInicio = 0, mesInicio = 0, diaFin = 0, mesFin = 0;
        LocalDate fechaInicio, fechaFin;
        boolean esFechaCorrecta = false;

        while (!esFechaCorrecta) {
            System.out.println("Ingrese la fecha de inicio (día y mes): ");
            diaInicio = solicitarDia();
            mesInicio = solicitarMes();

            try {
                fechaInicio = LocalDate.of(anno, mesInicio, diaInicio);
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
                } else {
                    esFechaCorrecta = true;
                }
            } catch (DateTimeException e) {
                System.out.println("Error: la fecha ingresada no es válida. Inténtelo nuevamente.");
            }
        }

        // devolver un array con las fechas de inicio y fin
        return new int[]{diaInicio, mesInicio, diaFin, mesFin};
    }


    /**
     * Método auxiliar para solicitar el día.
     * @return día ingresado
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
     * @return mes ingresado
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

    /*---------------------------------------------------------------------------------*
     *          Métodos para mostrar los Importes obtenidos por cada productor
     ---------------------------------------------------------------------------------*/

    /**
     * Método para mostrar los importes obtenidos por cada productor.
     * @param resumenAnual resumen anual a consultar
     */
    private void mostrarImportesObtenidosPorProductor(ResumenAnual resumenAnual) {
        System.out.println("Importes obtenidos por cada productor");
        resumenAnual.printarImportesPorProductor();
    }

    /*---------------------------------------------------------------------------------*
     *          Métodos para mostrar los Importes obtenidos por cada Logística
     ---------------------------------------------------------------------------------*/

    /**
     * Método para mostrar los importes obtenidos por cada Logística.
     * @param resumenAnual resumen anual a consultar
     */
    private void mostrarImportesObtenidosPorLogistica(ResumenAnual resumenAnual) {
        System.out.println("Importes obtenidos por cada Logística");
        resumenAnual.printarImportesPorLogistica();
    }

    /*-----------------------------------------------------------------------------------*
     *          Métodos para mostrar los Beneficios de la cooperativa por cada producto
    -----------------------------------------------------------------------------------*/

    /**
     * Método para mostrar los beneficios de la cooperativa por cada producto.
     * @param resumenAnual resumen anual a consultar
     */
    private void mostrarBeneficiosCooperativaPorProducto(ResumenAnual resumenAnual) {
        System.out.println("Beneficios de la cooperativa por cada producto");
        resumenAnual.mostrarBeneficiosCooperativaPorProducto();
    }

    /*----------------------------------------------------------------------------------------------*
     *          Métodos para mostrar la evolución de los precios de referencias de cada producto
     ----------------------------------------------------------------------------------------------*/

    /**
     * Método para mostrar la evolución de los precios de referencias de cada producto.
     * @param resumenAnual resumen anual a consultar
     */
    private void mostrarEvolucionPreciosReferencia(ResumenAnual resumenAnual) {
        System.out.println("Evolución de los precios de referencia de cada producto");
        resumenAnual.printEvolucionPreciosReferencia();
    }

}
