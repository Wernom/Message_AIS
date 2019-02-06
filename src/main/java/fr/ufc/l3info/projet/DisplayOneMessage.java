package fr.ufc.l3info.projet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class DisplayOneMessage extends JPanel {

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

    private JButton applyAll=new JButton();
    private JButton cancelAll=new JButton();
    private JButton applySingle =new JButton();
    private JButton cancelSingle=new JButton();
    private JPanel display=new JPanel(new BorderLayout());


    DisplayOneMessage(Message ship){
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

        JPanel panelValue=new JPanel(new GridLayout(0,4));
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

        //rateOverTurn
        JLabel rateOverTurnLabel = new JLabel("Rate Over Turn");
        rateOverTurnText = new JTextField(vessel==null?"":String.valueOf(vessel.getRateOverTurn()));
        panelValue.add(initPanelValue(rateOverTurnLabel,rateOverTurnText));

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

        // hour
        JLabel hourLabel = new JLabel("Hour");
        hoursText = new JTextField((vessel==null)?"":String.valueOf(vessel.getHour()));
        panelValue.add(initPanelValue(hourLabel,hoursText));

        // minutes
        JLabel minuteLabel = new JLabel("Hour");
        minutesText = new JTextField((vessel==null)?"":String.valueOf(vessel.getMinute()));
        panelValue.add(initPanelValue(minuteLabel,minutesText));

        display.add(scrollPane,BorderLayout.CENTER);
        display.add(initButton((vessel==null)?"GhostShip":vessel.getUTCString()),BorderLayout.SOUTH);
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
    void saveModificationOne(Message ship) {
        String defaultValue="0";
        ship.getDecode().setMessageType((messageTypeText.getText()==null)?defaultValue: messageTypeText.getText());
        ship.getDecode().setRepeatIndicator(repeatIndicatorText.getText()==null?defaultValue:repeatIndicatorText.getText());
        // ship.getDecode().setMMSI(MMSIText.getText()==null?defaultValue:MMSIText.getText());
        ship.getDecode().setNavigationStatus(navigationStatusText.getText()==null?defaultValue:navigationStatusText.getText());
        ship.getDecode().setRateOverTurn(Double.parseDouble(rateOverTurnText.getText()==null?defaultValue:rateOverTurnText.getText()));
        ship.getDecode().setSpeedOverGround(Double.parseDouble(speedOverGroundText.getText()==null?defaultValue:speedOverGroundText.getText()));
        ship.getDecode().setPositiontionAccuracy(positiontionAccuracyText.getText()==null?defaultValue:positiontionAccuracyText.getText());
        ship.getDecode().setLongitude(Double.parseDouble(longitudeText.getText()==null?defaultValue:longitudeText.getText()));
        ship.getDecode().setLatitude(Double.parseDouble(latitudeText.getText()==null?defaultValue:latitudeText.getText()));
        ship.getDecode().setCourseOverGroud(Double.parseDouble(courseOverGroudText.getText()==null?defaultValue:courseOverGroudText.getText()));
        ship.getDecode().setTrueHeading(trueHeadingText.getText()==null?defaultValue:trueHeadingText.getText());
        ship.getDecode().setTimeStamp(timeStampText.getText()==null?defaultValue:timeStampText.getText());
        ship.getDecode().setManeuverIndicator(maneuverIndicatorText.getText()==null?defaultValue:maneuverIndicatorText.getText());
        ship.getDecode().setSpare(spareText.getText()==null?defaultValue:spareText.getText());
        ship.getDecode().setRAIMflag(RAIMflagText.getText()==null?defaultValue:RAIMflagText.getText());
        ship.getDecode().setRadioStatus(radioStatusText.getText()==null?defaultValue:radioStatusText.getText());
        ship.getDecode().setHour(Integer.parseInt(hoursText.getText()==null?defaultValue:hoursText.getText()));
        ship.getDecode().setMinute(Integer.parseInt(minutesText.getText()==null?defaultValue:minutesText.getText()));
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