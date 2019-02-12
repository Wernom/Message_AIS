package fr.ufc.l3info.projet;


import org.openstreetmap.gui.jmapviewer.Coordinate;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

class DisplaySelectedShip extends JPanel {

    private JPanel panel = new JPanel(new BorderLayout());
    private JPanel info=new JPanel(new BorderLayout());



// constructor
    /**
     * update ship information with selection
     */
   DisplaySelectedShip(){
        add(panel);
        info.setPreferredSize(new Dimension(0,400));
        initPanelInfo();
        panel.add(info,BorderLayout.CENTER);
   }

// getters
    /**
     * @return JPanel
     */
    JPanel getPanel() {
        return info;
    }

// initializer
    /**
     * initialize panel for display of all selected ship
     */
    private void initPanelInfo(){
       add(info,BorderLayout.CENTER);
       add(info,BorderLayout.SOUTH);
       Border border ;
       border = BorderFactory.createEtchedBorder();
       border = BorderFactory.createTitledBorder(border,"Ship Selection");
       info.setBorder(border);
   }

//displayer
    /**
     * reload information after validation or cancel
     * @param map Carte
     * @param trafic HashMap<String ,Ship>
     * @param selectedShip HashMap<String ,Ship>
     * @param modificationSelector String
     * @param modif boolean
     */
    void affichage( Carte map,  HashMap<String ,Ship> trafic, HashMap<String ,Ship> selectedShip, String modificationSelector, boolean modif){
       info.removeAll();
       final JTabbedPane tabbedPane=new JTabbedPane();
       for( Ship vessel:selectedShip.values()) {
           String mmsi= vessel.getMMSI();
           HashMap<String ,Message> allSelectedMessage=new HashMap<>();
           DisplayOneShip displayOneShip=new DisplayOneShip(map,trafic,allSelectedMessage,vessel,modificationSelector,modif );
           tabbedPane.addTab(mmsi,displayOneShip.getInfo());
           displayOneShip.getListDeroulante().addListSelectionListener(selectTimeMessageList(map,trafic,displayOneShip,allSelectedMessage,vessel,modificationSelector,modif));
       }
       info.add(tabbedPane,BorderLayout.CENTER);

        tabbedPane.setSelectedComponent(tabbedPane.getComponentAt(0));
        String mmsi=tabbedPane.getTitleAt(0);
        Ship ship=trafic.get(mmsi);
        centrageMap(map,trafic,ship,modif);
        tabbedPane.addChangeListener(centerMapListener(map,trafic,tabbedPane,modif));
   }

    /**
     * centre la map sur le navirre selectionné
     * @param map Carte
     * @param trafic HashMap<String ,Ship>
     * @param ship Ship
     * @param modif boolean
     */
   private void centrageMap(Carte map,HashMap<String ,Ship> trafic,Ship ship,boolean modif){
       if(!modif) {// centrage map when select ship in tab
           map.reloadMap(trafic,new Coordinate(ship.getLastKnownMessage().getDecode().getLatitude(), ship.getLastKnownMessage().getDecode().getLongitude()),modif);
       }else if (ship.getModifiedMessage().size() != 0){
           map.reloadMap(trafic,new Coordinate(ship.getLastKnownModifiedMessage().getDecode().getLatitude(), ship.getLastKnownModifiedMessage().getDecode().getLongitude()),modif);
       }
   }

    /**
     * select the message on the list
     * @param map Carte
     * @param trafic HashMap<String ,Ship>
     * @param displayOneShip DisplayOneShip
     * @param allSelectedMessage HashMap<String ,Message>
     * @param vessel Ship
     * @param modificationSelector String
     * @param modif boolean
     * @return ListSelectionListener
     */
    private  ListSelectionListener selectTimeMessageList(final Carte map, final HashMap<String ,Ship> trafic,final DisplayOneShip displayOneShip,final HashMap<String ,Message> allSelectedMessage,final Ship vessel,final String modificationSelector,final boolean modif){
        return new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    if(displayOneShip.getListDeroulante().getSelectedValue().toString().equals("<empty>")){
                        // do something ?
                        return;
                    }
                    List allMessage = displayOneShip.getListDeroulante().getSelectedValuesList();
                    if(allMessage.size()!=0) {
                        for (Object msgTime : allMessage) {
                            String time = msgTime.toString();
                            allSelectedMessage.put(vessel.getMMSI(), vessel.getMessages().get(time));
                        }
                    }else{
                        String time=displayOneShip.getListDeroulante().getSelectedValue().toString();
                        allSelectedMessage.put(vessel.getMMSI(), vessel.getMessages().get(time));
                    }
                    displayOneShip.reload(map,trafic,allSelectedMessage,vessel,modificationSelector,modif); // centrage map
                    displayOneShip.getInfo().updateUI();
                    displayOneShip.getInfo().revalidate();
                }
            }
        };
    }

    /**
     * center the map on the selected ship
     * @param map Carte
     * @param trafic HashMap<String ,Ship>
     * @param tabbedPane JTabbedPane
     * @param modif boolean
     * @return ChangeListener
     */
    private ChangeListener centerMapListener(final Carte map,final HashMap<String ,Ship> trafic,final JTabbedPane tabbedPane,final boolean modif){
        return new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                String mmsi=tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
                Ship ship=trafic.get(mmsi);
                centrageMap(map,trafic,ship,modif);
            }
        };
    }

}
