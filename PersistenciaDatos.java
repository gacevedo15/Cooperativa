import java.io.*;
import java.util.ArrayList;

public class PersistenciaDatos {

    // Guardar datos de la cooperativa
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

    // Cargar datos de la cooperativa
    public static TipoCooperativa cargarDatos(String rutaArchivo) {
        TipoCooperativa cooperativa = null;
        try {
            FileInputStream archivoEntrada = new FileInputStream(rutaArchivo);
            ObjectInputStream objetoEntrada = new ObjectInputStream(archivoEntrada);
            cooperativa = (TipoCooperativa) objetoEntrada.readObject();
            objetoEntrada.close();
            archivoEntrada.close();
            System.out.println("Los datos se han cargado correctamente desde el archivo: " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error al cargar los datos desde el archivo: " + rutaArchivo);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar los datos desde el archivo: " + rutaArchivo);
            e.printStackTrace();
        }
        return cooperativa;
    }

    // Guardar datos de ofertas logísticas
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

    // Cargar datos de ofertas logísticas
    public static OfertaLogistica[] cargarOfertas(String rutaArchivo) {
        OfertaLogistica[] ofertas = null;
        try {
            FileInputStream archivoEntrada = new FileInputStream(rutaArchivo);
            ObjectInputStream objetoEntrada = new ObjectInputStream(archivoEntrada);
            ofertas = (OfertaLogistica[]) objetoEntrada.readObject();
            objetoEntrada.close();
            archivoEntrada.close();
            System.out.println("Las ofertas se han cargado correctamente desde el archivo: " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error al cargar las ofertas desde el archivo: " + rutaArchivo);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar las ofertas desde el archivo: " + rutaArchivo);
            e.printStackTrace();
        }
        return ofertas;
    }
}
