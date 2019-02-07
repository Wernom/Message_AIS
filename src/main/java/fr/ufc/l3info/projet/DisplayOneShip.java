package fr.ufc.l3info.projet;

import javax.swing.*;
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
        listPan.add(listDeroulante);
    }

    void reload( HashMap<String ,Message> selectedMessage){
        tabPan.removeAll();
        affichage(selectedMessage);
    }

    private void affichage(HashMap<String, Message> selectedMessage){

        JTabbedPane tabbedPane=new JTabbedPane();

        for(final Message vessel:selectedMessage.values()) {
            DisplayOneMessage displayOneMessage = new DisplayOneMessage(vessel);
            displayOneMessage.getApplySingle().addActionListener(addApplySingleListener(selectedMessage,displayOneMessage,vessel.getDecode().getMMSI()));
            displayOneMessage.getCancelAll().addActionListener(addCancelAllListener(selectedMessage));
            displayOneMessage.getApplyAll().addActionListener(addApplyAllListener(selectedMessage,displayOneMessage));
            tabbedPane.addTab(vessel.getDecode().getUTCString(),displayOneMessage.getDisplay());
        }
        tabPan.add(tabbedPane);

    }

    //listener
    /**
     * create an ActionListener for cancelAll Button
     * @return ActionListener
     */
    private ActionListener addCancelAllListener(final HashMap<String ,Message> selectedMessage){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                affichage(selectedMessage);
                info.revalidate();
                info.updateUI();
            }
        };
    }

    /**
     * create an ActionListener for applyAll Button
     * @return ActionListener
     */
    private ActionListener addApplyAllListener(final HashMap<String ,Message> selectedMessage, final DisplayOneMessage displayOneMessage){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveModificationAll(selectedMessage,displayOneMessage);
                affichage(selectedMessage);
                info.revalidate();
                info.updateUI();
            }
        };
    }

    /**
     * create an ActionListener for applySingle Button
     * @return ActionListener
     */
    private ActionListener addApplySingleListener(final HashMap<String ,Message> selectedMessage,final DisplayOneMessage displayOneMessage,final String mmsi){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayOneMessage.saveModificationOne(selectedMessage.get(mmsi));
                affichage(selectedMessage);
                info.revalidate();
                info.updateUI();

            }
        };
    }

//modification save
    /**
     * Save information modification for all selected vessel
     */
    private void saveModificationAll(HashMap<String ,Message> selectedMessage,final DisplayOneMessage displayOneMessage){
        for(Message vessel:selectedMessage.values()) {
            displayOneMessage.saveModificationOne(vessel);
        }
    }

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
