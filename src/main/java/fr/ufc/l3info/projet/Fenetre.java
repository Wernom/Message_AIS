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

    private JFrame fenetre=new JFrame("AIS-Project");
    private Carte map;
    private Menu menuBar;
    private MenuDeroulant menuDeroulant;
    private DisplaySelectedShip displaySelectedShip;
    private boolean modifMsgSelection=false;
    private String selectorModification="Hard";
    private Boolean testSelectModification=false;
    private Boolean testSelectMessageType=false;

    private Dimension sizeScreen;

    /**
     * constructor of display
     */
    Fenetre(){
        sizeScreen=Toolkit.getDefaultToolkit().getScreenSize();
        int defaultsizeH=sizeScreen.height*3/4;
        int defaultsizeW=sizeScreen.width*3/4;
        menuBar = new Menu();

        menuBar.getMenuItem().addActionListener(importListener());

        // faire une fonction pour la lisibilité
        menuBar.getMenuItemExporterCsv().addActionListener(new ActionListener() {
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
                            List selectedShip = menuDeroulant.getListDeroulante().getSelectedValuesList();
                            BufferedWriter bw = new BufferedWriter(writer);
                            for (Object item : selectedShip) {
                                String content = menuBar.getShip((String) item).getLastKnownMessage().getDecode().printMessage();
                                bw.write(content);
                                bw.newLine();
                            }
                            bw.close();
                            writer.close();
                            } finally {
                            writer.close();
                        }
                    } catch (Exception ex) {
                        System.out.println("Impossible de creer le fichier");
                    }
                }
            }

        });

        menuBar.getMenuItemExporterAis().addActionListener(new ActionListener() {
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
                            List selectedShip = menuDeroulant.getListDeroulante().getSelectedValuesList();
                            BufferedWriter bw = new BufferedWriter(writer);
                            for (Object item : selectedShip) {
                                String content = menuBar.getShip((String) item).getLastKnownMessage().getDecode().printMessage();
                                bw.write(menuBar.getShip((String) item).getLastKnownMessage().getAis().getRawData());
                                bw.newLine();
                            }
                            bw.close();
                            writer.close();
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
        fenetre.setSize(defaultsizeW, defaultsizeH);
        fenetre.setMinimumSize(new Dimension((int)(defaultsizeW/1.2),(int)(defaultsizeH)));

        // creation de la carte
        map = new Carte();

        //!\\ TRAVAUX EN COURS //!\\ cf fonction selectShipOnMap()
        //listener pour la selection des navire via la map
        map.getMyMap().addMouseListener(selectShipOnMap());


        // creation du menu deroulant (liste bateau) a gauche
        menuDeroulant = new MenuDeroulant();
        menuDeroulant.getListDeroulante().addListSelectionListener(addSelectListener());


        // creation de la partie modification des message AIS en bas
        displaySelectedShip = new DisplaySelectedShip();

        // affichage
        //----------
        fenetre.setJMenuBar(menuBar.getMenuBar());
        //----------
        fenetre.add(menuDeroulant.getPanel(),BorderLayout.WEST);
        fenetre.add(map.getPanel(), BorderLayout.CENTER);
        fenetre.add(displaySelectedShip.getPanel(),BorderLayout.SOUTH);
        fenetre.setVisible(true);
    }


    //----------------

    /**
     * reload the map after import raw AIS
     */
    private void reloadMap(){
        map.reloadMap(menuBar.getShips(),(Ship)null,modifMsgSelection);
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

    // listener
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
                    if(mmsi.equals("<none>")){
                        displaySelectedShip.getPanel().removeAll();
                        displaySelectedShip.getPanel().revalidate();
                        displaySelectedShip.getPanel().updateUI();
                        reloadMap();
                        return;
                    }
                    List allShip = menuDeroulant.getListDeroulante().getSelectedValuesList();
                    final HashMap<String ,Ship> allSelectedShip=new HashMap<>();
                    for(Object vessel:allShip){
                        allSelectedShip.put(menuBar.getShip((String)vessel).getMMSI(),menuBar.getShip((String)vessel));
                    }
                    displaySelectedShip.affichage(map,menuBar.getShips(),allSelectedShip,selectorModification,modifMsgSelection);
                    displaySelectedShip.getPanel().revalidate();
                    displaySelectedShip.getPanel().updateUI();
                    menuDeroulant.getChoiceDisplayedMessage().addItemListener(selectMessage(allSelectedShip));
                    menuDeroulant.getPossibilitiesModifications().addItemListener(selectionModification(allSelectedShip));
                }

            }
        };
    }

    /**
     *  Menu listener import AIS data from file
     * @return ActionListener
     */
    private ActionListener importListener(){
        return new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                if (fc.showOpenDialog(menuBar.getMenu()) == JFileChooser.APPROVE_OPTION) {
                    boolean rawData;
                    try {
                        //verification
                        File fic=fc.getSelectedFile();
                        if(fic.getName().matches(".*(.raw)$")){
                            rawData=true;
                        }else if(fic.getName().matches(".*(.csv)$")){
                            rawData=false;
                            PopUp.warning(fenetre,"This feature must be added soon.\nSorry for the incovenience");
                            return;

                        }else {
                            // popup the File Extension must be "File.raw" or "File.csv"
                            PopUp.error(fenetre,"File Extension must be \"File.raw\" or \"File.csv\"");
                            return;
                        }
                        //import
                        InputStream flux = new FileInputStream(fic);
                        InputStreamReader lecture = new InputStreamReader(flux);
                        BufferedReader buff = new BufferedReader(lecture);

                        String ligne;
                        Ship vessel = null;
                        while ((ligne = buff.readLine()) != null) {
                            try {
                                Message msg;
                                if(rawData) {
                                     msg = new Message(ligne);
                                }else {
                                    msg=createMessageFromCsv(ligne);
                                }
                                if(vessel==null){
                                    vessel = new Ship(msg.getDecode().getMMSI());
                                    createNewShip(vessel,msg);
                                }else{
                                    if(vessel.getMMSI().equals(msg.getDecode().getMMSI())){
                                        vessel.addMessage(msg);
                                    }else{
                                        vessel=new Ship(msg.getDecode().getMMSI());
                                        createNewShip(vessel,msg);
                                    }
                                }

                            }catch (Exception exception){
                                System.out.println("error when AIS import");
                            }
                        }
                        buff.close();
                    } catch (Exception ex) {
                        System.out.println(e.toString());
                    }
                    reloadMap();
                }
            }
        };
    }

    /**
     * Create Message on CSV import
     * @param ligne String
     * @return Message
     */
    private Message createMessageFromCsv(String ligne){
        String []strTab=ligne.split(",");
        // TODO
        return new Message(strTab);
    }

    /**
     * Create new ship on import
     * @param vessel Ship
     * @param msg Message
     */
    private void createNewShip(Ship vessel,Message msg){
        vessel.addMessage(msg);
        menuBar.getShips().put(msg.getDecode().getMMSI(), vessel);
        menuDeroulant.getDefaultList().addElement(msg.getDecode().getMMSI());
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

    /**
     * for choice to display Unmodified message or modified message
     * @param allSelectedShip HashMap<String ,Ship>
     * @return ItemListener
     */
    private ItemListener selectMessage(final HashMap<String ,Ship> allSelectedShip){
        return new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()== ItemEvent.SELECTED){
                    if(!testSelectMessageType) {
                        testSelectMessageType=true;
                        Object item = e.getItem();
                        switch (item.toString()) {
                            case "Modified":
                                modifMsgSelection = true;
                                break;
                            case "Unmodified":
                            default:
                                modifMsgSelection = false;
                                break;
                        }
                        displaySelectedShip.affichage(map, menuBar.getShips(), allSelectedShip, selectorModification, modifMsgSelection);
                        displaySelectedShip.getPanel().revalidate();
                        displaySelectedShip.getPanel().updateUI();
                        reloadMap();
                    }
                }
                if(e.getStateChange()== ItemEvent.DESELECTED){
                    testSelectMessageType=false;
                }
            }
        };
    }

    /**
     * set ItemListener for hard or propagation modification
     * @param allSelectedShip HashMap<String ,Ship>
     * @return ItemListener
     */
    private ItemListener selectionModification(final HashMap<String ,Ship> allSelectedShip){
        return new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()== ItemEvent.SELECTED){
                    if(!testSelectModification) {
                        testSelectModification = true;
                        Object item = e.getItem();
                        selectorModification = item.toString();
                        displaySelectedShip.affichage(map, menuBar.getShips(), allSelectedShip, selectorModification, modifMsgSelection);
                        displaySelectedShip.getPanel().revalidate();
                        displaySelectedShip.getPanel().updateUI();
                    }
                }
                if(e.getStateChange()== ItemEvent.DESELECTED){
                    testSelectModification=false;
                }
            }
        };
    }

    //*\\ Traveaux en cours //!\\
    private MouseListener selectShipOnMap(){
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton()==MouseEvent.BUTTON1){
                    //System.out.println("mouseClicked");
                    Point point=map.getMyMap().getMapPosition(e.getX(),e.getY());
                    if(point!=null) {
                        for (MapMarker mark : map.getMyMap().getMapMarkerList()) {
                            Coordinate coordinate=mark.getCoordinate();
                            Point point1=map.getMyMap().getTileController().getTileSource().latLonToXY(coordinate,map.getMyMap().getZoom());

                            //System.out.println("souris "+point+"\nship :"+point1);
                            if ((point.x-(int)coordinate.getLon()<=20)&&(point.y-(int)coordinate.getLat()<=20)) {
                                map.reloadMap(menuBar.getShips(), coordinate,modifMsgSelection);
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
class PopUp{
    static void warning(Component component,String label){
        JOptionPane onImport=new JOptionPane(label,JOptionPane.WARNING_MESSAGE);
        onImport.createDialog(component,"Warning").setVisible(true);
    }

    static void error(Component component,String label){
        JOptionPane onImport=new JOptionPane(label,JOptionPane.ERROR_MESSAGE);
        onImport.createDialog(component,"Error").setVisible(true);
    }

    static void information(Component component,String label){
        JOptionPane onImport=new JOptionPane(label,JOptionPane.INFORMATION_MESSAGE);
        onImport.createDialog(component,"Information").setVisible(true);
    }
}