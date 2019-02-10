package fr.ufc.l3info.projet;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.TreeMap;

class DisplayOneShip extends JPanel{
    private JPanel listPan=new JPanel(new BorderLayout());
    private JPanel tabPan=new JPanel(new BorderLayout());
    private JPanel info=new JPanel(new BorderLayout());
    private JList listDeroulante;
    private DefaultListModel<String> defaultList;

    /**
     * constructor
     */
    DisplayOneShip(Ship vessel,boolean modif){
        initDisplay();
        initList(vessel,modif);
    }

    private void initDisplay(){
        add(info,BorderLayout.WEST);
        add(info,BorderLayout.CENTER);
        info.add(listPan,BorderLayout.WEST);
        info.add(tabPan,BorderLayout.CENTER);
    }

    /**
     * initialize list of message
     */
    private void initList(Ship ship,boolean modif) {
        defaultList=new DefaultListModel<>();
        listDeroulante = new JList<>(defaultList);
        listDeroulante.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listDeroulante.setLayoutOrientation(JList.VERTICAL);
        TreeMap<String, Message> mapMessage;
        if(modif) {
            mapMessage = ship.getModifiedMessage();
        }else{
            mapMessage =ship.getMessages();
        }
        if(mapMessage.size()==0){
            defaultList.addElement("<empty>");
            listDeroulante.setSelectedValue("<empty>",false);
        }else{
            for (Message msg:mapMessage.values()) {
                defaultList.addElement(msg.getDecode().getUTCString());
            }
        }
        JScrollPane scrollPane=new JScrollPane(listDeroulante);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        listPan.setPreferredSize(new Dimension(60,0));
        listPan.add(scrollPane);
    }

    void reload(Carte map,HashMap<String,Ship> trafic, HashMap<String ,Message> selectedMessage,Ship ship,boolean modif){
        tabPan.removeAll();
        affichage(map,trafic,selectedMessage,ship,modif);

    }

    private void affichage(final Carte map, final HashMap<String,Ship> trafic, HashMap<String, Message> selectedMessage, final Ship ship,final boolean modif){

        final JTabbedPane tabbedPane=new JTabbedPane();
        final TreeMap<String, Message> mapMessage;
        if(modif) {
            mapMessage = ship.getModifiedMessage();
        }else{
            mapMessage =ship.getMessages();
        }
        for(final Message vessel:selectedMessage.values()) {

            DisplayOneMessage displayOneMessage = new DisplayOneMessage(vessel, modif);
            displayOneMessage.getApplySingle().addActionListener(addApplySingleListener(map, trafic, selectedMessage, ship, displayOneMessage, modif));
            displayOneMessage.getCancelSingle().addActionListener(addCancelAllListener(map, trafic, selectedMessage, ship, modif));
            //displayOneMessage.getCancelAll().addActionListener(addCancelAllListener(map,trafic,selectedMessage,ship));
            //displayOneMessage.getApplyAll().addActionListener(addApplyAllListener(map,trafic,selectedMessage,ship,displayOneMessage));
            tabbedPane.addTab(vessel.getDecode().getUTCString(), displayOneMessage.getDisplay());

        }
        tabPan.add(tabbedPane);
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                String time=tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
                map.reloadMap(trafic, ship.getMMSI(), mapMessage.get(time),modif);
            }
        });
        tabbedPane.setSelectedIndex(0);
        String time = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
        map.reloadMap(trafic, ship.getMMSI(), mapMessage.get(time),modif);
    }

    //listener
    /**
     * create an ActionListener for cancelAll Button
     * @return ActionListener
     */
    private ActionListener addCancelAllListener(final Carte map,final HashMap<String,Ship> trafic,final HashMap<String ,Message> selectedMessage,final Ship ship,final boolean modif){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reload(map,trafic,selectedMessage,ship,modif);
                info.revalidate();
                info.updateUI();
            }
        };
    }

    /**
     * create an ActionListener for applyAll Button
     * @return ActionListener
     */
   /* private ActionListener addApplyAllListener(final Carte map,final HashMap<String,Ship> trafic,final HashMap<String ,Message> selectedMessage,final Ship ship, final DisplayOneMessage displayOneMessage){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveModificationAll(selectedMessage,ship,displayOneMessage);
                reload(map,trafic,selectedMessage,ship);
                info.revalidate();
                info.updateUI();
            }
        };
    }/

    /**
     * create an ActionListener for applySingle Button
     * @return ActionListener
     */
    private ActionListener addApplySingleListener(final Carte map,final HashMap<String,Ship> trafic,final HashMap<String ,Message> selectedMessage,final Ship ship,final DisplayOneMessage displayOneMessage,final boolean modif){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayOneMessage.saveModificationOne(ship);
                reload(map,trafic,selectedMessage,ship,modif);
                info.revalidate();
                info.updateUI();
            }
        };
    }

//modification save
    ///**
    // * Save information modification for all selected vessel
    // */
   /* private void saveModificationAll(HashMap<String ,Message> selectedMessage,final Ship ship,final DisplayOneMessage displayOneMessage){
        for(Message vessel:selectedMessage.values()) {
            displayOneMessage.saveModificationOne(ship);
        }
    }*/

    /**
     * @return JList
     */
    JList getListDeroulante() {
        return listDeroulante;
    }

    /**
     * @return JPanel
     */
    JPanel getInfo() {
        return info;
    }

}
