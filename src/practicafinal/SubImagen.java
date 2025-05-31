/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicafinal;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 *
 * @author Samuel Morante
 */
public class SubImagen {

    private BufferedImage imagen;
    private int posicionX;
    private int posicionY;
    private int ancho;
    private int alto;
    private int orden;
    private boolean hayError = false;

    public SubImagen(BufferedImage imagen, int posicionX, int posicionY, int ancho, int alto, int orden) {
        this.imagen = imagen;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.ancho = ancho;
        this.alto = alto;
        this.orden = orden;
    }

    public BufferedImage getImagen() {
        return imagen;
    }

    public int getOrden() {
        return orden;
    }

    public boolean getHayError() {
        return hayError;
    }

    public int getPosicionX() {
        return posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

//--------------Metodo para cortar una imagen en varios trozos------------------
    public static List<SubImagen> cortarImagen(int numCols, int numFilas, String imagenPuzzle) {
        List<SubImagen> listaSubImagenes = new ArrayList<>();

        try {
            //Creamos un ImageIO de la imagen aleatoria y nos quedamos con su ancho y alto
            BufferedImage imagenAleatoria = ImageIO.read(new File(imagenPuzzle));

            int imagenAncho = imagenAleatoria.getWidth();
            int imagenAlto = imagenAleatoria.getHeight();

            //Saber cuanto tenemos que separar por ancho y por alto
            int separarAncho = imagenAncho / numCols;
            int separarAlto = imagenAlto / numFilas;

            //Bucle para obtener subimagenes, es decir, para cortar la imagen con los datos recogidos previamente
            for (int i = 0; i < numFilas * numCols; i++) {
                int x = (i % numCols) * separarAncho;
                int y = (i / numCols) * separarAlto;
                BufferedImage subimagen = imagenAleatoria.getSubimage(x, y, separarAncho, separarAlto);
                int orden = i;
                //Crear una instancia de SubImagen y guardarla
                SubImagen subImagen = new SubImagen(subimagen, x, y, separarAncho, separarAlto, orden);
                listaSubImagenes.add(subImagen);

            }
            return listaSubImagenes;
        } catch (IOException e) {
            System.out.println("Error metodo cortarImagen");
            return null;
        }

    }
//------------------------------------------------------------------------------

//---------Seleccionar una imagen aleatoria-------------------------------------
    public static String seleccionarImagen() {
        try {
            String[] nombreImagenes;

            String rutaImagen;
            //Guardamos la ruta de la imagen
            String ruta = seleccionarDirectorio.getNombreDirectorio();
            //Creacion File con el directorio
            File carpetaImagenes = new File(ruta);
            //Guardamos el array con el contenido del directorio
            nombreImagenes = carpetaImagenes.list();
            //Creamos un número aleatorio y seleccionará uno de la lista
            Random random = new Random();
            int numeroAleatorio = random.nextInt(nombreImagenes.length);
            String nombreImagen = nombreImagenes[numeroAleatorio];
            //Para crear bien la ruta. Pues si seleccionamos un directorio será ruta absoluta y no relativa.
            if (ruta.equals("imagenes")) {
                rutaImagen = ruta + "/" + nombreImagen;
            } else {
                rutaImagen = ruta + "\\" + nombreImagen;
            }

            return rutaImagen;
        } catch (IllegalArgumentException e) {
            System.err.print("Error, no hay imágenes");

            return null;
        }
    }
//------------------------------------------------------------------------------

}
