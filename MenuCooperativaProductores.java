import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * Write a description of class MenuCooperativaProductores here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MenuCooperativaProductores implements IMenu {

    /**
     * Scanner para leer la entrada del usuario.
     */
    private Scanner scanner;

    /**
     * Constructor de la clase MenuCooperativaProductos. Inicializa el Scanner.
     */
    public MenuCooperativaProductores() {
        scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("---- MENU COOPERATIVA PRODUCTORES ----");
            System.out.println("1. Crear Productor");
            System.out.println("2. Añadir Producto a un Productor");
            System.out.println("3. Ver Detalles de un Productor");
            System.out.println("4. Modificar extensión de un Producto de un Productor");
            System.out.println("5. Eliminar Producto de un Productor");
            System.out.println("6. Eliminar Productor");
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
                    crearProductor();
                    break;
                case 2:
                    anadirProductoAProductor();
                    break;
                case 3:
                    verDetallesProductor();
                    break;
                case 4:
                    modificarExtensionProductoProductor();
                    break;
                case 5:
                    eliminarProductoProductor();
                    break;
                case 6:
                    eliminarProductor();
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
     *      Métodos para crear Productores
     *********************************************/

    /**
     * Método para crear un productor.
     */
    private void crearProductor() {
        System.out.println("---- CREAR PRODUCTOR ----");
        String nombre = "";
        boolean nombreValido = false;

        // Comprobar que el nombre del productor es válido
        while (!nombreValido) {
            System.out.println("Ingrese el nombre del productor:");
            nombre = scanner.nextLine().trim();
            if (!nombre.matches("^[a-zA-Z ]{2,50}$")) {
                System.out.println("Error: el nombre debe contener solo letras y espacios, y tener entre 2 y 50 caracteres.");
            } else {
                nombreValido = true;
            }
        }

        // Comprobar si el productor ya existe en la cooperativa
        boolean productorExistente = false;
        for (Productor productor : Menu.cooperativa.getProductores()) {
            if (productor.getNombre().equalsIgnoreCase(nombre)) {
                productorExistente = true;
                break;
            }
        }

        if (productorExistente) {
            System.out.println("Error: Ya existe un productor con el nombre: " + nombre + " en la cooperativa");
        } else {
            TipoProductor tipoProductor = solicitarTipoProductor();
            Productor productor = new Productor(nombre, tipoProductor);
            Menu.cooperativa.addProductor(productor);
            System.out.println("Productor creado correctamente");
        }
    }


    /**
     * Método para solicitar el tipo de productor.
     */
    private TipoProductor solicitarTipoProductor() {
        int opcion;
        do {
            System.out.println("Seleccione el tipo de productor: ");
            mostrarTiposProductor();

            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // limpiar el buffer de entrada
            } catch (InputMismatchException e) {
                System.out.println("Error: debe ingresar un número entero");
                scanner.nextLine(); // Limpia el buffer de entrada
                opcion = -1; // Asigna un valor inválido para que vuelva a mostrar el menú
            }
            if (opcion > 0 && opcion <= TipoProductor.values().length) {
                return TipoProductor.values()[opcion-1];
            } else {
                System.out.println("Opción inválida. Por favor, intente de nuevo.");
            }
        } while (opcion != 0);
        return null;
    }

    /**
     * Método auxiliar para mostrar los tipos de productor.
     */
    private void mostrarTiposProductor() {
        for (TipoProductor tipo : TipoProductor.values()) {
            System.out.println(tipo.ordinal()+1 + ". " + tipo);
        }
    }

    /******************************************************
     *      Métodos para añadir Productos a un Productor
     ******************************************************/

    /**
     * Método para añadir un producto a un productor.
     */
    private void anadirProductoAProductor() {
        System.out.println("---- AÑADIR PRODUCTO A UN PRODUCTOR ----");
        float extension = 0;
        Producto producto = new MenuCooperativaProductos().seleccionarProducto();
        Productor productor = seleccionarProductor();

        if (producto != null && productor != null) {
            if (productor.buscarProducto(producto.getTipo())) {
                System.out.println("Error: El productor ya tiene el producto: " + producto.getTipo());
                return;
            }

            if (productor.getTipoProductor() == TipoProductor.PEQUENO_PRODUCTOR) {
                if (productor.getProductos().size() >= 5) {
                    System.out.println("Error: El productor ya tiene el máximo de productos permitidos: "+TipoCooperativa.MAX_PRODUCTOS);
                    return;
                }
                extension = solicitarExtensionDelProducto();
                if ((productor.getExtensionTotal()+extension) > TipoCooperativa.MAX_HA) {
                    System.out.println("Error: La extension total no puede ser mayor a 5");
                    return;
                }
            }

            productor.addProducto(producto,extension);
            System.out.println("Producto: "+producto.getTipo()+" añadido correctamente al productor: " + productor.getNombre());
        }
    }

    /**
     * Método para seleccionar un productor.
     */
    public Productor seleccionarProductor() {
        if (Menu.cooperativa.getProductores().isEmpty()) {
            System.out.println("No hay productores disponibles.");
            return null;
        }
        System.out.println("Seleccione el productor: ");
        for (int i = 0; i < Menu.cooperativa.getProductores().size(); i++) {
            System.out.println(i+1 + ". " + Menu.cooperativa.getProductores().get(i).getNombre());
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
        if (opcion < 0 || opcion >= Menu.cooperativa.getProductores().size()) {
            System.out.println("Opción inválida. Por favor, intente de nuevo.");
            return null;
        }
        return Menu.cooperativa.getProductores().get(opcion);
    }

    /**
     * Método auxiliar para añadir la extension que tendrá el productor de un producto.
     */
    private float solicitarExtensionDelProducto() {
        System.out.println("Ingrese la extension que tendrá: ");
        do {
            try {
                float extension = scanner.nextFloat();
                scanner.nextLine(); // limpiar el buffer de entrada
                if (extension > 0) {
                    return extension;
                } else {
                    System.out.println("Error: la extension debe ser mayor a 0");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: debe ingresar un número");
                scanner.nextLine(); // Limpia el buffer de entrada
            }
        } while (true);
    }

    /***********************************************************
     *      Métodos para ver los detalles de los productores
     ***********************************************************/

    /**
     * Método para ver los detalles de un productor.
     */
    private void verDetallesProductor() {
        System.out.println("---- VER DETALLES DE UN PRODUCTOR ----");
        Productor productor = seleccionarProductor();
        if (productor != null) {
            System.out.println(productor);
        }
    }

    /**************************************************************************
     *      Métodos para modificar extensión de un Producto de un Productor
     *************************************************************************/
    private void modificarExtensionProductoProductor() {
        System.out.println("---- MODIFICAR EXTENSION DE UN PRODUCTO DE UN PRODUCTOR ----");
        Productor productor = seleccionarProductor();
        if (productor != null) {
            Producto producto = seleccionarProducto(productor);
            System.out.println("Extension total: "+productor.calcularExtensionTotal());
            if (producto != null) {
                float extension = solicitarExtensionDelProducto();
                if (productor.getTipoProductor() == TipoProductor.PEQUENO_PRODUCTOR) {
                    if ((productor.getExtensionTotal()+extension) > TipoCooperativa.MAX_HA) {
                        System.out.println("Error: La extension total no puede ser mayor a 5");
                        return;
                    }
                }
                productor.modificarExtensionProducto(producto.getTipo(), extension);
                System.out.println("Extension modificada correctamente.");
            }
        }
    }

    /**
     * Método para seleccionar un producto de un productor.
     */
    private Producto seleccionarProducto(Productor productor) {
        if (productor.getProductos().isEmpty()) {
            System.out.println("No hay productos disponibles.");
            return null;
        }
        System.out.println("Seleccione el producto: ");
        int contador = 1;
        Iterator<Map.Entry<Producto, Float>> it = productor.getProductos().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Producto, Float> entry = it.next();
            System.out.println(contador + ". " + entry.getKey().getTipo());
            contador++;
        }

        int opcion;
        try {
            opcion = scanner.nextInt() - 1;
            scanner.nextLine(); // limpiar el buffer de entrada
        } catch (InputMismatchException e) {
            System.out.println("Por favor, ingrese un número válido.");
            scanner.next();
            return null;
        }
        if (opcion < 0 || opcion >= productor.getProductos().size()) {
            System.out.println("Opción inválida. Por favor, intente de nuevo.");
            return null;
        }
        return (Producto) productor.getProductos().keySet().toArray()[opcion];
    }

    /******************************************************
     *      Métodos para eliminar Productos de un Productor
     ******************************************************/
    private void eliminarProductoProductor() {
        System.out.println("---- ELIMINAR PRODUCTO DE UN PRODUCTOR ----");
        Productor productor = seleccionarProductor();
        if (productor != null) {
            Producto producto = seleccionarProducto(productor);
            if (producto != null) {
                productor.removeProducto(producto.getTipo());
                System.out.println("El producto: "+producto.getTipo()+" ha sido eliminado correctamente.");
            }
        }
    }

    /******************************************************
     *      Métodos para eliminar Productores
     ******************************************************/
    private void eliminarProductor() {
        System.out.println("---- ELIMINAR PRODUCTOR ----");
        Productor productor = seleccionarProductor();
        if (productor != null) {
            Menu.cooperativa.eliminarProductor(productor);
            System.out.println("El productor: " + productor.getNombre() + " ha sido eliminado correctamente.");
        }
    }


}