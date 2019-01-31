package fr.ufc.l3info.projet;


import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;
import java.util.List;

class Fenetre {

    private JFrame fenetre=new JFrame();
    private Carte map;
    private Menu menuBar;
    private MenuDeroulant menuDeroulant;
    private ModificationMessage modificationMessage;

    /**
     * constructor of display
     */
    Fenetre(){
        menuBar = new Menu();

        // faire une fonction pour la lisibilité
        menuBar.getMenuItem().addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fc = new JFileChooser();
                if (fc.showOpenDialog(menuBar.getMenu()) == JFileChooser.APPROVE_OPTION) {
                    try {
                        InputStream flux = new FileInputStream(fc.getSelectedFile());
                        InputStreamReader lecture = new InputStreamReader(flux);
                        BufferedReader buff = new BufferedReader(lecture);
                        String ligne;
                        int ENLEVEMOI=0;
                        while ((ligne = buff.readLine()) != null) {
                            //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                            ++ENLEVEMOI;
                            //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                            Message msg=new Message(ligne);
                            Ship vessel = new Ship(msg.getDecode().getMMSI());
                            vessel.addMessage(msg);
                            menuBar.getShips().put(msg.getDecode().getMMSI(),vessel);
                            menuDeroulant.getDefaultList().addElement(msg.getDecode().getMMSI());
                            //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                            if(ENLEVEMOI==3)
                                break; // les autres lignes du fichier ne sont peut etre pas encore traité
                            //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                        }
                        buff.close();
                    } catch (Exception ex) {
                        System.out.println(e.toString());
                    }
                    reloadMap();
                }
            }
        });

        // faire une fonction pour la lisibilité
        menuBar.getMenuItemExporter().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                if (fc.showOpenDialog(menuBar.getMenu()) == JFileChooser.APPROVE_OPTION) {
                    final String chemin = fc.getSelectedFile().getAbsolutePath();
                    final File fichier = new File(chemin);
                    System.out.println(chemin);
                    try {
                        fichier.createNewFile();
                        final FileWriter writer = new FileWriter(fichier);
                        try {
                            /*for (menuBar.getMessages() :
                                 ) {
                                
                            }*/
                            writer.write("Test\n");
                        } finally {
                            writer.close();
                        }
                    } catch (Exception ex) {
                        System.out.println("Impossible de creer le fichier");
                    }
                }
            }

        });

        // set JFrame
        BorderLayout layout= new BorderLayout(4,4);
        fenetre.setLayout(layout);
        fenetre.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        fenetre.addWindowListener(addWindowEvent());
        fenetre.setSize(1000, 800);
        fenetre.setMinimumSize(new Dimension(700,700));

        // creation de la carte
        map = new Carte();

        //!\\ TRAVAUX EN COURS //!\\ cf fonction selectShipOnMap()
        //listener pour la selection des navire via la map
        map.getMyMap().addMouseListener(selectShipOnMap());


        // creation du menu deroulant (liste bateau) a gauche
        menuDeroulant = new MenuDeroulant();
        menuDeroulant.getListDeroulante().addListSelectionListener(addSelectListener());

        // creation de la partie modification des message AIS en bas
        modificationMessage = new ModificationMessage();
        modificationMessage.getPanel().setPreferredSize(new Dimension(0,200));

        // affichage
        //----------
        fenetre.setJMenuBar(menuBar.getMenuBar());
        //----------
        fenetre.add(menuDeroulant.getPanel(),BorderLayout.WEST);
        fenetre.add(map.getPanel(), BorderLayout.CENTER);
        fenetre.add(modificationMessage.getPanel(),BorderLayout.SOUTH);
        fenetre.setVisible(true);
    }


    //----------------

    /**
     * reload the map after import raw AIS
     */
    private void reloadMap(){
        map.reloadMap(menuBar.getShips());
    }

    /**
     * setup a listener for display the selection of the list of ship
     * @return ListSelectionListener
     */
    private ListSelectionListener addSelectListener(){
        return new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){ // lors de la section d'un ou plusieur item de la liste deroulante
                    String mmsi=(String)menuDeroulant.getListDeroulante().getSelectedValue();
                    List allShip = menuDeroulant.getListDeroulante().getSelectedValuesList();
                    HashMap<String ,Ship> allSelectedShip=new HashMap<>();
                    for(Object vessel:allShip){
                        allSelectedShip.put(menuBar.getShip((String)vessel).getLastKnownMessage().getDecode().getMMSI(),menuBar.getShip((String)vessel));
                    }
                    modificationMessage.affichage(allSelectedShip);
                    modificationMessage.getPanel().revalidate();
                    modificationMessage.getPanel().updateUI();
                    map.reloadMap(menuBar.getShips(),mmsi); // centre la map sur le navire selectionné
                }

            }
        };
    }

    /**
     * Check if the user want to close the application
     */
    private void validateOnClose(){
        Object[] options = {"yes, please",
                "no, thanks"};
        JOptionPane onQuit=new JOptionPane("Do you want to close the application ?",JOptionPane.WARNING_MESSAGE,JOptionPane.YES_NO_OPTION,null,options);
        onQuit.createDialog(fenetre,"Warning").setVisible(true);
        Object res=onQuit.getValue();
        if(res==options[0]){
            // do something before close app
            System.exit(0);
        }
    }

    /**
     * add some event to window
     * @return WindowListener
     */
    private WindowListener addWindowEvent(){
        return new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                validateOnClose();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        };
    }

    //*\\ Traveaux en cours //!\\
    private MouseListener selectShipOnMap(){
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton()==MouseEvent.BUTTON1){
                    System.out.println("mouseClicked");
                    Point point=map.getMyMap().getMapPosition(e.getX(),e.getY());
                    if(point!=null) {
                        for (MapMarker mark : map.getMyMap().getMapMarkerList()) {
                            Coordinate coordinate=mark.getCoordinate();
                            Point point1=map.getMyMap().getTileController().getTileSource().latLonToXY(coordinate,map.getMyMap().getZoom());

                            System.out.println("souris "+point+"\nship :"+point1);
                            if ((point.x-(int)coordinate.getLon()<=20)&&(point.y-(int)coordinate.getLat()<=20)) {
                                map.reloadMap(menuBar.getShips(), coordinate);
                                return;
                            }
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }

}
