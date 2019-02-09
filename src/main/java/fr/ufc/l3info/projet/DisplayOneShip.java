package fr.ufc.l3info.projet;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

class DisplayOneShip extends JPanel{
    private JPanel listPan=new JPanel(new BorderLayout());
    private JPanel tabPan=new JPanel(new BorderLayout());
    private JPanel info=new JPanel(new BorderLayout());
    private JList listDeroulante;
    private DefaultListModel<String> defaultList;

    /**
     * constructor
     */
    DisplayOneShip(Ship vessel){
        initDisplay(vessel);

    }

    private void initDisplay(Ship ship){
        add(info,BorderLayout.WEST);
        add(info,BorderLayout.CENTER);
        initList(ship);
        info.add(listPan,BorderLayout.WEST);
        info.add(tabPan,BorderLayout.CENTER);
    }

    /**
     * initialize list of message
     */
    private void initList(Ship ship) {
        defaultList=new DefaultListModel<>();
        listDeroulante = new JList<>(defaultList);
        listDeroulante.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listDeroulante.setLayoutOrientation(JList.VERTICAL);

        for (Message msg:ship.getMessages().values()) {
            defaultList.addElement(msg.getDecode().getUTCString());
        }
        JScrollPane scrollPane=new JScrollPane(listDeroulante);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        listPan.add(scrollPane);
    }

    void reload(Carte map,HashMap<String,Ship> trafic, HashMap<String ,Message> selectedMessage,Ship ship){
        tabPan.removeAll();
        affichage(map,trafic,selectedMessage,ship);

    }

    private void affichage(final Carte map, final HashMap<String,Ship> trafic, HashMap<String, Message> selectedMessage, final Ship ship){

        final JTabbedPane tabbedPane=new JTabbedPane();

        for(final Message vessel:selectedMessage.values()) {
            DisplayOneMessage displayOneMessage = new DisplayOneMessage(vessel);
            displayOneMessage.getApplySingle().addActionListener(addApplySingleListener(map,trafic,selectedMessage,ship,displayOneMessage));
            displayOneMessage.getCancelSingle().addActionListener(addCancelAllListener(map,trafic,selectedMessage,ship));
            //displayOneMessage.getCancelAll().addActionListener(addCancelAllListener(map,trafic,selectedMessage,ship));
            //displayOneMessage.getApplyAll().addActionListener(addApplyAllListener(map,trafic,selectedMessage,ship,displayOneMessage));
            tabbedPane.addTab(vessel.getDecode().getUTCString(),displayOneMessage.getDisplay());
        }
        tabPan.add(tabbedPane);
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                String time=tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
                map.reloadMap(trafic,ship.getMMSI(),ship.getMessages().get( time));
            }
        });
        tabbedPane.setSelectedIndex(0);
        String time=tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
        map.reloadMap(trafic,ship.getMMSI(),ship.getMessages().get( time));

    }

    //listener
    /**
     * create an ActionListener for cancelAll Button
     * @return ActionListener
     */
    private ActionListener addCancelAllListener(final Carte map,final HashMap<String,Ship> trafic,final HashMap<String ,Message> selectedMessage,final Ship ship){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reload(map,trafic,selectedMessage,ship);
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
    private ActionListener addApplySingleListener(final Carte map,final HashMap<String,Ship> trafic,final HashMap<String ,Message> selectedMessage,final Ship ship,final DisplayOneMessage displayOneMessage){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayOneMessage.saveModificationOne(ship);
                affichage(map,trafic,selectedMessage,ship);
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
