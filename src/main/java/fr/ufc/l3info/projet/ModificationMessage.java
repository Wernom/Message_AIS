package fr.ufc.l3info.projet;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

class ModificationMessage extends JPanel {

   private JPanel panel = new JPanel(new BorderLayout());
   private JPanel info=new JPanel(new BorderLayout());
   private JButton validate= new JButton("Validate");
   private JButton cancel= new JButton("Cancel");
   private ArrayList<Message> allSelectedShip=new ArrayList<>();

    /**
     * update ship information with selection
     */
   ModificationMessage(){
        add(panel,BorderLayout.CENTER);

       initPanelInfo();
        panel.add(info,BorderLayout.CENTER);
   }

    /**
     * initialize panel for display of all selected ship
     */
    private void initPanelInfo(){
       add(info,BorderLayout.CENTER);
       Border border ;
       border = BorderFactory.createEtchedBorder();
       border = BorderFactory.createTitledBorder(border,"Ship Selection");
       info.setBorder(border);

   }

    /**
     * generate display of ship information
     * @param ais Message AIS data
     */
    private JPanel affichageOneMessage(Message ais){
       JPanel OneMessage=new JPanel(new BorderLayout());
       add(OneMessage,BorderLayout.NORTH);
       add(OneMessage,BorderLayout.CENTER);
       add(OneMessage,BorderLayout.SOUTH);



        MessageDecode vessel = ais.getDecode();

        JLabel AISraw=new JLabel(" Raw AIS : "+ais.getAis().getRawData());
       OneMessage.add(AISraw,BorderLayout.NORTH);

        JPanel panelValue=new JPanel(new GridLayout(3,5));
        JScrollPane scrollPane=new JScrollPane(panelValue);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);


        // messageType
        JLabel messageTypeLabel = new JLabel("Message Type");
        JTextField messageTypeText = new JTextField((vessel==null)?"":vessel.getMessageType());
        panelValue.add(initPanelValue(messageTypeLabel,messageTypeText));

        //repeatIndicator
        JLabel repeatIndicatorLabel = new JLabel("Repeat Indicator");
        JTextField repeatIndicatorText = new JTextField((vessel==null)?"":vessel.getRepeatIndicator());
        panelValue.add(initPanelValue(repeatIndicatorLabel,repeatIndicatorText));

        // MMSI
        JLabel MMSILabel = new JLabel("MMSI");
        JTextField MMSIText = new JTextField((vessel==null)?"":vessel.getMMSI());
        panelValue.add(initPanelValue(MMSILabel,MMSIText));


        // navigationStatus
        JLabel navigationStatusLabel = new JLabel("Navigation Status");
        JTextField navigationStatusText = new JTextField((vessel==null)?"":vessel.getNavigationStatus());
        panelValue.add(initPanelValue(navigationStatusLabel,navigationStatusText));

        // speedOverGround
        JLabel speedOverGroundLabel = new JLabel("Speed Over Ground");
        JTextField speedOverGroundText = new JTextField((vessel==null)?"":String.valueOf(vessel.getSpeedOverGround()));
        panelValue.add(initPanelValue(speedOverGroundLabel,speedOverGroundText));

        // positiontionAccuracy
        JLabel positiontionAccuracyLabel = new JLabel("Position Accuracy");
        JTextField positiontionAccuracyText = new JTextField((vessel==null)?"":vessel.getPositiontionAccuracy());
        panelValue.add(initPanelValue(positiontionAccuracyLabel,positiontionAccuracyText));

        // longitude
        JLabel longitudeLabel = new JLabel("Longitude");
        JTextField longitudeText = new JTextField((vessel==null)?"":String.valueOf(vessel.getLongitude()));
        panelValue.add(initPanelValue(longitudeLabel,longitudeText));

        // latitude
        JLabel latitudeLabel = new JLabel("Latitude");
        JTextField latitudeText = new JTextField((vessel==null)?"":String.valueOf(vessel.getLatitude()));
        panelValue.add(initPanelValue(latitudeLabel,latitudeText));

        // trueHeading
        JLabel trueHeadingLabel = new JLabel("True Heading");
        JTextField trueHeadingText = new JTextField((vessel==null)?"":vessel.getTrueHeading());
        panelValue.add(initPanelValue(trueHeadingLabel,trueHeadingText));

        // timeStamp
        JLabel timeStampLabel = new JLabel("Time Stamp");
        JTextField timeStampText = new JTextField((vessel==null)?"":vessel.getTimeStamp());
        panelValue.add(initPanelValue(timeStampLabel,timeStampText));

        // maneuverIndicator
        JLabel maneuverIndicatorLabel = new JLabel("Maneuver Indicator");
        JTextField maneuverIndicatorText = new JTextField((vessel==null)?"":vessel.getManeuverIndicator());
        panelValue.add(initPanelValue(maneuverIndicatorLabel,maneuverIndicatorText));

        // spare
        JLabel spareLabel = new JLabel("Spare");
        JTextField spareText = new JTextField((vessel==null)?"":vessel.getSpare());
        panelValue.add(initPanelValue(spareLabel,spareText));

        // RAIMflag
        JLabel RAIMflagLabel = new JLabel("RAIM Flag");
        JTextField RAIMflagText = new JTextField((vessel==null)?"":vessel.getRAIMflag());
        panelValue.add(initPanelValue(RAIMflagLabel,RAIMflagText));

        // radioStatus
        JLabel radioStatusLabel = new JLabel("Radio Status");
        JTextField radioStatusText = new JTextField((vessel==null)?"":vessel.getRadioStatus());
        panelValue.add(initPanelValue(radioStatusLabel,radioStatusText));



       OneMessage.add(scrollPane,BorderLayout.CENTER);
       OneMessage.add(setupButton(),BorderLayout.SOUTH);
       return OneMessage;
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
     * display all tabs of selected ship
      * @param allShip ArrayList<Message>
     */
    void affichage(ArrayList<Message> allShip){
        this.allSelectedShip=allShip;
        reload();
   }

   void reload(){
       info.removeAll();
       JTabbedPane tabbedPane=new JTabbedPane();
       for(Message vessel:allSelectedShip) {
           tabbedPane.addTab(vessel.getDecode().getMMSI(),affichageOneMessage(vessel));
       }

       info.add(tabbedPane,BorderLayout.CENTER);
   }

    /**
     * setup cancel and validate modification button
     * @return JPanel
     */
    private JPanel setupButton(){
        JPanel panelButton=new JPanel();
        cancel.setForeground(new Color(139, 0, 0));
        validate.setForeground(new Color(0, 114, 57));

        panelButton.add(cancel);
        panelButton.add(validate);
        return panelButton;
    }


    /**
     * @return JPanel
     */
    JPanel getPanel() {
        return panel;
    }

    /**
     * @return JButton
     */
    JButton getCancelButton(){
        return cancel;
    }

    /**
     * @return JButton
     */
    JButton getValidateButton(){
        return cancel;
    }
}
