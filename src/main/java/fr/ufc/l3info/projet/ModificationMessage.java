package fr.ufc.l3info.projet;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

class ModificationMessage extends JPanel {

    private JPanel panel = new JPanel(new BorderLayout());
    private JPanel info=new JPanel(new BorderLayout());
    private HashMap<String,Message> allSelectedShip;

    private JTextField messageTypeText;
    private JTextField repeatIndicatorText;
    private JTextField MMSIText;
    private JTextField navigationStatusText;
    private JTextField speedOverGroundText;
    private JTextField positiontionAccuracyText;
    private JTextField longitudeText;
    private JTextField latitudeText;
    private JTextField trueHeadingText;
    private JTextField timeStampText;
    private JTextField maneuverIndicatorText;
    private JTextField spareText;
    private JTextField RAIMflagText;
    private JTextField radioStatusText;


// construcor
    /**
     * update ship information with selection
     */
   ModificationMessage(){
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

    /**
     *  initialize one of attribut information
     * @param label JLabel
     * @param textField JtextField
     * @return JPanel
     */
    private JPanel initPanelValue(JLabel label, JTextField textField){
        JPanel returnPanel=new JPanel();
        returnPanel.add(label);
        textField.setColumns(5);
        returnPanel.add(textField);
        return returnPanel;
    }

    /**
     * setup cancel and validate modification button
     * @return JPanel
     */
    private JPanel initButton(HashMap<String ,Message> allShip,String mmsi){
        JPanel panelButton=new JPanel();

        // set text
        JButton applyAll=new JButton("Apply (all)");
        JButton cancelAll=new JButton("Cancel (all)");
        JButton applySingle =new JButton("Apply ("+mmsi+")");
        JButton cancelSingle=new JButton("Cancel ("+mmsi+")");

        // color font
        cancelAll.setForeground(new Color(139, 0, 0));
        applyAll.setForeground(new Color(0, 114, 57));
        cancelSingle.setForeground(new Color(139, 0, 0));
        applySingle.setForeground(new Color(0, 114, 57));

        //add listener
        cancelAll.addActionListener(addCancelAllListener(allShip));
        applyAll.addActionListener(addApplyAllListener(allShip));
        applySingle.addActionListener(addApplySingleListener(allShip,mmsi));

        // add to panel
        panelButton.add(cancelAll);
        //panelButton.add(applyAll);
        //panelButton.add(cancelSingle);
        panelButton.add(applySingle);
        return panelButton;
    }

//displayer
    /**
     * generate display of ship information
     * @param ais Message AIS data
     */
    private JPanel affichageOneMessage(HashMap<String ,Message> allShip,Message ais){
       JPanel OneMessage=new JPanel(new BorderLayout());
       add(OneMessage,BorderLayout.NORTH);
       add(OneMessage,BorderLayout.CENTER);
       add(OneMessage,BorderLayout.SOUTH);

        MessageDecode vessel = ais.getDecode();

        JLabel AISraw=new JLabel(" Raw AIS : "+ais.getAis().getRawData()); // display raw data
       OneMessage.add(AISraw,BorderLayout.NORTH);

        JPanel panelValue=new JPanel(new GridLayout(3,5));
        JScrollPane scrollPane=new JScrollPane(panelValue); // for reponsive design
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);


        // messageType
        JLabel messageTypeLabel = new JLabel("Message Type");
        messageTypeText = new JTextField((vessel==null)?"":vessel.getMessageType());
        panelValue.add(initPanelValue(messageTypeLabel,messageTypeText));

        //repeatIndicator
        JLabel repeatIndicatorLabel = new JLabel("Repeat Indicator");
        repeatIndicatorText = new JTextField((vessel==null)?"":vessel.getRepeatIndicator());
        panelValue.add(initPanelValue(repeatIndicatorLabel,repeatIndicatorText));

        // MMSI
        JLabel MMSILabel = new JLabel("MMSI");
        MMSIText = new JTextField((vessel==null)?"":vessel.getMMSI());
        panelValue.add(initPanelValue(MMSILabel,MMSIText));


        // navigationStatus
        JLabel navigationStatusLabel = new JLabel("Navigation Status");
        navigationStatusText = new JTextField((vessel==null)?"":vessel.getNavigationStatus());
        panelValue.add(initPanelValue(navigationStatusLabel,navigationStatusText));

        // speedOverGround
        JLabel speedOverGroundLabel = new JLabel("Speed Over Ground");
        speedOverGroundText = new JTextField((vessel==null)?"":String.valueOf(vessel.getSpeedOverGround()));
        panelValue.add(initPanelValue(speedOverGroundLabel,speedOverGroundText));

        // positiontionAccuracy
        JLabel positiontionAccuracyLabel = new JLabel("Position Accuracy");
        positiontionAccuracyText = new JTextField((vessel==null)?"":vessel.getPositiontionAccuracy());
        panelValue.add(initPanelValue(positiontionAccuracyLabel,positiontionAccuracyText));

        // longitude
        JLabel longitudeLabel = new JLabel("Longitude");
        longitudeText = new JTextField((vessel==null)?"":String.valueOf(vessel.getLongitude()));
        panelValue.add(initPanelValue(longitudeLabel,longitudeText));

        // latitude
        JLabel latitudeLabel = new JLabel("Latitude");
        latitudeText = new JTextField((vessel==null)?"":String.valueOf(vessel.getLatitude()));
        panelValue.add(initPanelValue(latitudeLabel,latitudeText));

        // trueHeading
        JLabel trueHeadingLabel = new JLabel("True Heading");
        trueHeadingText = new JTextField((vessel==null)?"":vessel.getTrueHeading());
        panelValue.add(initPanelValue(trueHeadingLabel,trueHeadingText));

        // timeStamp
        JLabel timeStampLabel = new JLabel("Time Stamp");
        timeStampText = new JTextField((vessel==null)?"":vessel.getTimeStamp());
        panelValue.add(initPanelValue(timeStampLabel,timeStampText));

        // maneuverIndicator
        JLabel maneuverIndicatorLabel = new JLabel("Maneuver Indicator");
        maneuverIndicatorText = new JTextField((vessel==null)?"":vessel.getManeuverIndicator());
        panelValue.add(initPanelValue(maneuverIndicatorLabel,maneuverIndicatorText));

        // spare
        JLabel spareLabel = new JLabel("Spare");
        spareText = new JTextField((vessel==null)?"":vessel.getSpare());
        panelValue.add(initPanelValue(spareLabel,spareText));

        // RAIMflag
        JLabel RAIMflagLabel = new JLabel("RAIM Flag");
        RAIMflagText = new JTextField((vessel==null)?"":vessel.getRAIMflag());
        panelValue.add(initPanelValue(RAIMflagLabel,RAIMflagText));

        // radioStatus
        JLabel radioStatusLabel = new JLabel("Radio Status");
        radioStatusText = new JTextField((vessel==null)?"":vessel.getRadioStatus());
        panelValue.add(initPanelValue(radioStatusLabel,radioStatusText));



       OneMessage.add(scrollPane,BorderLayout.CENTER);
       OneMessage.add(initButton(allShip,(vessel==null)?"GhostShip":vessel.getMMSI()),BorderLayout.SOUTH);
       return OneMessage;
   }

    /**
     * display all tabs of selected ship
      * @param allShip ArrayList<Message>
     */
    void affichage(HashMap<String ,Message> allShip){
        reload(allShip);
   }

    /**
     * reload information after validation or cancel
     */
    private void reload(HashMap<String ,Message> allShip){
       info.removeAll();
       JTabbedPane tabbedPane=new JTabbedPane();
       for(Message vessel:allShip.values()) {
           tabbedPane.addTab(vessel.getDecode().getMMSI(),affichageOneMessage(allShip,vessel));
       }
       info.add(tabbedPane,BorderLayout.CENTER);
   }

//listener
    /**
     * create an ActionListener for cancelAll Button
     * @return ActionListener
     */
    private ActionListener addCancelAllListener(final HashMap<String ,Message> allShip){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reload(allShip);
                getPanel().revalidate();
                getPanel().updateUI();
            }
        };
    }

    /**
     * create an ActionListener for applyAll Button
     * @return ActionListener
     */
    private ActionListener addApplyAllListener(final HashMap<String ,Message> allShip){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveModificationAll(allShip);
                reload(allShip);
                getPanel().revalidate();
                getPanel().updateUI();
            }
        };
    }

    /**
     * create an ActionListener for applySingle Button
     * @return ActionListener
     */
    private ActionListener addApplySingleListener(final HashMap<String ,Message> allShip, final String mmsi){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveModificationOne(allShip.get(mmsi).getDecode());
                reload(allShip);
                getPanel().revalidate();
                getPanel().updateUI();
                System.out.println("Modification done!");
            }
        };
    }

//modification save
    /**
     * Save information modification for one selected vessel
     */
    private void saveModificationOne(MessageDecode ship) {
        ship.setMessageType(messageTypeText.getText());
        ship.setRepeatIndicator(repeatIndicatorText.getText());
        ship.setMMSI(MMSIText.getText());
        ship.setNavigationStatus(navigationStatusText.getText());
        ship.setSpeedOverGround(Double.parseDouble(speedOverGroundText.getText()));
        ship.setPositiontionAccuracy(positiontionAccuracyText.getText());
        ship.setLongitude(Double.parseDouble(longitudeText.getText()));
        ship.setLatitude(Double.parseDouble(latitudeText.getText()));
        ship.setTrueHeading(trueHeadingText.getText());
        ship.setTimeStamp(timeStampText.getText());
        ship.setManeuverIndicator(maneuverIndicatorText.getText());
        ship.setSpare(spareText.getText());
        ship.setRAIMflag(RAIMflagText.getText());
        ship.setRadioStatus(radioStatusText.getText());
    }

    /**
     * Save information modification for all selected vessel
     */
    private void saveModificationAll(HashMap<String ,Message> allShip){
        for(Message vessel:allShip.values()) {
            saveModificationOne(vessel.getDecode());
        }
    }
}
