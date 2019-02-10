package fr.ufc.l3info.projet;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

class DisplayOneMessage extends JPanel {

    // ais info
    private JTextField messageTypeText;
    private JTextField repeatIndicatorText;
    private JTextField MMSIText;
    private JTextField navigationStatusText;
    private JTextField rateOverTurnText;
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
    private JTextField hoursText;
    private JTextField minutesText;

    //time interval JTextField
    private JTextField hourFrom=new JTextField();
    private JTextField minuteFrom=new JTextField();
    private JTextField hourTo=new JTextField();
    private JTextField minuteTo=new JTextField();

    // button
    private JButton applyAll=new JButton();
    private JButton cancelAll=new JButton();
    private JButton applySingle =new JButton();
    private JButton cancelSingle=new JButton();

    // panel
    private JPanel timeIntervalPanel=new JPanel();
    private JPanel display=new JPanel(new BorderLayout());
    private JPanel info=new JPanel(new BorderLayout());

    /**
     * Constructor
     * @param message Message
     * @param modif boolean
     */
    DisplayOneMessage(Message message,boolean modif){
        if(!modif){
            setTimeIntervalPanel(message);
            info.add(timeIntervalPanel,BorderLayout.NORTH);
        }
        setDisplay(message,modif);
        info.add(display,BorderLayout.CENTER);
    }

    /**
     * Create panel for periode modification
     * @param msg Message
     */
    private void setTimeIntervalPanel(Message msg){
        int nbColumns=4;
        hourFrom.setColumns(nbColumns);
        minuteFrom.setColumns(nbColumns);
        hourTo.setColumns(nbColumns);
        minuteTo.setColumns(nbColumns);

        hourFrom.setText(Integer.toString(msg.getDecode().getHour()));
        minuteFrom.setText(Integer.toString(msg.getDecode().getMinute()));
        hourTo.setText(Integer.toString(msg.getDecode().getHour()));
        minuteTo.setText(Integer.toString(msg.getDecode().getMinute()));

        createTimeDisplay("From :",hourFrom,minuteFrom);
        createTimeDisplay("To :",hourTo,minuteTo);
    }

    /**
     * create Jpanel to display hour and minute
     * @param label String
     * @param hour JTextField
     * @param minute JTextField
     */
    private void createTimeDisplay(String label,JTextField hour,JTextField minute){
        JPanel jPanel=new JPanel();
        Border border ;
        border = BorderFactory.createEtchedBorder();
        border = BorderFactory.createTitledBorder(border,label);
        jPanel.add(hour);
        jPanel.add(new JLabel("h"));
        jPanel.add(minute);
        jPanel.setBorder(border);
        timeIntervalPanel.add(jPanel);
    }

    /**
     * generate display of ship information
     * @param message Message AIS data
     * @param modif boolean
     */
    private void setDisplay(Message message,boolean modif){
        display.removeAll();
        add(display,BorderLayout.NORTH);
        add(display,BorderLayout.CENTER);
        add(display,BorderLayout.SOUTH);

        MessageDecode vessel = message.getDecode();
        JLabel AISraw=new JLabel(" Raw AIS : "+message.getAis().getRawData()); // display raw data
        display.add(AISraw,BorderLayout.NORTH);

        JPanel panelValue=new JPanel(new GridLayout(0,4));
        JScrollPane scrollPane=new JScrollPane(panelValue); // for reponsive design
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // messageType
        JLabel messageTypeLabel = new JLabel("Message Type");
        messageTypeText = new JTextField((vessel==null)?"":vessel.getMessageType());
        panelValue.add(initPanelValue(messageTypeLabel,messageTypeText,modif));

        //repeatIndicator
        JLabel repeatIndicatorLabel = new JLabel("Repeat Indicator");
        repeatIndicatorText = new JTextField((vessel==null)?"":vessel.getRepeatIndicator());
        panelValue.add(initPanelValue(repeatIndicatorLabel,repeatIndicatorText,modif));

        // MMSI
        JLabel MMSILabel = new JLabel("MMSI");
        MMSIText = new JTextField((vessel==null)?"":vessel.getMMSI());
        panelValue.add(initPanelValue(MMSILabel,MMSIText,modif));


        // navigationStatus
        JLabel navigationStatusLabel = new JLabel("Navigation Status");
        navigationStatusText = new JTextField((vessel==null)?"":vessel.getNavigationStatus());
        panelValue.add(initPanelValue(navigationStatusLabel,navigationStatusText,modif));

        //rateOverTurn
        JLabel rateOverTurnLabel = new JLabel("Rate Over Turn");
        rateOverTurnText = new JTextField(vessel==null?"":String.valueOf(vessel.getRateOverTurn()));
        panelValue.add(initPanelValue(rateOverTurnLabel,rateOverTurnText,modif));

        // speedOverGround
        JLabel speedOverGroundLabel = new JLabel("Speed Over Ground");
        speedOverGroundText = new JTextField((vessel==null)?"":String.valueOf(vessel.getSpeedOverGround()));
        panelValue.add(initPanelValue(speedOverGroundLabel,speedOverGroundText,modif));

        // positiontionAccuracy
        JLabel positiontionAccuracyLabel = new JLabel("Position Accuracy");
        positiontionAccuracyText = new JTextField((vessel==null)?"":vessel.getPositiontionAccuracy());
        panelValue.add(initPanelValue(positiontionAccuracyLabel,positiontionAccuracyText,modif));

        // longitude
        JLabel longitudeLabel = new JLabel("Longitude");
        longitudeText = new JTextField((vessel==null)?"":String.valueOf(vessel.getLongitude()));
        panelValue.add(initPanelValue(longitudeLabel,longitudeText,modif));

        // latitude
        JLabel latitudeLabel = new JLabel("Latitude");
        latitudeText = new JTextField((vessel==null)?"":String.valueOf(vessel.getLatitude()));
        panelValue.add(initPanelValue(latitudeLabel,latitudeText,modif));

        //courseOverGround
        JLabel courseOverGroudLabel = new JLabel("Course Over Ground");
        courseOverGroudText= new JTextField((vessel==null)?"":String.valueOf(vessel.getCourseOverGroud()));
        panelValue.add(initPanelValue(courseOverGroudLabel,courseOverGroudText,modif));

        // trueHeading
        JLabel trueHeadingLabel = new JLabel("True Heading");
        trueHeadingText = new JTextField((vessel==null)?"":vessel.getTrueHeading());
        panelValue.add(initPanelValue(trueHeadingLabel,trueHeadingText,modif));

        // timeStamp
        JLabel timeStampLabel = new JLabel("Time Stamp");
        timeStampText = new JTextField((vessel==null)?"":vessel.getTimeStamp());
        panelValue.add(initPanelValue(timeStampLabel,timeStampText,modif));

        // maneuverIndicator
        JLabel maneuverIndicatorLabel = new JLabel("Maneuver Indicator");
        maneuverIndicatorText = new JTextField((vessel==null)?"":vessel.getManeuverIndicator());
        panelValue.add(initPanelValue(maneuverIndicatorLabel,maneuverIndicatorText,modif));

        // spare
        JLabel spareLabel = new JLabel("Spare");
        spareText = new JTextField((vessel==null)?"":vessel.getSpare());
        panelValue.add(initPanelValue(spareLabel,spareText,modif));

        // RAIMflag
        JLabel RAIMflagLabel = new JLabel("RAIM Flag");
        RAIMflagText = new JTextField((vessel==null)?"":vessel.getRAIMflag());
        panelValue.add(initPanelValue(RAIMflagLabel,RAIMflagText,modif));

        // radioStatus
        JLabel radioStatusLabel = new JLabel("Radio Status");
        radioStatusText = new JTextField((vessel==null)?"":vessel.getRadioStatus());
        panelValue.add(initPanelValue(radioStatusLabel,radioStatusText,modif));

        // hour
        JLabel hourLabel = new JLabel("Hour");
        hoursText = new JTextField((vessel==null)?"":String.valueOf(vessel.getHour()));
        panelValue.add(initPanelValue(hourLabel,hoursText,modif));

        // minutes
        JLabel minuteLabel = new JLabel("Minute");
        minutesText = new JTextField((vessel==null)?"":String.valueOf(vessel.getMinute()));
        panelValue.add(initPanelValue(minuteLabel,minutesText,modif));

        display.add(scrollPane,BorderLayout.CENTER);
        if(!modif) {
            display.add(initButton((vessel == null) ? "GhostShip" : vessel.getUTCString()), BorderLayout.SOUTH);
        }
    }

    /**
     *  initialize one of attribut information
     * @param label JLabel
     * @param textField JtextField
     * @param modif boolean
     * @return JPanel
     */
    private JPanel initPanelValue(JLabel label, JTextField textField,boolean modif){
        JPanel returnPanel=new JPanel();
        returnPanel.add(label);
        textField.setEditable(!modif);
        textField.setColumns(6);
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
       // applyAll.setText("Apply (all)");
        //cancelAll.setText("Cancel (all)");
        applySingle.setText("Apply ("+mmsi+")");
        cancelSingle.setText("Cancel ("+mmsi+")");

        // color font
        cancelAll.setForeground(new Color(139, 0, 0));
        applyAll.setForeground(new Color(0, 114, 57));
        cancelSingle.setForeground(new Color(139, 0, 0));
        applySingle.setForeground(new Color(0, 114, 57));


        // add to panel
        //panelButton.add(cancelAll);
        //panelButton.add(applyAll);
        panelButton.add(cancelSingle);
        panelButton.add(applySingle);
        return panelButton;
    }

    /**
     * Save information modification for one selected vessel
     */
    void saveModificationOne(Ship ship) {
        ship.staticRangeModification(repeatIndicatorText.getText(),
                navigationStatusText.getText(),
                getDoubleToAppliedModification(rateOverTurnText.getText()),
                getDoubleToAppliedModification(speedOverGroundText.getText()),
                positiontionAccuracyText.getText(),
                getDoubleToAppliedModification(longitudeText.getText()),
                getDoubleToAppliedModification(latitudeText.getText()),
                getDoubleToAppliedModification(courseOverGroudText.getText()),
                trueHeadingText.getText(),
                timeStampText.getText(),
                maneuverIndicatorText.getText(),
                spareText.getText(),
                RAIMflagText.getText(),
                radioStatusText.getText(),
                getIntToAppliedModification(hoursText.getText()),
                getIntToAppliedModification(minutesText.getText()),
                getIntToAppliedModification(hourFrom.getText()),
                getIntToAppliedModification(minuteFrom.getText()),
                getIntToAppliedModification(hourTo.getText()),
                getIntToAppliedModification(minuteTo.getText())
        );
        PopUp.information(info.getParent().getParent(),"Modification done !");
    }

    /**
     * get Integer value from String
     * @param text String
     * @return int
     */
    private int getIntToAppliedModification(String text){
        int defaultValue=1000000;
        return (text==null)?defaultValue:Integer.parseInt(text);
    }

    /**
     * get Double value from String
     * @param text String
     * @return double
     */
    private double getDoubleToAppliedModification(String text){
        double defaultValue=1000000;
        return (text==null)?defaultValue:Double.parseDouble(text);
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
    JButton getCancelSingle() {
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
        return info;
    }
}
