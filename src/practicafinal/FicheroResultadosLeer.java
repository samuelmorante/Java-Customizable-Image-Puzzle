/*
 * Clase FicheroResultadosLeer fichero de entrada de resultados
 */
package practicafinal;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Samuel Morante
 */
class FicheroResultadosLeer {

    /**
     * Los atributos de la clase son el flujo de entrada de ficheros y de objetos
     * el nombre es la cadena de caracteres del nombre del fichero
     */
    private final FileInputStream fis;
    private final ObjectInputStream f;
    private String nombre = "";
    private List<Partida> listaResultados;

    /**
     * Constructor del fichero con el nombre del fichero
     *
     * @param nombreFichero
     */
    public FicheroResultadosLeer(String nombreFichero) throws FileNotFoundException, IOException {
        fis = new FileInputStream(nombreFichero);
        f = new ObjectInputStream(fis);
        nombre = nombreFichero;
         listaResultados = new ArrayList<>();
    }

    /**
     * Convierte los datos del fichero a una cadena de caracteres para mostrar la
     * información que contiene
     *
     * @return
     */
    @Override
    public String toString() {
        String s = "";
        try {
            Partida res = (Partida) f.readObject();
            while (true) {
                s += res + "\n";
                res = (Partida) f.readObject();
            }
        } catch (EOFException ex) {
            //System.out.println("ERROR d'E/S llegint al fitxer de jugadors");
        } catch (ClassNotFoundException ex) {
            System.out.println("ERROR de classe llegint fitxer de jugadors");
        } catch (IOException ex) {
            Logger.getLogger(FicheroResultadosLeer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
    
    //Lee los objetos de la lista resultados
    public List<Partida> leerObjetosLista() {
        try {
            listaResultados.clear();
            while (true) {
                try {
                    Partida resultado = (Partida) f.readObject();
                    listaResultados.add(resultado);
                } catch (EOFException ex) {
                    break;
                } catch (ClassNotFoundException ex) {
                    System.out.println("ERROR: Clase no encontrada al leer el archivo");
                    break;
                }
            }
        } catch (IOException ex) {
            System.out.println("ERROR de E/S al leer el archivo");
        } finally {
            if (f != null) {
                try {
                    f.close();
                } catch (IOException ex) {
                    System.out.println("ERROR al cerrar el archivo");
                }
            }
        }
        return listaResultados;
    }

    /**
     * Cierra el fichero
     */
    public void close() throws IOException {
        f.close();
    }
    //Lee los resultados dentro del fichero
    public Partida leer() {
        Partida res = null;
        try {
            res = (Partida) f.readObject();
        } catch (IOException ex) {
            System.out.println("ERROR d'E/S llegint al fitxer de jugadors");
        } catch (ClassNotFoundException ex) {
            System.out.println("ERROR de classe llegint fitxer de jugadors");
        }
        return res;
    }
}