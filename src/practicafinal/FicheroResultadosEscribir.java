/*
 * Clase FicheroResultadosEscribir fichero de salida de resultados
 */
package practicafinal;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 *
 * @author Samuel Morante
 */
public class FicheroResultadosEscribir {

    /**
     * Los atributos son el flujo de salida del fichero y los de objetos
     */
    private FileOutputStream fos;
    private ObjectOutputStream f;

    /**
     * Constructor del fichero a partir de la cadena con el nombre del fichero si
     * se producen devuelve al padre
     *
     * @param nombreFichero
     * @throws java.io.FileNotFoundException
     */
    public FicheroResultadosEscribir(String nombreFichero) throws FileNotFoundException, IOException {
        fos = new FileOutputStream(nombreFichero);
        f = new ObjectOutputStream(fos);
    }

    /**
     * Escribe un objeto Resultado al fichero
     *
     * @param r
     * @throws java.io.IOException
     */
    public void write(Partida r) throws IOException {
        f.writeObject(r);
    }
    //Escribe en la lista resultado
    public void writeLista(List<Partida> listaResultados) throws IOException {
        for (Partida resultado : listaResultados) {
        f.writeObject(resultado);
    }
}

    /**
     * Cierra el fichero
     * @throws java.io.IOException
     */
    public void close() throws IOException {
        f.close();
    }
}
