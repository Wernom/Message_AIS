package fr.ufc.l3info.projet;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class ModificationMessage extends JPanel {

   private JPanel panel = new JPanel(new BorderLayout());
   private JPanel info=new JPanel();

   ModificationMessage(){
        add(panel,BorderLayout.NORTH);
        add(panel,BorderLayout.SOUTH);
       add(panel,BorderLayout.CENTER);
        JLabel entete=new JLabel("  Corps du message :");
        panel.add(entete,BorderLayout.NORTH);
        panel.add(info,BorderLayout.CENTER);
   }

    MessageDecode getInformation(ArrayList<Message> msg,String select){
       if (msg!=null) {
           for (Message vessel : msg) {
               if (vessel.getDecode().getMMSI() == select) {
                   return vessel.getDecode();
               }
           }
       }
       return null;
   }

   void affichage(MessageDecode vessel){
       // Label
        JLabel messageTypeLabel = new JLabel("Message Type");
        JLabel repeatIndicatorLabel = new JLabel("Repeat Indicator");
        JLabel MMSILabel = new JLabel("MMSI");
        JLabel navigationStatusLabel = new JLabel("Navigation Status");
        JLabel speedOverGroundLabel = new JLabel("Speed Over Ground");
        JLabel positiontionAccuracyLabel = new JLabel("Position Accuracy");
        JLabel longitudeLabel = new JLabel("Longitude");
        JLabel latitudeLabel = new JLabel("Latitude");
        JLabel trueHeadingLabel = new JLabel("True Heading");
        JLabel timeStampLabel = new JLabel("Time Stamp");
        JLabel maneuverIndicatorLabel = new JLabel("Maneuver Indicator");
        JLabel spareLabel = new JLabel("Spare");
        JLabel RAIMflagLabel = new JLabel("RAIM Flag");
        JLabel radioStatusLabel = new JLabel("Radio Status");

        // TextField
       JTextField messageTypeText = new JTextField((vessel==null)?"":vessel.getMessageType());
       JTextField repeatIndicatorText = new JTextField((vessel==null)?"":vessel.getRepeatIndicator());
       JTextField MMSIText = new JTextField((vessel==null)?"":vessel.getMMSI());
       JTextField navigationStatusText = new JTextField((vessel==null)?"":vessel.getNavigationStatus());
       JTextField speedOverGroundText = new JTextField((vessel==null)?"":String.valueOf(vessel.getSpeedOverGround()));
       JTextField positiontionAccuracyText = new JTextField((vessel==null)?"":vessel.getPositiontionAccuracy());
       JTextField longitudeText = new JTextField((vessel==null)?"":String.valueOf(vessel.getLongitude()));
       JTextField latitudeText = new JTextField((vessel==null)?"":String.valueOf(vessel.getLatitude()));
       JTextField trueHeadingText = new JTextField((vessel==null)?"":vessel.getTrueHeading());
       JTextField timeStampText = new JTextField((vessel==null)?"":vessel.getTimeStamp());
       JTextField maneuverIndicatorText = new JTextField((vessel==null)?"":vessel.getManeuverIndicator());
       JTextField spareText = new JTextField((vessel==null)?"":vessel.getSpare());
       JTextField RAIMflagText = new JTextField((vessel==null)?"":vessel.getRAIMflag());
       JTextField radioStatusText = new JTextField((vessel==null)?"":vessel.getRadioStatus());

       info.removeAll();

       info.add(messageTypeLabel);
       info.add(messageTypeText);
       info.add(repeatIndicatorLabel);
       info.add(repeatIndicatorText);
       info.add(MMSILabel);
       info.add(MMSIText);
       info.add(navigationStatusLabel);
       info.add(navigationStatusText);
       info.add(speedOverGroundLabel);
       info.add(speedOverGroundText);
       info.add(positiontionAccuracyLabel);
       info.add(positiontionAccuracyText);
       info.add(longitudeLabel);
       info.add(longitudeText);
       info.add(latitudeLabel);
       info.add(latitudeText);
       info.add(trueHeadingLabel);
       info.add(trueHeadingText);
       info.add(timeStampLabel);
       info.add(timeStampText);
       info.add(maneuverIndicatorLabel);
       info.add(maneuverIndicatorText);
       info.add(spareLabel);
       info.add(spareText);
       info.add(RAIMflagLabel);
       info.add(RAIMflagText);
       info.add(radioStatusLabel);
       info.add(radioStatusText);
   }

    JPanel getPanel() {
        return panel;
    }
}
