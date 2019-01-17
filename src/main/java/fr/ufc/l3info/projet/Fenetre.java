package fr.ufc.l3info.projet;


import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.ArrayList;
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
                            menuBar.getMessages().put(msg.getDecode().getMMSI(),msg);
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

        BorderLayout layout= new BorderLayout(4,4);
        fenetre.setLayout(layout);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setSize(1000, 800);
        fenetre.setMinimumSize(new Dimension(700,700));

        // creation de la carte
        map = new Carte();

        //!\\ TRAVAUX EN COURS //!\\
        //listener pour la selection des navire via la map
        map.getMyMap().addMouseListener(new MouseListener() {
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
                                map.reloadMap(menuBar.getMessages(), coordinate);
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
        });


        // creation du menu deroulant (liste bateau) a gauche
        menuDeroulant = new MenuDeroulant();
        menuDeroulant.getListDeroulante().addListSelectionListener(addSelectListener());

        // creation de la partie modification des message AIS en bas
        modificationMessage = new ModificationMessage();
        modificationMessage.getPanel().setPreferredSize(new Dimension(0,200));
        modificationMessage.getCancelButton().addActionListener(addCancelListener());
        modificationMessage.getValidateButton().addActionListener(addValidateListener());

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
        map.reloadMap(menuBar.getMessages());
    }

    /**
     * setup a listener for display the selection of the list of ship
     * @return ListSelectionListener
     */
    private ListSelectionListener addSelectListener(){
        return new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    Message topListShip= menuBar.getMessages().get((String)menuDeroulant.getListDeroulante().getSelectedValue());
                    List allShip = menuDeroulant.getListDeroulante().getSelectedValuesList();
                    ArrayList<Message> allSelectedShip=new ArrayList<>();
                    for(Object vessel:allShip){
                        allSelectedShip.add(menuBar.getMessage((String)vessel));
                    }
                    modificationMessage.affichage(allSelectedShip);
                    modificationMessage.getPanel().revalidate();
                    modificationMessage.getPanel().updateUI();
                    map.reloadMap(menuBar.getMessages(),topListShip.getDecode().getMMSI()); // centre la map sur le navire selectionné
                }

            }
        };
    }

    /**
     * create an ActionListener for cancel Button
     * @return ActionListener
     */
    private ActionListener addCancelListener(){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificationMessage.reload();
                modificationMessage.getPanel().revalidate();
                modificationMessage.getPanel().updateUI();
            }
        };
    }

    /**
     * create an ActionListener for validate Button
     * @return ActionListener
     */
    private ActionListener addValidateListener(){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // save information

                modificationMessage.reload();
                modificationMessage.getPanel().revalidate();
                modificationMessage.getPanel().updateUI();
            }
        };
    }
}
