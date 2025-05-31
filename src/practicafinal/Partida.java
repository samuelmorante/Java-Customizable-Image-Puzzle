/*
 * Clase Partida los atributos de la clase son el nombre del jugador (una cadena
 * de caracteres), la fecha (otra cadena de caracteres) y un entero que indica
 * los puntos que ha obtenido
 */
package practicafinal;

import java.io.Serializable;

/**
 *
 * @author Samuel Morante
 */
public class Partida implements Serializable {
    
    private String nombre;
    private String fecha;
    private int puntos;

    /**
     * Constructor por defecto, el objeto se inicializa con el valor de los
     * parámetros
     *
     * @param nombre
     * @param fecha
     * @param puntos
     */
    public Partida(String nombre, String fecha, int puntos) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.puntos = puntos;
    }

    Partida() {
        this.nombre = "";
        this.fecha = "";
        this.puntos = 0;
    }

    /**
     * Devuelve la fecha del resultado
     *
     * @return
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Devuelve el nombre de puntos que tiene el jugador
     *
     * @return
     */
    public int getPuntos() {
        return puntos;
    }

    @Override
    public String toString() {
        return "Jugador{" + "nombre=" + nombre + ","
                + " fecha=" + fecha + ","
                + " puntos=" + puntos + '}';
    }



    public String getNombre() {
        return nombre;
    }

    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    
}
