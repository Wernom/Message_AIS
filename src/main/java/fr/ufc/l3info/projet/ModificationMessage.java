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
    private HashMap<String,DisplayOneShip> tabMap=new HashMap<>();


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

//displayer


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
           String mmsi= vessel.getDecode().getMMSI();
           DisplayOneShip displayOneShip=new DisplayOneShip(vessel);
           tabMap.put(mmsi,displayOneShip);
           tabbedPane.addTab(mmsi,displayOneShip.getDisplay());
           displayOneShip.getApplySingle().addActionListener(addApplySingleListener(allShip,mmsi));
           displayOneShip.getCancelAll().addActionListener(addCancelAllListener(allShip));
           displayOneShip.getApplyAll().addActionListener(addApplyAllListener(allShip));
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
                tabMap.get(mmsi).saveModificationOne(allShip.get(mmsi));
                reload(allShip);
                getPanel().revalidate();
                getPanel().updateUI();
                System.out.println("Modification done!");
            }
        };
    }

//modification save

    /**
     * Save information modification for all selected vessel
     */
    private void saveModificationAll(HashMap<String ,Message> allShip){
        for(Message vessel:allShip.values()) {
            tabMap.get(vessel.getDecode().getMMSI()).saveModificationOne(vessel);
        }
    }

}


class DisplayOneShip extends JPanel{

    private JTextField messageTypeText;
    private JTextField repeatIndicatorText;
    private JTextField MMSIText;
    private JTextField navigationStatusText;
    private JTextField speedOverGroundText;
    private JTextField positiontionAccuracyText;
    private JTextField longitudeText;
    private JTextField latitudeText;
    private JTextField courseOverGroudText;
    private JTextField trueHeadingText;
    private JTextField timeStampText;
    private JTextField maneuverIndicatorText;
    private JTextField spareText;
    private JTextField RAIMflagText;
    private JTextField radioStatusText;

    private JButton applyAll=new JButton();
    private JButton cancelAll=new JButton();
    private JButton applySingle =new JButton();
    private JButton cancelSingle=new JButton();
     private JPanel display=new JPanel(new BorderLayout());


     DisplayOneShip(Message ship){
         setDisplay(ship);
         getCancelSingle().addActionListener(addCancelSingleListener(ship));
     }

    /**
     * generate display of ship information
     * @param ship Message AIS data
     */
    private void setDisplay(Message ship){
        display.removeAll();
        add(display,BorderLayout.NORTH);
        add(display,BorderLayout.CENTER);
        add(display,BorderLayout.SOUTH);

        MessageDecode vessel = ship.getDecode();

        JLabel AISraw=new JLabel(" Raw AIS : "+ship.getAis().getRawData()); // display raw data
        display.add(AISraw,BorderLayout.NORTH);

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

        //courseOverGround
        JLabel courseOverGroudLabel = new JLabel("Course Over Ground");
        courseOverGroudText= new JTextField((vessel==null)?"":String.valueOf(vessel.getCourseOverGroud()));
        panelValue.add(initPanelValue(courseOverGroudLabel,courseOverGroudText));

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

        display.add(scrollPane,BorderLayout.CENTER);
        display.add(initButton((vessel==null)?"GhostShip":vessel.getMMSI()),BorderLayout.SOUTH);
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
    private JPanel initButton(String mmsi){
        JPanel panelButton=new JPanel();

        // set text
         applyAll.setText("Apply (all)");
         cancelAll.setText("Cancel (all)");
         applySingle.setText("Apply ("+mmsi+")");
         cancelSingle.setText("Cancel ("+mmsi+")");

        // color font
        cancelAll.setForeground(new Color(139, 0, 0));
        applyAll.setForeground(new Color(0, 114, 57));
        cancelSingle.setForeground(new Color(139, 0, 0));
        applySingle.setForeground(new Color(0, 114, 57));


        // add to panel
        panelButton.add(cancelAll);
        panelButton.add(applyAll);
        panelButton.add(cancelSingle);
        panelButton.add(applySingle);
        return panelButton;
    }

    /**
     * Save information modification for one selected vessel
     */
    void saveModificationOne(Message ship) {

        ship.getDecode().setMessageType((messageTypeText.getText()==null)?"0": messageTypeText.getText());
        ship.getDecode().setRepeatIndicator(repeatIndicatorText.getText()==null?"0":repeatIndicatorText.getText());
        ship.getDecode().setMMSI(MMSIText.getText()==null?"0":MMSIText.getText());
        ship.getDecode().setNavigationStatus(navigationStatusText.getText()==null?"0":navigationStatusText.getText());
        ship.getDecode().setSpeedOverGround(Double.parseDouble(speedOverGroundText.getText()==null?"0":speedOverGroundText.getText()));
        ship.getDecode().setPositiontionAccuracy(positiontionAccuracyText.getText()==null?"0":positiontionAccuracyText.getText());
        ship.getDecode().setLongitude(Double.parseDouble(longitudeText.getText()==null?"0":longitudeText.getText()));
        ship.getDecode().setLatitude(Double.parseDouble(latitudeText.getText()==null?"0":latitudeText.getText()));
        ship.getDecode().setCourseOverGroud(Double.parseDouble(courseOverGroudText.getText()==null?"0":courseOverGroudText.getText()));
        ship.getDecode().setTrueHeading(trueHeadingText.getText()==null?"0":trueHeadingText.getText());
        ship.getDecode().setTimeStamp(timeStampText.getText()==null?"0":timeStampText.getText());
        ship.getDecode().setManeuverIndicator(maneuverIndicatorText.getText()==null?"0":maneuverIndicatorText.getText());
        ship.getDecode().setSpare(spareText.getText()==null?"0":spareText.getText());
        ship.getDecode().setRAIMflag(RAIMflagText.getText()==null?"0":RAIMflagText.getText());
        ship.getDecode().setRadioStatus(radioStatusText.getText()==null?"0":radioStatusText.getText());
        ship.setAis();
    }

    /**
     * create an ActionListener for cancelSingle Button
     * @return ActionListener
     */
    private ActionListener addCancelSingleListener(final Message ship){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDisplay(ship);
                getDisplay().revalidate();
                getDisplay().updateUI();
            }
        };
    }

    /**
     * @return JButton
     */
    JButton getApplySingle() {
        return applySingle;
    }

    /**
     * @return JButton
     */
    private JButton getCancelSingle() {
        return cancelSingle;
    }

    /**
     * @return JButton
     */
    JButton getApplyAll() {
        return applyAll;
    }

    /**
     * @return JButton
     */
    JButton getCancelAll() {
        return cancelAll;
    }

    /**
     * @return JPanel
     */
    JPanel getDisplay() {
        return display;
    }
}