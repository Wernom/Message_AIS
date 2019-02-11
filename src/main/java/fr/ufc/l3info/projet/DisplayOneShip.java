package fr.ufc.l3info.projet;


import javax.swing.*;
import javax.swing.border.Border;
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
    DisplayOneShip( Carte map, HashMap<String,Ship> trafic, HashMap<String, Message> selectedMessage,Ship vessel,String modificationSelector,boolean modif){
        initDisplay();
        initList(vessel,modif);
        affichage(map,trafic,selectedMessage,vessel,modificationSelector,modif);
    }

    /**
     * initialize display of info for one ship
     */
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
        listDeroulante.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listDeroulante.setLayoutOrientation(JList.VERTICAL);
        TreeMap<String, Message> mapMessage=new TreeMap<>();
        String selection;

            if (modif) {
                if(ship.getModifiedMessage().size()!=0) {
                    mapMessage = ship.getModifiedMessage();
                    selection = ship.getLastKnownModifiedMessage().getDecode().getUTCString();
                }else{
                    selection="<empty>";
                }
            } else {
                mapMessage = ship.getMessages();
                selection = ship.getLastKnownMessage().getDecode().getUTCString();
            }
            if(mapMessage.size()==0){
                defaultList.addElement("<empty>");
                listDeroulante.setSelectedValue("<empty>",false);
            }else{
                for (Message msg:mapMessage.values()) {
                    defaultList.addElement(msg.getDecode().getUTCString());
                }
                listDeroulante.setSelectedValue(selection,false);
            }
        JScrollPane scrollPane=new JScrollPane(listDeroulante);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        Border border;
        border = BorderFactory.createEtchedBorder();
        scrollPane.setBorder(border);
        listPan.setPreferredSize(new Dimension(60,0));
        listPan.add(scrollPane);
    }

    /**
     * display info of one ship
     * @param map Carte
     * @param trafic HashMap<String,Ship>
     * @param selectedMessage HashMap<String, Message>
     * @param ship Ship
     * @param modificationSelector String
     * @param modif boolean
     */
    void affichage(final Carte map, final HashMap<String,Ship> trafic, HashMap<String, Message> selectedMessage, final Ship ship,String modificationSelector,final boolean modif){

        final JTabbedPane tabbedPane=new JTabbedPane();
        final TreeMap<String, Message> mapMessage;
        if(modif) {
            mapMessage = ship.getModifiedMessage();
        }else{
            mapMessage =ship.getMessages();
        }
        if(selectedMessage.size()!=0) {
            for (final Message vessel : selectedMessage.values()) {

                DisplayOneMessage displayOneMessage = new DisplayOneMessage(vessel, modificationSelector, modif);
                displayOneMessage.getApplySingle().addActionListener(addApplySingleListener(map, trafic, selectedMessage, ship, displayOneMessage, modificationSelector, modif));
                displayOneMessage.getCancelSingle().addActionListener(addCancelAllListener(map, trafic, selectedMessage, ship, modificationSelector, modif));
                //displayOneMessage.getCancelAll().addActionListener(addCancelAllListener(map,trafic,selectedMessage,ship));
                //displayOneMessage.getApplyAll().addActionListener(addApplyAllListener(map,trafic,selectedMessage,ship,displayOneMessage));
                tabbedPane.addTab(vessel.getDecode().getUTCString(), displayOneMessage.getDisplay());

            }
        }else{
            if(mapMessage.size()!=0) {
                DisplayOneMessage displayOneMessage = new DisplayOneMessage(mapMessage.get(mapMessage.lastKey()), modificationSelector, modif);
                displayOneMessage.getApplySingle().addActionListener(addApplySingleListener(map, trafic, selectedMessage, ship, displayOneMessage, modificationSelector, modif));
                displayOneMessage.getCancelSingle().addActionListener(addCancelAllListener(map, trafic, selectedMessage, ship, modificationSelector, modif));
                //displayOneMessage.getCancelAll().addActionListener(addCancelAllListener(map,trafic,selectedMessage,ship));
                //displayOneMessage.getApplyAll().addActionListener(addApplyAllListener(map,trafic,selectedMessage,ship,displayOneMessage));
                tabbedPane.addTab(mapMessage.get(mapMessage.lastKey()).getDecode().getUTCString(), displayOneMessage.getDisplay());
            }
        }
        tabPan.add(tabbedPane);
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                String time=tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
                map.reloadMap(trafic, ship.getMMSI(), mapMessage.get(time),modif);
            }
        });
        if(mapMessage.size()!=0) {
            tabbedPane.setSelectedIndex(0);
            String time = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
            map.reloadMap(trafic, ship.getMMSI(), mapMessage.get(time),modif);
        }
    }

    //listener
    /**
     * create an ActionListener for cancelAll Button
     * @param map Carte
     * @param trafic  HashMap<String,Ship>
     * @param selectedMessage HashMap<String ,Message>
     * @param ship Ship
     * @param modificationSelector String
     * @param modif boolean
     * @return ActionListener
     */
    private ActionListener addCancelAllListener(final Carte map,final HashMap<String,Ship> trafic,final HashMap<String ,Message> selectedMessage,final Ship ship,final String modificationSelector,final boolean modif){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                affichage(map,trafic,selectedMessage,ship,modificationSelector,modif);
                info.revalidate();
                info.updateUI();
            }
        };
    }
/*
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
*/

    /**
     * create an ActionListener for applySingle Button
     * @param map Carte
     * @param trafic  HashMap<String,Ship>
     * @param selectedMessage HashMap<String ,Message>
     * @param ship Ship
     * @param displayOneMessage DisplayOneMessage
     * @param modificationSelector String
     * @param modif boolean
     * @return ActionListener
     */
    private ActionListener addApplySingleListener(final Carte map,final HashMap<String,Ship> trafic,final HashMap<String ,Message> selectedMessage,final Ship ship,final DisplayOneMessage displayOneMessage,final String modificationSelector,final boolean modif){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch(modificationSelector){
                    case "Propagation":
                        displayOneMessage.saveModificationPropagationOne(ship);
                        break;
                    case "Hard":
                    default:
                        displayOneMessage.saveModificationHardOne(ship);
                        break;
                }
                affichage(map,trafic,selectedMessage,ship,modificationSelector,modif);
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
