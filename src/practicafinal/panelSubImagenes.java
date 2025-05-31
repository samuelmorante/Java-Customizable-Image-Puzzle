/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicafinal;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author Samuel Morante
 */
public class panelSubImagenes {
    
    private String rutaImagen;
    //Para instanciar la clase
    public panelSubImagenes(){
        
    }
    //Para obtener la ruta de la imagen
    public String getRutaImagen(){
        return rutaImagen;
    }
    
    //Crea una lista con las subimagenes ordenadas (de 0 a x)
    private List<SubImagen> listaConSubImagenesOrdenadas(int numCols, int numFilas){
        //Guarda la ruta
        rutaImagen = SubImagen.seleccionarImagen();
        //Crea la lista
        List<SubImagen> lista = SubImagen.cortarImagen(numCols, numFilas, rutaImagen);
        int i = 0;
        //Añadir el orden de la lista
        for  (SubImagen subImagen : lista){
            int orden = i;
            subImagen.setOrden(orden);
            i++;
        }
        
        return lista;
        
    }
    

    //Crear lista con las subimagenes de forma aleatoria
    public List<SubImagen> listaConSubImagenesAleatorias(int numCols, int numFilas){
        //Crea la lista ordenada
        List<SubImagen> listaOriginal = listaConSubImagenesOrdenadas(numCols,numFilas);
        
        //Creamos listaAleatoria 
        List<SubImagen> listaAleatoria = new ArrayList<>(listaOriginal);
        
        
        Random random = new Random();
        while(!listaOriginal.isEmpty()){
            //Escoge un número aleatorio
            int numeroAleatorio = random.nextInt(listaOriginal.size());
            //Guardamos la subimagen
            SubImagen elementoAleatorio = listaOriginal.get(numeroAleatorio);
            //Eliminamos el elemento de la listaOriginal para poder hacer el bucle y que no se repita imagenes en la otra lista
            listaOriginal.remove(numeroAleatorio);
            //Eliminamos el elemento correspondiente al mismo índice de la listaAleatoria
            listaAleatoria.remove(numeroAleatorio);
            //Añade el elemento a la listaAleatoria
            listaAleatoria.add(elementoAleatorio);
        }
        
        return listaAleatoria;
        
    }
    
    //Método para crear la lista con ImageIcon para poder ponerlo en los botones
    public List<ImageIcon> listaConImagenesParaBotones(List<SubImagen> lista){
        try{
        List <SubImagen> listaSubImagenes = lista;
        
        List <ImageIcon> listaImageIcons = new ArrayList<>();
        //Bucle para ir conviertiendo las subimagenes en imageIcon recorriendo la lista y añadiendolo en listaImageIcon. 
        for (SubImagen subImagen : listaSubImagenes){
            BufferedImage imagen = subImagen.getImagen();
            ImageIcon imageIcon = new ImageIcon(imagen);
            int orden = subImagen.getOrden()+1;
            imageIcon.setDescription(""+orden);
            listaImageIcons.add(imageIcon);
            
        }
        return listaImageIcons;
        }catch (NullPointerException e){
            System.err.print("Error");
            return null;
        }
    }
    
     
    
    
    
    
    
    
    
}
