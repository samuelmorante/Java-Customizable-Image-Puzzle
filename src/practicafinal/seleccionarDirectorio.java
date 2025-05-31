/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicafinal;

import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author Samuel Morante
 */
public class seleccionarDirectorio {
    
   
    private static String nombreDirectorio = "imagenes";
    
     public seleccionarDirectorio(){
        
    }
     
    
     //Devuelve el nombre del directorio
     public static String getNombreDirectorio(){
         return nombreDirectorio;
     }
     
//-----------------Seleccionar un directorio------------------------------------
        public static String seleccionarDirectorio(JFrame ventana, int numFilas, int numCols) {
            
        JFileChooser fileChooser = new JFileChooser();
        SubImagen.seleccionarImagen();
        //Comando para que solo permita seleccionar directorios
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int option = fileChooser.showOpenDialog(ventana);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedDirectory = fileChooser.getSelectedFile();
            //Guardamos la dirección absouluta en nombreDirectorio
            nombreDirectorio = selectedDirectory.getAbsolutePath();
            
        } else {
            System.out.println("Selección de directorio cancelada por el usuario.");
        }
        
        return nombreDirectorio;
    }
//------------------------------------------------------------------------------
        
        
    
}
