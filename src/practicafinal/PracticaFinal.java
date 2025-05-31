/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practicafinal;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.Timer;

/**
 *
 * @author Samuel Morante
 */
public class PracticaFinal {

//---------Atributos------------------------------------------------------------
    private final JFrame ventana = new JFrame();
    private FicheroResultadosEscribir fout;
    private CardLayout cardLayout;
    private String textoIngresado = "";
    private JButton[] botonesPuzzle;
    private int contadorClicks = 1;
    private JButton boton1;
    private JButton boton2;
    private Image imagenPulsada1;
    private Image imagenPulsada2;
    private int numFilas;
    private int numCols;
    private JPanel panelVisualizaciones;
    private boolean finTiempo = false;
    private Timer cronometro;
    private String usuario;
    private String fechaHora;
    private int puntos;
    private int tiempo;
    private final JProgressBar barraProgresion = crearBarraProgresion();
    private boolean estaEnPartida = false;
    private boolean datosIncorrectos = false;
    private panelSubImagenes panelSubImagenes = new panelSubImagenes();
    private String[] ordenImagenes;
    private int ordenPrimerClick = 0;
    private int boton1pulsado = 0;
    String rutaImagen;
    private panelImagen imagenSolucion;

//------------------------------------------------------------------------------   
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        new PracticaFinal().metodoPrincipal();
    }

    private void metodoPrincipal() throws IOException {

/////////////////////////CREACION INTERFACES////////////////////////////////////
//----------------------JFrame--------------------------------------------------        
        //Crear el JFrame ventana y añadirle el nombre que saldrá arriba izq
        ventana.setTitle("Práctica Final");
//------------------------------------------------------------------------------

//---------------------Panel de contenidos--------------------------------------
        //Añadir el panel de contenidos al JFrame y definir su layout
        Container panelContenidos = ventana.getContentPane();
        panelContenidos.setLayout(new BorderLayout());

//------------------------------------------------------------------------------
//--------------------Panel de botones------------------------------------------
        //Crear el JPanel panelBotones, añadirlo al panel de contenidos y ajustar su tamaño
        JPanel panelBotones = new JPanel();
        panelContenidos.add(panelBotones, BorderLayout.WEST);
        //Definir el layout de panelBotones para que vaya de arriba a abajo los botones
        panelBotones.setLayout(new GridLayout(4, 1));

//------------------------------------------------------------------------------
//-----------------------Panel de visualizaciones-------------------------------
        //Crear el JPanel panelVisualizaciones y elegir el layout CardLayout ya que pondremos varios paneles encima
        panelVisualizaciones = new JPanel();
        panelContenidos.add(panelVisualizaciones, BorderLayout.CENTER);
        panelVisualizaciones.setLayout(new CardLayout());

        //Creación JPanel panelStandBy y añadir la imagen de la UIB
        JPanel panelStandBy = new JPanel();
        panelVisualizaciones.add(panelStandBy, "panelStandBy");
        panelImagen logotipoUIB = new panelImagen("iconos/UIB.jpg");
        panelStandBy.add(logotipoUIB);

        //Creacion JPanel panelPartida
        JPanel panelPartida = new JPanel();
        panelPartida.setLayout(new BorderLayout());
        panelVisualizaciones.add(panelPartida, "panelPartida");

        //Creacion panelImagenes
        JPanel panelImagenes = new JPanel();
        panelPartida.add(panelImagenes);
        panelPartida.add(barraProgresion, BorderLayout.SOUTH);

        //Creación JPanel panelHistorial
        JPanel panelHistorial = new JPanel();
        panelVisualizaciones.add(panelHistorial, "panelHistorial");
        panelHistorial.setLayout(new BorderLayout());

//------------------------------------------------------------------------------
//--------------------JSplit----------------------------------------------------
        //Creacion JSplit separador1
        JSplitPane separador1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        separador1.setDividerLocation(0.7);
        separador1.add(panelBotones);
        separador1.add(panelVisualizaciones);
        panelContenidos.add(separador1, BorderLayout.CENTER);
        //Deshabilitar que se pueda arrastrar separador1
        separador1.setEnabled(false);
//------------------------------------------------------------------------------
//-----------------------Añadir JMenuBar----------------------------------------
        //Crear JMenuBar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setPreferredSize(new Dimension(200, 0));
        //Añadir color al menu
        menuBar.setBackground(Color.BLACK);
        //Crear la pestaña menu en el JMenuBar
        JMenu menu = new JMenu("MENU");
        //Añadir color a las letras del menu
        menu.setForeground(Color.WHITE);
        //Añadir dentro de la pestaña Menu los apartados correspondiente
        JMenuItem nuevaPartidaMenu = new JMenuItem("NUEVA PARTIDA");
        JMenuItem clasificacionMenu = new JMenuItem("CLASIFICACIÓN GENERAL");
        JMenuItem historialMenu = new JMenuItem("HISTORIAL");
        JMenuItem cambiarDirectorioMenu = new JMenuItem("CAMBIAR DIRECTORIO DE IMÁGENES");
        JMenuItem salirMenu = new JMenuItem("SALIR");

        //Añadir los comoponentes en el JMenu menu
        menu.add(nuevaPartidaMenu);
        menu.add(clasificacionMenu);
        menu.add(historialMenu);
        menu.add(cambiarDirectorioMenu);
        menu.add(salirMenu);

        //Añadir en el JMenuBar el menu
        menuBar.add(menu);

        //Añadir en el JFrame ventana el JMenuBar para que aparezca
        ventana.setJMenuBar(menuBar);
//------------------------------------------------------------------------------

//------------------------Añadir JToolBar---------------------------------------
        //Crear JToolBar
        JToolBar iconosMenu = new JToolBar();
        panelContenidos.add(iconosMenu, BorderLayout.NORTH);

        //Añadir color al JToolBar
        iconosMenu.setBackground(Color.BLACK);

        //Crear las instancias para cada imagen del JToolBar
        ImageIcon iconoNueva = new ImageIcon("iconos/iconoNuevaPartida.jpg");
        ImageIcon iconoClasificacion = new ImageIcon("iconos/iconoHistorialSelectivo.jpg");
        ImageIcon iconoHistorial = new ImageIcon("iconos/iconoHistorialGeneral.jpg");
        ImageIcon iconoDirectorio = new ImageIcon("iconos/iconoCambiarDirectorio.jpg");
        ImageIcon iconoSalir = new ImageIcon("iconos/iconoSalir.jpg");

        //Ahora crear los botones que sean los iconos
        JButton nuevaPartidaIcono = new JButton(iconoNueva);
        JButton clasificacionIcono = new JButton(iconoClasificacion);
        JButton historialIcono = new JButton(iconoHistorial);
        JButton cambiarDirectorioIcono = new JButton(iconoDirectorio);
        JButton salirIcono = new JButton(iconoSalir);

        //Hacemos que no tenga borde para que se vea mejor el icono y no parezca un botón
        nuevaPartidaIcono.setBorderPainted(false);
        clasificacionIcono.setBorderPainted(false);
        historialIcono.setBorderPainted(false);
        cambiarDirectorioIcono.setBorderPainted(false);
        salirIcono.setBorderPainted(false);

        //Añadimos los botones en forma de icono dentro del JToolBar
        iconosMenu.add(nuevaPartidaIcono);
        iconosMenu.add(clasificacionIcono);
        iconosMenu.add(historialIcono);
        iconosMenu.add(cambiarDirectorioIcono);
        iconosMenu.add(salirIcono);

//------------------------Añadir JSplitPane separador3 y separador4-------------         
        JSplitPane separador2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        separador2.add(iconosMenu);
        separador2.add(separador1);
        panelContenidos.add(separador2, BorderLayout.NORTH);
        separador2.setEnabled(false);

        JSplitPane separador3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        separador3.setDividerLocation(17);
        separador3.add(menuBar);
        separador3.add(panelContenidos);
        ventana.setContentPane(separador3);
        separador3.setEnabled(false);

//------------------------------------------------------------------------------
//-----------------Añadir botones al JPanel panelBotones------------------------
        //Crear botones
        JButton nuevaPartidaBoton = new JButton("NUEVA PARTIDA");
        JButton clasificacionBoton = new JButton("HISTORIAL GENERAL");
        JButton historialBoton = new JButton("HISTORIAL SELECTIVO");
        JButton salirBoton = new JButton("SALIR");

        //Ajustar tamaño de los botones
        nuevaPartidaBoton.setPreferredSize(new Dimension(177, 119));
        clasificacionBoton.setPreferredSize(new Dimension(177, 119));
        historialBoton.setPreferredSize(new Dimension(177, 119));
        salirBoton.setPreferredSize(new Dimension(177, 119));

        //Cambiar colores a los botones
        nuevaPartidaBoton.setBackground(Color.GREEN);
        clasificacionBoton.setBackground(Color.ORANGE);
        historialBoton.setBackground(Color.ORANGE);
        salirBoton.setBackground(Color.ORANGE);

        //Añadir los botones al panelBotones
        panelBotones.add(nuevaPartidaBoton);
        panelBotones.add(clasificacionBoton);
        panelBotones.add(historialBoton);
        panelBotones.add(salirBoton);
//------------------------------------------------------------------------------
////////////////////////////////////////////////////////////////////////////////

/////////////////////EVENTOS////////////////////////////////////////////////////
//--------------------------Salir-----------------------------------------------
        metodoSalir(salirIcono, salirBoton, salirMenu);
//------------------------------------------------------------------------------
//-------------------------Nueva partida----------------------------------------
        metodoNuevaPartida(nuevaPartidaIcono, nuevaPartidaBoton, nuevaPartidaMenu, panelImagenes);
//------------------------------------------------------------------------------
//-------------------------Historial general------------------------------------
        metodoHistorialGeneral(clasificacionIcono, clasificacionBoton, clasificacionMenu, panelHistorial);
//------------------------------------------------------------------------------
//------------------------Historial selectivo-----------------------------------
        metodoHistorialSelectivo(historialIcono, historialBoton, historialMenu, panelHistorial);
//------------------------------------------------------------------------------
//-----------------------Cambiar directorio imagenes----------------------------
        metodoCambiarDirectorioImagenes(cambiarDirectorioIcono, cambiarDirectorioMenu);
//------------------------------------------------------------------------------
////////////////////////////////////////////////////////////////////////////////

//////////////////////////Últimas intervenciones////////////////////////////////
        //Ajustar tamaño de la ventana
        ventana.setSize(900, 600);
        //Activación del cierre interactivo ventana de windows en el contenedor 
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //centrado del contenedor ventana en el centro de la pantalla
        ventana.setLocationRelativeTo(null);
        //visualización contenedor JFrame ventana
        ventana.setVisible(true);
    }
////////////////////////////////////////////////////////////////////////////////

/////////////////////////MÉTODOS////////////////////////////////////////////////
//----------------------Metodo para salir del programa--------------------------
    private void salirVentana() {
        //Sale del programa
        System.exit(0);
    }
//------------------------------------------------------------------------------

//--------Metodo que crea el resultado que usaremos luego para el historial-----
    private void crearResultado() throws IOException {
        //creamos un File con el nombre del archivo
        File ficheroResultado = new File("resultados.dat");

        //En caso de que no exista creará el fichero y meterá el resultado.
        if (!ficheroResultado.exists()) {
            crearFicheroResultado(usuario, fechaHora, puntos);
        } //En caso de que ya exista el fichero simplemente meterá el resultado.
        else if (ficheroResultado.exists()) {
            añadirObjetoLista(usuario, fechaHora, puntos);
        }

    }
//------------------------------------------------------------------------------

//-----Añadimos el ActionListener para los que usan la función salirVentana-----
    private void metodoSalir(JButton boton, JButton boton2, JMenuItem menu) {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salirVentana();

            }
        };
        //Añadimos los botones que se usarán para salir
        boton.addActionListener(actionListener);
        boton2.addActionListener(actionListener);
        menu.addActionListener(actionListener);
    }
//------------------------------------------------------------------------------

//---Metodo que usarán los botones para mostrar el historial general------------
    private void metodoHistorialGeneral(JButton boton, JButton boton2, JMenuItem menu, JPanel panel2) {
        //Creamos JTextArea que contendrá los resultados
        JTextArea areaVisualizacionResultados = new JTextArea();
        areaVisualizacionResultados.setEditable(false);
        //En caso de ser necesario usar scrollpane
        JScrollPane scrollPane = new JScrollPane(areaVisualizacionResultados);
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!estaEnPartida) {
                    //Llamamos para poder leer
                    FicheroResultadosLeer fichero;
                    //Borramos todo lo que hubiese antes para que no haya errores cada vez que mostremos el historial
                    panel2.removeAll();
                    try {
                        fichero = new FicheroResultadosLeer("resultados.dat");
                        //Leemos el archivo resultados.dat y metemos los resultados en una lista
                        List<Partida> resultados = fichero.leerObjetosLista();
                        //Para saber el numero con el que haremos el bucle
                        int numeroResultados = resultados.size();
                        areaVisualizacionResultados.setText("");
                        //Bucle que recorre la lista y crea un string con los resultados y los añade al JTextArea
                        for (int i = 0; i < numeroResultados; i++) {
                            String resultadoString = "Nombre: " + resultados.get(i).getNombre() + " -  Fecha: " + resultados.get(i).getFecha() + " -  Puntos: " + resultados.get(i).getPuntos();
                            areaVisualizacionResultados.append(resultadoString + "\n");

                            //Parametros de letra 
                            Font fuente = new Font("Arial", Font.BOLD, 23);
                            areaVisualizacionResultados.setFont(fuente);
                            panel2.add(scrollPane, BorderLayout.CENTER);

                        }
                        //Actualizar y repintar el panel después de realizar los cambios
                        panel2.revalidate();
                        panel2.repaint();
                        mostrarPanel("panelHistorial");
                        //En caso de que esté vacio resultados.dat
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "NADIE HA JUGADO AUN. NO HAY HISTORIAL", "MENSAJE", JOptionPane.INFORMATION_MESSAGE);
                    }
                    //En caso de que pulse el boton durante una partida
                } else {
                    JOptionPane.showMessageDialog(null, "ANTES DEBES ACABAR LA PARTIDA", "MENSAJE", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        };
        //Añadimos los botones que se usará para el historial general
        boton.addActionListener(actionListener);
        boton2.addActionListener(actionListener);
        menu.addActionListener(actionListener);

    }
//------------------------------------------------------------------------------

//----------------Metodo para el historial selectivo----------------------------
    //Hace exactamente lo mismo que el metodo del historial general pero hace un bucle para ver que el usuario introducido por el usuario coincide con algun resultado
    private void metodoHistorialSelectivo(JButton boton, JButton boton2, JMenuItem menu, JPanel panel2) {
        JTextArea areaVisualizacionResultados = new JTextArea();

        areaVisualizacionResultados.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaVisualizacionResultados);
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!estaEnPartida) {
                    try {
                        dialogoHistorial();
                        FicheroResultadosLeer fichero;
                        fichero = new FicheroResultadosLeer("resultados.dat");
                        List<Partida> resultados = fichero.leerObjetosLista();
                        panel2.removeAll();
                        int contador = 0;
                        areaVisualizacionResultados.setText("");
                        //Bucle para buscar coincidencias
                        for (int i = 0; i < resultados.size(); i++) {
                            String textoIngresadoMinus = textoIngresado.toLowerCase();
                            if (textoIngresadoMinus.equals(resultados.get(i).getNombre().toLowerCase())) {
                                String resultadoString = "Nombre: " + resultados.get(i).getNombre() + " -  Fecha: " + resultados.get(i).getFecha() + " -  Puntos: " + resultados.get(i).getPuntos();
                                areaVisualizacionResultados.append(resultadoString + "\n");

                                Font fuente = new Font("Arial", Font.BOLD, 23);
                                areaVisualizacionResultados.setFont(fuente);
                                panel2.add(scrollPane, BorderLayout.CENTER);
                                contador++;
                            }
                        }

                        panel2.revalidate();
                        panel2.repaint();
                        //En caso de que no haya coincidencias
                        if (contador == 0) {
                            areaVisualizacionResultados.append("NO EXISTE COINCIDENCIAS");
                            Font fuente = new Font("Arial", Font.BOLD, 23);
                            areaVisualizacionResultados.setFont(fuente);
                            panel2.add(scrollPane, BorderLayout.CENTER);
                        }
                        mostrarPanel("panelHistorial");
                        //En caso de que no exista resultados.dat
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "NADIE HA JUGADO AUN. NO HAY HISTORIAL", "MENSAJE", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "ANTES DEBES ACABAR LA PARTIDA", "MENSAJE", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        };
        //Añadimos los botones que usarán el historial selectivo
        boton.addActionListener(actionListener);
        boton2.addActionListener(actionListener);
        menu.addActionListener(actionListener);
    }
//------------------------------------------------------------------------------

//-------------Muestra el dialogo para la busqueda selectiva del historial------
    private void dialogoHistorial() {
        JTextField textoHistorial = new JTextField();
        int opcion = JOptionPane.showOptionDialog(null, textoHistorial, "Ingresar texto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

        if (opcion == JOptionPane.OK_OPTION) {
            textoIngresado = textoHistorial.getText();
        } else {
            System.out.println("Opcion Cancelada");
        }

    }
//------------------------------------------------------------------------------

//---------------Creacion de la barra de progresion-----------------------------
    private JProgressBar crearBarraProgresion() {
        int valorMinimo = 0;
        int valorMaximo = 100;
        JProgressBar barraTemporal;
        barraTemporal = new JProgressBar();
        //ASIGNACIÓN VALORES MÍNIMO Y MÁXIMO A LA JProgressBar barraTemporal
        barraTemporal.setMinimum(valorMinimo);
        barraTemporal.setMaximum(valorMaximo);
        //ASIGNACIÓN VALOR INICIAL A LA JProgressBar barraTemporal
        barraTemporal.setValue(0);
        //ACTIVACIÓN VISUALIZACIÓN VALOR EN LA JProgressBar barraTemporal
        barraTemporal.setStringPainted(true);
        //DIMENSIONAMIENTO JProgressBar barraTemporal
        barraTemporal.setPreferredSize(new Dimension(500, 25));
        //ASIGNACIÓN COLORES DE FONDO Y TRAZADO JProgressBar barraTemporal
        barraTemporal.setForeground(Color.RED);
        barraTemporal.setBackground(Color.YELLOW);

        return barraTemporal;
    }
//------------------------------------------------------------------------------

//----------------Creacion del cronometro---------------------------------------
    private void crearTimer() {

        int valorMaximo = 100;
        cronometro = new Timer(tiempo, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int valor = barraProgresion.getValue();
                if (valor < valorMaximo) {
                    // Asignar a JProgressBar barraTemporal su valor incrementado en una unidad
                    barraProgresion.setValue(valor + 1);
                    finTiempo = false;
                } else {
                    //En caso de que llegue al tiempo máximo
                    finTiempo = true;
                    metodoCompletarPuzzle();
                    // Detener el temporizador cronometro
                    cronometro.stop();

                }
            }
        });
    }
//------------------------------------------------------------------------------

//-----Metodo para mostrar un panel en concreto cuando se usa CardLayout--------
    private void mostrarPanel(String nombrePanel) {
        if (cardLayout == null) {
            cardLayout = (CardLayout) panelVisualizaciones.getLayout();
        }
        cardLayout.show(panelVisualizaciones, nombrePanel);
    }
//------------------------------------------------------------------------------

//----------------Metodo para la creación de una nueva partida------------------
    private void metodoNuevaPartida(JButton boton, JButton boton2, JMenuItem menu, JPanel panelImagenes) {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Si no está en una partida mostrará el formulario
                if (!estaEnPartida) {
                    datosIntroducidosUsuario();
                }
                //Si sale todo bien ejecutará esto    
                if (!estaEnPartida && !datosIncorrectos) {
                    contadorClicks = 1;
                    finTiempo = false;
                    estaEnPartida = true;
                    //Ponemos la barra en 0
                    barraProgresion.setValue(0);
                    //Mostramos el panel de partida
                    mostrarPanel("panelPartida");

                    //Y crea el puzzle
                    mostrarPuzzle(panelImagenes);
                    //En caso de que los datos no sean incorrectos empezaría el tiempo ya
                    if (!datosIncorrectos) {
                        crearTimer();
                        cronometro.start();
                    }
                    //En caso de que esté en partida o salga algo mal
                } else if (estaEnPartida) {
                    JOptionPane.showMessageDialog(null, "ANTES DEBES ACABAR LA PARTIDA", "MENSAJE", JOptionPane.INFORMATION_MESSAGE);
                } else if (datosIncorrectos && !estaEnPartida) {
                    mostrarPanel("panelStandBy");
                } else {
                    mostrarPanel("panelStandBy");
                }
            }
        };
        //Añadimos los botones que crearán una nueva partida
        boton.addActionListener(actionListener);
        boton2.addActionListener(actionListener);
        menu.addActionListener(actionListener);
    }
//------------------------------------------------------------------------------

//--------------------Metodo para cambiar el directorio de imagenes-------------
    private void metodoCambiarDirectorioImagenes(JButton boton, JMenuItem menu) {

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Seleccionariamos el directorio
                if (!estaEnPartida) {
                    seleccionarDirectorio.seleccionarDirectorio(ventana, numFilas, numCols);
                } //En caso de que estemos en una partida
                else {
                    JOptionPane.showMessageDialog(null, "ANTES DEBES ACABAR LA PARTIDA", "MENSAJE", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        };
        //Añadimos los botones que usarán el cambio de directorio
        boton.addActionListener(actionListener);
        menu.addActionListener(actionListener);
    }
//------------------------------------------------------------------------------

//---Metodo que muestra por pantalla los datos que debe introduciar para jugar--
    private void datosIntroducidosUsuario() {
        try {
            //Creacion del formulario
            String[] literalesIntroduccion = {"NOMBRE", "NÚMERO DE SUBDIVISIONES VERTICAL", "NÚMERO DE SUBDIVISIONES HORIZONTAL"};
            literalesIntroduccion = new LTGraficos(ventana, literalesIntroduccion).getDatosTexto();
            //Guardamos las filas y las columnas del tiempo
            numFilas = Integer.parseInt(literalesIntroduccion[1]);
            numCols = Integer.parseInt(literalesIntroduccion[2]);
            //Añadimos el tiempo que crece exponencialmente depende de cuánto se divide el puzzle
            double tiempoExponencial = Math.pow(2, numFilas * numCols);
            tiempo = (int) (tiempoExponencial);
            //Guardar la fecha y hora en el que haces el formulario
            LocalDateTime fechaHoraActual = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm:ss");
            fechaHora = fechaHoraActual.format(formatter);
            //En caso de que no rellene bien los datos
            if (Integer.parseInt(literalesIntroduccion[1]) < 0 || Integer.parseInt(literalesIntroduccion[2]) < 2) {
                datosIncorrectos = true;
                JOptionPane.showMessageDialog(ventana, "DEBES INTRODUCIR UN NÚMERO MAYOR QUE UNO");

            } //En caso de que si rellene bien los datos
            else {
                usuario = literalesIntroduccion[0];
                datosIncorrectos = false;
            }
            //Excepcion de errores
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(ventana, "DEBES INTRODUCIR UN NÚMERO CORRECTO");
            datosIncorrectos = true;
        } catch (NullPointerException e) {
            datosIncorrectos = true;
        }

    }
//------------------------------------------------------------------------------

//-------------------Metodo para crear el fichero partida.dat----------------
    private void crearFicheroResultado(String nombre, String fecha, int puntos) {
        try {
            fout = new FicheroResultadosEscribir("resultados.dat");
            Partida res = new Partida(nombre, fecha, puntos);
            fout.write(res);
            fout.close();
        } catch (IOException e) {
            System.err.print(e);
        }
    }
//------------------------------------------------------------------------------

//------------------Añadir un objeto Partida a la lista-----------------------
    private void añadirObjetoLista(String nombre, String fecha, int puntos) throws IOException {
        try {
            FicheroResultadosLeer fichero = new FicheroResultadosLeer("resultados.dat");
            List<Partida> resultados = fichero.leerObjetosLista();
            Partida res = new Partida(nombre, fecha, puntos);
            resultados.add(res);
            fout = new FicheroResultadosEscribir("resultados.dat");
            fout.writeLista(resultados);
            fout.close();
        } catch (NullPointerException e) {

        }
    }
//------------------------------------------------------------------------------

//---------ActionListener para el botón continuar en panelImagenSolucion--------
    private void accionBotonContinuar(JButton boton) {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Mostraria el panel standby
                mostrarPanel("panelStandBy");
                estaEnPartida = false;
            }
        };
        boton.addActionListener(actionListener);
    }
//------------------------------------------------------------------------------

//------Metodo para mostrar el puzzle usando botones y añadiendo imagenes-------
    private void mostrarPuzzle(JPanel panel) {
        try {
            //Borramos todo el contenido
            panel.removeAll();
            //Creamos la lista con las subimagenes ordenadas de forma aleatoria
            List<SubImagen> lista = panelSubImagenes.listaConSubImagenesAleatorias(numCols, numFilas);
            //Las convertimos en ImageIcon para poder introducirlas a los botones
            List<ImageIcon> listaImageIcon = panelSubImagenes.listaConImagenesParaBotones(lista);
            //Creamos un array con el primer orden aleatorio
            ordenImagenes = new String[lista.size()];
            for (int i = 0; i < lista.size(); i++) {
                int orden = lista.get(i).getOrden();
                String nombreOrden = String.valueOf(orden);
                ordenImagenes[i] = nombreOrden;
            }
            //GridLayout para que sea mismo tamaño los botones
            panel.setLayout(new GridLayout(numFilas, numCols));
            //Creamos los botones
            String nombreBoton;
            botonesPuzzle = new JButton[listaImageIcon.size()];
            //Bucle que crea botones y añade la imagen correspondiente
            for (int i = 0; i < listaImageIcon.size(); i++) {
                int botonAncho = panel.getWidth() / numCols;
                int botonAlto = panel.getHeight() / numFilas;

                Image imagenAjustada = listaImageIcon.get(i).getImage().getScaledInstance(botonAncho, botonAlto, Image.SCALE_SMOOTH);
                int numeroBoton = i;
                nombreBoton = String.valueOf(numeroBoton);
                ImageIcon botonPuzzle = new ImageIcon(imagenAjustada);
                JButton boton = new JButton(botonPuzzle);

                boton.setActionCommand(nombreBoton);
                botonesPuzzle[i] = boton;
                panel.add(boton);

            }
            agregarActionListenerBotonesPuzzle();
        } catch (NullPointerException e) {

        }

    }
//------------------------------------------------------------------------------

//-------------Saber si el puzzle está completo o no----------------------------
    private boolean puzzleTerminado() {
        try {
            boolean finPuzzle = true;

            for (int i = 0; i < ordenImagenes.length - 1; i++) {
                //En caso de que el array esté ordenado del 0 al numero mayor significa que el puzzle se habrá completado
                    if (Integer.parseInt(ordenImagenes[i]) > Integer.parseInt(ordenImagenes[i + 1])) {
                    finPuzzle = false;
                    break;
                }
            }

            return finPuzzle;
        } catch (NullPointerException e) {
            return false;
        }

    }
//------------------------------------------------------------------------------

//------Añadir el actionListener a los botones y que se cambien al pulsar-------    
    private void agregarActionListenerBotonesPuzzle() {

        for (JButton boton : botonesPuzzle) {
            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //Si contadorclicks es igual a uno significa que ha pulsado en la primera imagen asi que guarda la variable

                    if (contadorClicks == 1) {
                        //Guardamos el boton que hemos pulsado
                        boton1 = boton;
                        boton1pulsado = Integer.parseInt(boton1.getActionCommand());
                        //Guardamos la imagen que hemos apretado
                        imagenPulsada1 = ((ImageIcon) boton1.getIcon()).getImage();
                        ordenPrimerClick = Integer.parseInt(ordenImagenes[boton1pulsado]);
                        
                        contadorClicks++;
                        //Si contadorclicks es igual a dos significa que ha apretado el segundo boton en el que quiere cambiar la imagen y se cambia
                    } else if (contadorClicks == 2) {
                        //Guardamos el segundo boton que hemos apretado
                        boton2 = boton;
                        //Guardamos la imagen
                        imagenPulsada2 = ((ImageIcon) boton2.getIcon()).getImage();
                        //Cambiamos las imagenes de los botones
                        boton2.setIcon(new ImageIcon(imagenPulsada1));
                        boton1.setIcon(new ImageIcon(imagenPulsada2));
                        
                        int boton2pulsado = Integer.parseInt(boton2.getActionCommand());
                        int aux = ordenPrimerClick;
                        //Aquí ponemos el orden correcto en el array que habiamos creado
                        ordenImagenes[boton1pulsado] = String.valueOf(ordenImagenes[boton2pulsado]);
                        ordenImagenes[boton2pulsado] = String.valueOf(aux);
                        //Vuelta a empezar
                        contadorClicks = 1;
                        //Comprueba si se ha terminado el puzzle
                        metodoCompletarPuzzle();
                    }

                }
            });
        }
    }
//------------------------------------------------------------------------------

//----Metodo para saber si ha completado el puzzle o se ha acabado el tiempo----
    private void metodoCompletarPuzzle() {

        try {
            boolean puzzleTerminado = puzzleTerminado();
            //Condicion por si ha completado el puzzle y da los puntos al usuario
            if (puzzleTerminado && !finTiempo) {
                cronometro.stop();
                JOptionPane.showMessageDialog(null, "HAS COMPLETADO EL PUZZLE", "¡ENHORABUENA!", JOptionPane.INFORMATION_MESSAGE);
                puntos = 4;
                //Crea el resultado
                crearResultado();

                //Muestra la imagen de la solucion
                ponerImagenSolucion();
                mostrarPanel("panelImagenSolucion");

                //En caso de que termine el tiempo
            } else if (!puzzleTerminado && finTiempo) {
                JOptionPane.showMessageDialog(null, "SE TE HA TERMINADO EL TIEMPO", "¡FIN!", JOptionPane.INFORMATION_MESSAGE);
                puntos = 0;
                crearResultado();
                ponerImagenSolucion();
                mostrarPanel("panelImagenSolucion");

            }

        } catch (NullPointerException e) {
            mostrarPanel("panelStandBy");
            estaEnPartida = false;
        } catch (IOException e) {
            System.err.print("ERROR");
        }

    }
//------------------------------------------------------------------------------

//---------Metodo para el panelImagenSolucion-----------------------------------
    private void ponerImagenSolucion() {
        JPanel panelImagenSolucion = new JPanel(new BorderLayout());
        panelVisualizaciones.add(panelImagenSolucion, "panelImagenSolucion");
        rutaImagen = panelSubImagenes.getRutaImagen();
        imagenSolucion = new panelImagen(rutaImagen);
        panelImagenSolucion.add(imagenSolucion, BorderLayout.CENTER);
        JButton botonContinuar = new JButton("CONTINUAR");
        panelImagenSolucion.add(botonContinuar, BorderLayout.SOUTH);
        accionBotonContinuar(botonContinuar);
    }

//--------Metodo para mostrar una imagen en un JPanel visto en clase------------
    private class panelImagen extends JComponent {

        private BufferedImage imagen = null;

        public panelImagen(String nombre) {
            try {
                imagen = ImageIO.read(new File(nombre));
            } catch (IOException e) {
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(imagen, 0, 0, getParent().getWidth(), getParent().getHeight(), this);
        }

        //La componente decide su tamaño en función de la dimensión de la ventana
        @Override
        public Dimension getPreferredSize() {
            if (imagen == null) {
                return new Dimension(200, 200);
            } else {
                return getParent().getSize();
            }
        }
    }
//------------------------------------------------------------------------------
////////////////////////////////////////////////////////////////////////////////
}
