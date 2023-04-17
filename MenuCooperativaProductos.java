import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase que representa el menú de la cooperativa de productos.
 * Permite crear, eliminar, actualizar y ver los productos disponibles.
 * @author Gustavo Acevedo Alfonso
 * @version 1.0
 */
public class MenuCooperativaProductos implements IMenu {

    /**
     * Scanner para leer la entrada del usuario.
     */
    private Scanner scanner;

    /**
     * Constructor de la clase MenuCooperativaProductos. Inicializa el Scanner.
     */
    public MenuCooperativaProductos() {
        scanner = new Scanner(System.in);
    }

    /**
     * Implementación del método mostrarMenu de la interfaz IMenu.
     */
    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("---- MENU COOPERATIVA PRODUCTOS ----");
            System.out.println("1. Crear producto");
            System.out.println("2. Ver productos disponibles");
            System.out.println("3. Ver detalles de un producto");
            System.out.println("4. Eliminar producto");
            System.out.println("5. Actualizar precio de un producto");
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
                case 1 -> {
                    System.out.println("Crear producto...");
                    crearProducto();
                }
                case 2 -> {
                    System.out.println("Ver productos disponibles...");
                    verProductosDisponibles();
                }
                case 3 -> {
                    System.out.println("Ver detalles de un producto...");
                    verDetallesProducto();
                }
                case 4 -> {
                    System.out.println("Eliminar producto...");
                    eliminarProducto();
                }
                case 5 -> {
                    System.out.println("Actualizar precio de un producto...");
                    actualizarPrecioProducto();
                }
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida. Por favor, intente de nuevo.");
            }
        } while (opcion != 0);
    }

    /*---------------------------------------------*
     *          Métodos para crear un producto
     ---------------------------------------------*/

    /**
     * Método para crear un producto.
     */
    public void crearProducto(){
        TipoProducto tipoProducto = seleccionarTipoProducto();
        if (tipoProducto == null) {
            return;
        }

        if (existeProductoConTipo(tipoProducto)) {
            System.out.println("Ya existe un producto del tipo " + tipoProducto + ".");
            return;
        }

        float rendimientoPorHa = solicitarRendimientoPorHa();
        float valorReferenciaPorKg = solicitarValorReferenciaPorKg();
        boolean esPerecedero = preguntarSiEsPerecedero();
        Producto producto = new Producto(tipoProducto, rendimientoPorHa, valorReferenciaPorKg, esPerecedero);

        Menu.cooperativa.addProducto(producto);
        System.out.println("   Producto creado:");
        System.out.println(producto.toStringSencillo());
    }

    /**
     * Método para seleccionar el tipo de producto.
     * @return El tipo de producto seleccionado.
     */
    private TipoProducto seleccionarTipoProducto() {
        System.out.println("Seleccione el tipo de producto: ");
        for (TipoProducto tipo : TipoProducto.values()) {
            System.out.println(tipo.ordinal()+1 + ". " + tipo);
        }
        int opcion;
        try {
            opcion = scanner.nextInt()-1;
            scanner.nextLine(); // limpiar el buffer de entrada
        } catch (InputMismatchException e) {
            System.out.println("Por favor, ingrese un número válido.");
            scanner.next();
            return null;
        }
        if (opcion < 0 || opcion >= TipoProducto.values().length) {
            System.out.println("Opción inválida. Por favor, intente de nuevo.");
            return null;
        }
        return TipoProducto.values()[opcion];
    }

    /**
     * Método para verificar si existe un producto con el tipo indicado.
     * @param tipo El tipo de producto a verificar.
     * @return true si existe un producto con el tipo indicado, false en caso contrario.
     */
    private boolean existeProductoConTipo(TipoProducto tipo) {
        try {
            for (Producto producto : Menu.cooperativa.getProductos()) {
                if (producto.getTipo().equals(tipo)) {
                    return true;
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Error: la lista de productos de la cooperativa es nula.");
        }
        return false;
    }

    /**
     * Método para solicitar el rendimiento por Ha.
     * @return El rendimiento por Ha ingresado por el usuario.
     */
    private float solicitarRendimientoPorHa() {
        float rendimientoPorHa;
        do {
            try {
                System.out.println("Ingrese el rendimiento por Ha: ");
                rendimientoPorHa = scanner.nextFloat();
                scanner.nextLine(); // limpiar el buffer de entrada
                if (rendimientoPorHa <= 0) {
                    System.out.println("El rendimiento por Ha debe ser mayor a 0");
                } else {
                    return rendimientoPorHa;
                }
            } catch (InputMismatchException e) {
                System.out.println("Debe ingresar un número válido");
                scanner.nextLine(); // limpiar el buffer de entrada
            }
        } while (true);
    }

    /**
     * Método para solicitar el valor de referencia por Kg.
     * @return El valor de referencia por Kg ingresado por el usuario.
     */
    private float solicitarValorReferenciaPorKg() {
        float valorReferenciaPorKg = 0;
        boolean valorValido = false;
        while (!valorValido) {
            try {
                System.out.println("Ingrese el valor de referencia por Kg (Utilice `,´ para los decimales): ");
                valorReferenciaPorKg = scanner.nextFloat();
                scanner.nextLine(); // limpiar el buffer de entrada
                if (valorReferenciaPorKg <= 0) {
                    throw new Exception("El valor de referencia por Kg debe ser mayor a 0");
                }
                valorValido = true;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // para limpiar el buffer del scanner
            }
        }
        return valorReferenciaPorKg;
    }

    /**
     * Método para preguntar si el producto es perecedero.
     * @return true si el producto es perecedero, false en caso contrario.
     */
    private boolean preguntarSiEsPerecedero() {
        boolean esPerecedero = false;
        boolean respuestaValida = false;
        do {
            System.out.println("¿Es Perecedero? (Si/No): ");
            String respuesta = scanner.next().toLowerCase(); // Leer solo la siguiente palabra
            try {
                if (respuesta.equals("si")) {
                    esPerecedero = true;
                    respuestaValida = true;
                } else if (respuesta.equals("no")) {
                    respuestaValida = true;
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Respuesta inválida. Por favor, intente de nuevo.");
            }
            scanner.nextLine(); // limpiar el buffer de entrada
        } while (!respuestaValida);
        return esPerecedero;
    }

    /*---------------------------------------------------*
     *      Métodos para ver los productos disponibles.
     ---------------------------------------------------*/

    /**
     * Método para ver los productos disponibles.
     */
    public void verProductosDisponibles() {
        System.out.println("Productos disponibles:");
        for (Producto producto : Menu.cooperativa.getProductos()) {
            System.out.println(producto.toStringSencillo());
        }
    }

    /*---------------------------------------------------*
     *      Métodos para ver los detalles de un producto.
     ---------------------------------------------------*/

    /**
     * Método para ver los detalles de un producto.
     */
    public void verDetallesProducto() {
        Producto producto = seleccionarProducto();
        if (producto == null) {
            return;
        }
        System.out.println(producto.toStringDetallado());
    }

    /**
     * Método para seleccionar un producto.
     * Si no hay productos disponibles, se muestra un mensaje y se retorna null.
     * @return El producto seleccionado.
     */
    public Producto seleccionarProducto() {
        if (Menu.cooperativa.getProductos().isEmpty()) {
            System.out.println("No hay productos disponibles.");
            return null;
        }
        System.out.println("Seleccione el producto: ");
        for (int i = 0; i < Menu.cooperativa.getProductos().size(); i++) {
            System.out.println(i+1 + ". " + Menu.cooperativa.getProductos().get(i).getTipo());
        }
        int opcion;
        try {
            opcion = scanner.nextInt()-1;
            scanner.nextLine(); // limpiar el buffer de entrada
        } catch (InputMismatchException e) {
            System.out.println("Por favor, ingrese un número válido.");
            scanner.next();
            return null;
        }
        if (opcion < 0 || opcion >= Menu.cooperativa.getProductos().size()) {
            System.out.println("Opción inválida. Por favor, intente de nuevo.");
            return null;
        }
        return Menu.cooperativa.getProductos().get(opcion);
    }

    /*---------------------------------------------------*
     *          Métodos para eliminar un producto.
     ---------------------------------------------------*/

    /**
     * Método para eliminar un producto.
     */
    public void eliminarProducto() {
        Producto producto = seleccionarProducto();
        if (producto == null) {
            return;
        }
        Menu.cooperativa.eliminarProducto(producto);
        System.out.println("Producto "+producto.getTipo()+" eliminado con éxito.");
    }

    /*-------------------------------------------------------------*
     *          Métodos para Actualizar precio de un producto.
     -------------------------------------------------------------*/

    /**
     * Método para actualizar el precio de un producto.
     */
    public void actualizarPrecioProducto() {
        Producto producto = seleccionarProducto();
        if (producto == null) {
            return;
        }
        float nuevoPrecio = solicitarNuevoPrecio();
        LocalDate fechaActualizacion = LocalDate.now();
        Menu.cooperativa.actualizarPrecioProducto(producto.getTipo(), nuevoPrecio,fechaActualizacion);
        System.out.println("El Precio del producto "+producto.getTipo()+" ha sido actualizado con éxito a" + nuevoPrecio + " €/Kg");
    }

    /**
     * Método para solicitar el nuevo precio de un producto.
     * @return El nuevo precio ingresado por el usuario.
     */
    private float solicitarNuevoPrecio() {
        float nuevoPrecio = 0;
        boolean valorValido = false;
        while (!valorValido) {
            try {
                System.out.println("Ingrese el nuevo precio (Utilice `,´ para los decimales): ");
                nuevoPrecio = scanner.nextFloat();
                scanner.nextLine(); // limpiar el buffer de entrada
                if (nuevoPrecio <= 0) {
                    throw new Exception("El precio debe ser mayor a 0");
                }
                valorValido = true;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // para limpiar el buffer del scanner
            }
        }
        return nuevoPrecio;
    }

}
