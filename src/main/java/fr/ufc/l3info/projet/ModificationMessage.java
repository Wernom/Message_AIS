package fr.ufc.l3info.projet;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

class ModificationMessage extends JPanel {

   private JPanel panel = new JPanel(new BorderLayout());
   private JPanel info=new JPanel(new BorderLayout());

    /**
     * update ship information with selection
     */
   ModificationMessage(){
        add(panel,BorderLayout.NORTH);
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
       //OneMessage.removeAll();
       JPanel OneMessage=new JPanel(new BorderLayout());
       add(OneMessage,BorderLayout.NORTH);
       add(OneMessage,BorderLayout.CENTER);



        MessageDecode vessel = ais.getDecode();

        JLabel AISraw=new JLabel(" Raw AIS : "+ais.getAis().getRawData());
       OneMessage.add(AISraw,BorderLayout.NORTH);

        JPanel panelValue=new JPanel();

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

       OneMessage.add(panelValue,BorderLayout.CENTER);
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
        returnPanel.add(textField);
        return returnPanel;
   }

    /**
     * display all tabs of selected ship
      * @param allShip ArrayList<Message>
     */
    void affichage(ArrayList<Message> allShip){
        info.removeAll();
        JTabbedPane tabbedPane=new JTabbedPane();
        for(Message vessel:allShip) {
            tabbedPane.addTab(vessel.getDecode().getMMSI(),affichageOneMessage(vessel));
        }

        info.add(tabbedPane,BorderLayout.CENTER);

   }




    /**
     * @return JPanel
     */
    JPanel getPanel() {
        return panel;
    }
}
