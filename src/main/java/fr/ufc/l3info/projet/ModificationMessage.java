package fr.ufc.l3info.projet;

import javax.swing.*;
import java.awt.*;

class ModificationMessage extends JPanel {

   private JPanel panel = new JPanel(new BorderLayout());
   private JPanel info=new JPanel(new BorderLayout());

   ModificationMessage(){
        add(panel,BorderLayout.NORTH);
        add(panel,BorderLayout.CENTER);
        add(info,BorderLayout.NORTH);
        add(info,BorderLayout.CENTER);
        JLabel entete=new JLabel("  Message Body :");

        panel.add(entete,BorderLayout.NORTH);
        panel.add(info,BorderLayout.CENTER);
   }

   void affichage(Message ais){
        info.removeAll();
        MessageDecode vessel = ais.getDecode();

        JLabel AISraw=new JLabel(" Raw AIS : "+ais.getAis().getRawData());
        info.add(AISraw,BorderLayout.NORTH);

        JPanel panelValue=new JPanel();

        // messageType
        JLabel messageTypeLabel = new JLabel("Message Type");
        JTextField messageTypeText = new JTextField((vessel==null)?"":vessel.getMessageType());
        panelValue.add(initPanelInfo(messageTypeLabel,messageTypeText));

        //repeatIndicator
        JLabel repeatIndicatorLabel = new JLabel("Repeat Indicator");
        JTextField repeatIndicatorText = new JTextField((vessel==null)?"":vessel.getRepeatIndicator());
        panelValue.add(initPanelInfo(repeatIndicatorLabel,repeatIndicatorText));

        // MMSI
        JLabel MMSILabel = new JLabel("MMSI");
        JTextField MMSIText = new JTextField((vessel==null)?"":vessel.getMMSI());
        panelValue.add(initPanelInfo(MMSILabel,MMSIText));


        // navigationStatus
        JLabel navigationStatusLabel = new JLabel("Navigation Status");
        JTextField navigationStatusText = new JTextField((vessel==null)?"":vessel.getNavigationStatus());
        panelValue.add(initPanelInfo(navigationStatusLabel,navigationStatusText));

        // speedOverGround
        JLabel speedOverGroundLabel = new JLabel("Speed Over Ground");
        JTextField speedOverGroundText = new JTextField((vessel==null)?"":String.valueOf(vessel.getSpeedOverGround()));
        panelValue.add(initPanelInfo(speedOverGroundLabel,speedOverGroundText));

        // positiontionAccuracy
        JLabel positiontionAccuracyLabel = new JLabel("Position Accuracy");
        JTextField positiontionAccuracyText = new JTextField((vessel==null)?"":vessel.getPositiontionAccuracy());
        panelValue.add(initPanelInfo(positiontionAccuracyLabel,positiontionAccuracyText));

        // longitude
        JLabel longitudeLabel = new JLabel("Longitude");
        JTextField longitudeText = new JTextField((vessel==null)?"":String.valueOf(vessel.getLongitude()));
        panelValue.add(initPanelInfo(longitudeLabel,longitudeText));

        // latitude
        JLabel latitudeLabel = new JLabel("Latitude");
        JTextField latitudeText = new JTextField((vessel==null)?"":String.valueOf(vessel.getLatitude()));
        panelValue.add(initPanelInfo(latitudeLabel,latitudeText));

        // trueHeading
        JLabel trueHeadingLabel = new JLabel("True Heading");
        JTextField trueHeadingText = new JTextField((vessel==null)?"":vessel.getTrueHeading());
        panelValue.add(initPanelInfo(trueHeadingLabel,trueHeadingText));

        // timeStamp
        JLabel timeStampLabel = new JLabel("Time Stamp");
        JTextField timeStampText = new JTextField((vessel==null)?"":vessel.getTimeStamp());
        panelValue.add(initPanelInfo(timeStampLabel,timeStampText));

        // maneuverIndicator
        JLabel maneuverIndicatorLabel = new JLabel("Maneuver Indicator");
        JTextField maneuverIndicatorText = new JTextField((vessel==null)?"":vessel.getManeuverIndicator());
        panelValue.add(initPanelInfo(maneuverIndicatorLabel,maneuverIndicatorText));

        // spare
        JLabel spareLabel = new JLabel("Spare");
        JTextField spareText = new JTextField((vessel==null)?"":vessel.getSpare());
        panelValue.add(initPanelInfo(spareLabel,spareText));

        // RAIMflag
        JLabel RAIMflagLabel = new JLabel("RAIM Flag");
        JTextField RAIMflagText = new JTextField((vessel==null)?"":vessel.getRAIMflag());
        panelValue.add(initPanelInfo(RAIMflagLabel,RAIMflagText));

        // radioStatus
        JLabel radioStatusLabel = new JLabel("Radio Status");
        JTextField radioStatusText = new JTextField((vessel==null)?"":vessel.getRadioStatus());
        panelValue.add(initPanelInfo(radioStatusLabel,radioStatusText));

       info.add(panelValue,BorderLayout.CENTER);
   }

    private JPanel initPanelInfo(JLabel label, JTextField textField){
        JPanel returnPanel=new JPanel();
        returnPanel.add(label);
        returnPanel.add(textField);
        return returnPanel;
   }


    JPanel getPanel() {
        return panel;
    }
}
