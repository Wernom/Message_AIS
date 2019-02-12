package fr.ufc.l3info.projet;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

class DisplayOneMessage extends JPanel {

    private Message messageRaw;

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
    private JButton apply =new JButton("Apply");
    private JButton cancel=new JButton("Cancel");

    // panel
    private JPanel timeIntervalPanel=new JPanel();
    private JPanel display=new JPanel(new BorderLayout());
    private JPanel info=new JPanel(new BorderLayout());

    /**
     * Constructor
     * @param message Message
     * @param modif boolean
     */
    DisplayOneMessage(Message message,String modificationType,boolean modif){
        this.messageRaw=message;
        if(!modif){
            setTimeIntervalPanel();
            info.add(timeIntervalPanel,BorderLayout.NORTH);
        }
        setDisplay(modificationType,modif);
        info.add(display,BorderLayout.CENTER);
    }

    /**
     * Create panel for periode modification
     */
    private void setTimeIntervalPanel(){
        int nbColumns=4;
        hourFrom.setColumns(nbColumns);
        minuteFrom.setColumns(nbColumns);
        hourTo.setColumns(nbColumns);
        minuteTo.setColumns(nbColumns);

        hourFrom.setText(Integer.toString(messageRaw.getDecode().getHour()));
        minuteFrom.setText(Integer.toString(messageRaw.getDecode().getMinute()));
        hourTo.setText(Integer.toString(messageRaw.getDecode().getHour()));
        minuteTo.setText(Integer.toString(messageRaw.getDecode().getMinute()));

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
     * @param modif boolean
     */
    private void setDisplay(String modificationType,boolean modif){
        display.removeAll();
        add(display,BorderLayout.NORTH);
        add(display,BorderLayout.CENTER);
        add(display,BorderLayout.SOUTH);

        JLabel AISraw=new JLabel(" Raw AIS : "+messageRaw.getAis().getRawData()); // display raw data
        display.add(AISraw,BorderLayout.NORTH);
        JPanel panelValue=new JPanel(new GridLayout(0,4));
        if(modificationType.equals("Propagation")){
            panelValue.setLayout(new BorderLayout());
        }

        JScrollPane scrollPane=new JScrollPane(panelValue);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        switch(modificationType) {
            case "Propagation":
                setDisplayForPropagationModification(panelValue,modif);

                break;
            case "Hard":
            default:
                setDisplayForHardModification(panelValue,modif);
                MMSIText.setEditable(false);
                hoursText.setEditable(false);
                minutesText.setEditable(false);
                 break;
        }

        display.add(scrollPane,BorderLayout.CENTER);

        display.add(initButton(), BorderLayout.SOUTH);

    }

    /**
     * set the display for hard modification
     * @param panelValue JPanel
     * @param modif boolean
     */
    private void setDisplayForHardModification(JPanel panelValue,boolean modif){
        // messageType
        JLabel messageTypeLabel = new JLabel("Message Type");
        messageTypeText = new JTextField((messageRaw.getDecode() == null) ? "" : messageRaw.getDecode().getMessageType());
        panelValue.add(initPanelValue(messageTypeLabel, messageTypeText, modif));

        //repeatIndicator
        JLabel repeatIndicatorLabel = new JLabel("Repeat Indicator");
        repeatIndicatorText = new JTextField((messageRaw.getDecode() == null) ? "" : messageRaw.getDecode().getRepeatIndicator());
        panelValue.add(initPanelValue(repeatIndicatorLabel, repeatIndicatorText, modif));

        // MMSI
        JLabel MMSILabel = new JLabel("MMSI");
        MMSIText = new JTextField((messageRaw.getDecode() == null) ? "" : messageRaw.getDecode().getMMSI());
        panelValue.add(initPanelValue(MMSILabel, MMSIText, modif));


        // navigationStatus
        JLabel navigationStatusLabel = new JLabel("Navigation Status");
        navigationStatusText = new JTextField((messageRaw.getDecode() == null) ? "" : messageRaw.getDecode().getNavigationStatus());
        panelValue.add(initPanelValue(navigationStatusLabel, navigationStatusText, modif));

        //rateOverTurn
        JLabel rateOverTurnLabel = new JLabel("Rate Over Turn");
        rateOverTurnText = new JTextField(messageRaw.getDecode() == null ? "" : String.valueOf(messageRaw.getDecode().getRateOverTurn()));
        panelValue.add(initPanelValue(rateOverTurnLabel, rateOverTurnText, modif));

        // speedOverGround
        setDisplayForPropagationModification(panelValue,modif);

        // positiontionAccuracy
        JLabel positiontionAccuracyLabel = new JLabel("Position Accuracy");
        positiontionAccuracyText = new JTextField((messageRaw.getDecode() == null) ? "" : messageRaw.getDecode().getPositiontionAccuracy());
        panelValue.add(initPanelValue(positiontionAccuracyLabel, positiontionAccuracyText, modif));

        // longitude
        JLabel longitudeLabel = new JLabel("Longitude");
        longitudeText = new JTextField((messageRaw.getDecode() == null) ? "" : String.valueOf(messageRaw.getDecode().getLongitude()));
        panelValue.add(initPanelValue(longitudeLabel, longitudeText, modif));

        // latitude
        JLabel latitudeLabel = new JLabel("Latitude");
        latitudeText = new JTextField((messageRaw.getDecode() == null) ? "" : String.valueOf(messageRaw.getDecode().getLatitude()));
        panelValue.add(initPanelValue(latitudeLabel, latitudeText, modif));

        //courseOverGround
        JLabel courseOverGroudLabel = new JLabel("Course Over Ground");
        courseOverGroudText = new JTextField((messageRaw.getDecode() == null) ? "" : String.valueOf(messageRaw.getDecode().getCourseOverGroud()));
        panelValue.add(initPanelValue(courseOverGroudLabel, courseOverGroudText, modif));

        // trueHeading
        JLabel trueHeadingLabel = new JLabel("True Heading");
        trueHeadingText = new JTextField((messageRaw.getDecode() == null) ? "" : messageRaw.getDecode().getTrueHeading());
        panelValue.add(initPanelValue(trueHeadingLabel, trueHeadingText, modif));

        // timeStamp
        JLabel timeStampLabel = new JLabel("Time Stamp");
        timeStampText = new JTextField((messageRaw.getDecode() == null) ? "" : messageRaw.getDecode().getTimeStamp());
        panelValue.add(initPanelValue(timeStampLabel, timeStampText, modif));

        // maneuverIndicator
        JLabel maneuverIndicatorLabel = new JLabel("Maneuver Indicator");
        maneuverIndicatorText = new JTextField((messageRaw.getDecode() == null) ? "" : messageRaw.getDecode().getManeuverIndicator());
        panelValue.add(initPanelValue(maneuverIndicatorLabel, maneuverIndicatorText, modif));

        // spare
        JLabel spareLabel = new JLabel("Spare");
        spareText = new JTextField((messageRaw.getDecode() == null) ? "" : messageRaw.getDecode().getSpare());
        panelValue.add(initPanelValue(spareLabel, spareText, modif));

        // RAIMflag
        JLabel RAIMflagLabel = new JLabel("RAIM Flag");
        RAIMflagText = new JTextField((messageRaw.getDecode() == null) ? "" : messageRaw.getDecode().getRAIMflag());
        panelValue.add(initPanelValue(RAIMflagLabel, RAIMflagText, modif));

        // radioStatus
        JLabel radioStatusLabel = new JLabel("Radio Status");
        radioStatusText = new JTextField((messageRaw.getDecode() == null) ? "" : messageRaw.getDecode().getRadioStatus());
        panelValue.add(initPanelValue(radioStatusLabel, radioStatusText, modif));

        // hour
        JLabel hourLabel = new JLabel("Hour");
        hoursText = new JTextField((messageRaw.getDecode() == null) ? "" : String.valueOf(messageRaw.getDecode().getHour()));
        panelValue.add(initPanelValue(hourLabel, hoursText, modif));

        // minutes
        JLabel minuteLabel = new JLabel("Minute");
        minutesText = new JTextField((messageRaw.getDecode() == null) ? "" : String.valueOf(messageRaw.getDecode().getMinute()));
        panelValue.add(initPanelValue(minuteLabel, minutesText, modif));
    }

    /**
     * set the display for propagation modification
     * @param panelValue JPanel
     * @param modif boolean
     */
    private void setDisplayForPropagationModification(JPanel panelValue,boolean modif){
        // speedOverGround
        JLabel speedOverGroundLabel = new JLabel("Speed Over Ground");
        speedOverGroundText = new JTextField((messageRaw.getDecode() == null) ? "" : String.valueOf(messageRaw.getDecode().getSpeedOverGround()));
        panelValue.add(initPanelValue(speedOverGroundLabel, speedOverGroundText, modif));
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
    private JPanel initButton(){
        JPanel panelButton=new JPanel();

        // color font
        cancel.setForeground(new Color(139, 0, 0));
        apply.setForeground(new Color(0, 114, 57));


        // add to panel
        panelButton.add(cancel);
        panelButton.add(apply);
        return panelButton;
    }

    /**
     * Save information in hard modification for one selected vessel
     * @param ship Ship
     */
    void saveModificationHardOne(Ship ship) {
        ship.staticRangeModification(getStringToAppliedModification(repeatIndicatorText.getText(),messageRaw.getDecode().getRepeatIndicator()),
            getStringToAppliedModification(navigationStatusText.getText(),messageRaw.getDecode().getNavigationStatus()),
            getDoubleToAppliedModification(rateOverTurnText.getText(),messageRaw.getDecode().getRateOverTurn()),
            getDoubleToAppliedModification(speedOverGroundText.getText(),messageRaw.getDecode().getSpeedOverGround()),
            getStringToAppliedModification(positiontionAccuracyText.getText(),messageRaw.getDecode().getPositiontionAccuracy()),
            getDoubleToAppliedModification(longitudeText.getText(),messageRaw.getDecode().getLongitude()),
            getDoubleToAppliedModification(latitudeText.getText(),messageRaw.getDecode().getLatitude()),
            getDoubleToAppliedModification(courseOverGroudText.getText(),messageRaw.getDecode().getCourseOverGroud()),
            getStringToAppliedModification(trueHeadingText.getText(),messageRaw.getDecode().getTrueHeading()),
            getStringToAppliedModification(timeStampText.getText(),messageRaw.getDecode().getTimeStamp()),
            getStringToAppliedModification(maneuverIndicatorText.getText(),messageRaw.getDecode().getManeuverIndicator()),
            getStringToAppliedModification(spareText.getText(),messageRaw.getDecode().getSpare()),
            getStringToAppliedModification(RAIMflagText.getText(),messageRaw.getDecode().getRAIMflag()),
            getStringToAppliedModification(radioStatusText.getText(),messageRaw.getDecode().getRadioStatus()),
            getIntToAppliedModification(hoursText.getText(),messageRaw.getDecode().getHour()),
            getIntToAppliedModification(minutesText.getText(),messageRaw.getDecode().getMinute()),
            getIntToAppliedModification(hourFrom.getText(),1000000),
            getIntToAppliedModification(minuteFrom.getText(),1000000),
            getIntToAppliedModification(hourTo.getText(),1000000),
            getIntToAppliedModification(minuteTo.getText(),1000000)
        );
        PopUp.information(info.getParent().getParent(),"Modification done !");
    }

    /**
     * Save information in propagation modification for one selected vessel
     * @param ship Ship
     */
    void saveModificationPropagationOne(Ship ship){
        ship.speedModificationAffectPosition(getDoubleToAppliedModification(speedOverGroundText.getText(),messageRaw.getDecode().getSpeedOverGround()),
                getIntToAppliedModification(hourFrom.getText(),1000000),
                getIntToAppliedModification(minuteFrom.getText(),1000000),
                getIntToAppliedModification(hourTo.getText(),1000000),
                getIntToAppliedModification(minuteTo.getText(),1000000));
        PopUp.information(info.getParent().getParent(),"Modification done !");
    }

    /**
     * get Integer value from String
     * @param text String
     * @return int
     */
    private int getIntToAppliedModification(String text,int test){
        int defaultValue=1000000;
        return text.equals(Integer.toString(test))?defaultValue:Integer.parseInt(text);
    }

    /**
     * get Double value from String
     * @param text String
     * @return double
     */
    private double getDoubleToAppliedModification(String text,double test){
        double defaultValue=1000000;
        return text.equals(Double.toString(test))?defaultValue:Double.parseDouble(text);
    }

    private String getStringToAppliedModification(String text,String test){
        String defaultValue=null;
        return text.equals(test)?defaultValue:text;
    }

    /**
     * @return JButton
     */
    JButton getApply() {
        return apply;
    }

    /**
     * @return JButton
     */
    JButton getCancel() {
        return cancel;
    }

    /**
     * @return JPanel
     */
    JPanel getDisplay() {
        return info;
    }
}
