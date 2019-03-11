package fr.ufc.l3info.projet;


import javax.swing.*;
import javax.swing.border.Border;
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
     * reload info message after selection
     * @param map Carte
     * @param trafic  HashMap<String,Ship>
     * @param selectedMessage  HashMap<String, Message>
     * @param vessel Ship
     * @param modificationSelector String
     * @param modif boolean
     */
    void reload(Carte map, HashMap<String,Ship> trafic, HashMap<String, Message> selectedMessage,Ship vessel,String modificationSelector,boolean modif){
        tabPan.removeAll();
        affichage(map,trafic,selectedMessage,vessel,modificationSelector,modif);
        tabPan.revalidate();
        tabPan.updateUI();
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
    private void affichage(final Carte map, final HashMap<String, Ship> trafic, HashMap<String, Message> selectedMessage, final Ship ship, String modificationSelector, final boolean modif){

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
                displayOneMessage.getApply().addActionListener(addApplySingleListener(map, trafic, selectedMessage, ship, displayOneMessage, modificationSelector, modif));
                displayOneMessage.getCancel().addActionListener(addCancelAllListener(map, trafic, selectedMessage, ship, modificationSelector, modif));

                tabbedPane.addTab(vessel.getDecode().getUTCString(), displayOneMessage.getDisplay());
            }
        }else{
            if(mapMessage.size()!=0) {
                DisplayOneMessage displayOneMessage = new DisplayOneMessage(mapMessage.get(mapMessage.lastKey()), modificationSelector, modif);
                displayOneMessage.getApply().addActionListener(addApplySingleListener(map, trafic, selectedMessage, ship, displayOneMessage, modificationSelector, modif));
                displayOneMessage.getCancel().addActionListener(addCancelAllListener(map, trafic, selectedMessage, ship, modificationSelector, modif));

                tabbedPane.addTab(mapMessage.get(mapMessage.lastKey()).getDecode().getUTCString(), displayOneMessage.getDisplay());
            }
        }
        tabPan.add(tabbedPane);
        if(mapMessage.size()>0 && tabbedPane.getTabCount()>0) {
            String time = tabbedPane.getTitleAt(0);
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
