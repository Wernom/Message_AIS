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
      * @param selectedShip ArrayList<Message>
     */
    void affichage( HashMap<String ,Ship> selectedShip){
        reload(selectedShip);
   }

    /**
     * reload information after validation or cancel
     */
    private void reload( HashMap<String ,Ship> selectedShip){
       info.removeAll();
       JTabbedPane tabbedPane=new JTabbedPane();
       for(final Ship vessel:selectedShip.values()) {
           String mmsi= vessel.getLastKnownMessage().getDecode().getMMSI();

           final DisplayOneShip displayOneShip=new DisplayOneShip(vessel);
           tabbedPane.addTab(mmsi,displayOneShip.getInfo());
           displayOneShip.getListDeroulante().addListSelectionListener(new ListSelectionListener() {
               @Override
               public void valueChanged(ListSelectionEvent e) {
                   if(!e.getValueIsAdjusting()){

                       List allMessage = displayOneShip.getListDeroulante().getSelectedValuesList();
                       HashMap<String ,Message> allSelectedMessage=new HashMap<>();
                       for(Object msgTime:allMessage){
                           allSelectedMessage.put(vessel.getMessages().get(msgTime).getDecode().getMMSI(),vessel.getMessages().get(msgTime));
                       }
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
