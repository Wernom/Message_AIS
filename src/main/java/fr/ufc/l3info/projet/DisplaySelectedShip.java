package fr.ufc.l3info.projet;


import javax.swing.*;
import javax.swing.border.Border;
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
        add(panel,BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(0,300));
        initPanelInfo();
        panel.add(info,BorderLayout.CENTER);
   }

// getters
    /**
     * @return JPanel
     */
    JPanel getPanel() {
        return panel;
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
     * display all tabs of selected ship
     * @param map Carte
     * @param trafic HashMap<String ,Ship>
     * @param selectedShip HashMap<String ,Ship>
     */
    void affichage( Carte map,HashMap<String ,Ship> trafic,HashMap<String ,Ship> selectedShip){
        reload(map,trafic,selectedShip);
   }

    /**
     * reload information after validation or cancel
     * @param map Carte
     * @param trafic HashMap<String ,Ship>
     * @param selectedShip HashMap<String ,Ship>
     */
    private void reload(final Carte map, final HashMap<String ,Ship> trafic, HashMap<String ,Ship> selectedShip){
       info.removeAll();
       JTabbedPane tabbedPane=new JTabbedPane();
       for(final Ship vessel:selectedShip.values()) {
           final String mmsi= vessel.getLastKnownMessage().getDecode().getMMSI();

           final DisplayOneShip displayOneShip=new DisplayOneShip(vessel);
           tabbedPane.addTab(mmsi,displayOneShip.getInfo());
           displayOneShip.getListDeroulante().addListSelectionListener(new ListSelectionListener() {
               @Override
               public void valueChanged(ListSelectionEvent e) {
                   if(!e.getValueIsAdjusting()){
                       List allMessage = displayOneShip.getListDeroulante().getSelectedValuesList();
                       HashMap<String ,Message> allSelectedMessage=new HashMap<>();
                       for(Object msgTime:allMessage){
                           allSelectedMessage.put(vessel.getMessages().get((String) msgTime).getDecode().getMMSI(),vessel.getMessages().get((String)msgTime));
                       }
                       String time= (String)displayOneShip.getListDeroulante().getSelectedValue();
                       map.reloadMap(trafic,mmsi,vessel.getMessages().get( time));
                       displayOneShip.reload(allSelectedMessage);
                       displayOneShip.getInfo().updateUI();
                       displayOneShip.getInfo().revalidate();
                   }
               }
           });
       }
       info.add(tabbedPane,BorderLayout.CENTER);
   }




}
