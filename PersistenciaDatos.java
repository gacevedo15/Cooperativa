import java.io.*;
import java.util.ArrayList;

/**
 * Clase para la persistencia de datos en archivos.
 * Permite guardar y cargar datos de una instancia de la clase TipoCooperativa y de una lista de OfertaLogistica.
 * @author Gustavo Acevedo Alfonso
 * @version 1.0
 */
public class PersistenciaDatos {

    /**
     * Guarda los datos de la cooperativa en un archivo.
     * @param cooperativa Instancia de la clase TipoCooperativa que contiene los datos a guardar.
     * @param rutaArchivo Ruta del archivo donde se guardarán los datos.
     */
    public static void guardarDatos(TipoCooperativa cooperativa, String rutaArchivo) {
        try {
            FileOutputStream archivoSalida = new FileOutputStream(rutaArchivo);
            ObjectOutputStream objetoSalida = new ObjectOutputStream(archivoSalida);
            objetoSalida.writeObject(cooperativa);
            objetoSalida.close();
            archivoSalida.close();
            System.out.println("Los datos se han guardado correctamente en el archivo: " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar los datos en el archivo: " + rutaArchivo);
            e.printStackTrace();
        }
    }

    /**
     * Carga los datos de la cooperativa desde un archivo.
     * @param rutaArchivo Ruta del archivo desde el que se cargarán los datos.
     * @return Instancia de la clase TipoCooperativa con los datos cargados.
     */
    public static TipoCooperativa cargarDatos(String rutaArchivo) {
        TipoCooperativa cooperativa;
        try {
            FileInputStream archivoEntrada = new FileInputStream(rutaArchivo);
            ObjectInputStream objetoEntrada = new ObjectInputStream(archivoEntrada);
            cooperativa = (TipoCooperativa) objetoEntrada.readObject();
            objetoEntrada.close();
            archivoEntrada.close();
            System.out.println("Los datos se han cargado correctamente desde el archivo: " + rutaArchivo);
        } catch (Throwable e) {
            System.out.println("Error al cargar los datos desde el archivo: " + rutaArchivo + ". Se creará una nueva Cooperativa.");
            cooperativa = new TipoCooperativa();
        }
        return cooperativa;
    }

    /**
     * Guarda los datos de las ofertas logísticas en un archivo.
     * @param ofertas Lista de ofertas logísticas que se guardarán en el archivo.
     * @param rutaArchivo Ruta del archivo donde se guardarán los datos.
     */
    public static void guardarOfertas(ArrayList ofertas, String rutaArchivo) {
        try {
            FileOutputStream archivoSalida = new FileOutputStream(rutaArchivo);
            ObjectOutputStream objetoSalida = new ObjectOutputStream(archivoSalida);
            objetoSalida.writeObject(ofertas);
            objetoSalida.close();
            archivoSalida.close();
            System.out.println("Las ofertas se han guardado correctamente en el archivo: " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar las ofertas en el archivo: " + rutaArchivo);
            e.printStackTrace();
        }
    }

    /**
     * Carga los datos de las ofertas logísticas desde un archivo.
     * @param rutaArchivo Ruta del archivo desde el que se cargarán los datos.
     * @return Lista de ofertas logísticas con los datos cargados.
     */
    public static ArrayList<OfertaLogistica> cargarOfertas(String rutaArchivo) {
        ArrayList<OfertaLogistica> ofertas;
        try {
            FileInputStream archivoEntrada = new FileInputStream(rutaArchivo);
            ObjectInputStream objetoEntrada = new ObjectInputStream(archivoEntrada);
            ofertas = (ArrayList<OfertaLogistica>) objetoEntrada.readObject();
            objetoEntrada.close();
            archivoEntrada.close();
            System.out.println("Las ofertas se han cargado correctamente desde el archivo: " + rutaArchivo);
        } catch (Throwable e) {
            System.out.println("Error al cargar las ofertas desde el archivo: " + rutaArchivo + ". Se creará una nueva lista vacía de ofertas.");
            ofertas = new ArrayList<>();
        }
        return ofertas;
    }
}
