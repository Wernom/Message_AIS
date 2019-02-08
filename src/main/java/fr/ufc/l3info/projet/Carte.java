package fr.ufc.l3info.projet;

import org.openstreetmap.gui.jmapviewer.*;
import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;



public class Carte extends JPanel implements JMapViewerEventListener{

    private static final long serialVersionUID = 1L;

    private JMapViewer myMap;
    private JPanel panel = new JPanel(new BorderLayout());
    private JPanel panelZoom;
    private JLabel zoomValue;
    private JLabel mperpLabelName;
    private JLabel mperpLabelValue;

    private Color currentSelectedMessageColor=new Color(179,0, 9);
    private Color currentLastMessageRecieveColor=new Color(234, 179,0);
    private Color currentSelectedShipColor=new Color(184, 75, 0, 193);
    private Color otherMessageColor=new Color(0, 4, 176, 255);
    private Color otherLastMessageReceiveColor=new Color(0, 117, 168);


    /**
     * Setups the JFrame layout, sets some default options for the JMapViewerTree and displays a map in the window.
     * Based on http://svn.openstreetmap.org/applications/viewer/jmapviewer/src/org/openstreetmap/gui/jmapviewer/Demo.java by Jan Peter Stotz
     */
    Carte() {

        myMap = new JMapViewer();
        setupPanels();

        // Listen to the map viewer for user operations so components will
        // receive events and updates
        myMap.addJMVListener(this);

        // Set some options, e.g. tile source and that markers are visible
        myMap.setTileSource(new OsmTileSource.Mapnik());
        myMap.setTileLoader(new OsmTileLoader(myMap));
        myMap.setMapMarkerVisible(true);
        myMap.setZoomContolsVisible(true);

        // activate map in window
        myMap.setVisible(true);
        Border border ;
        border = BorderFactory.createLineBorder(Color.black);
        myMap.setBorder(border);
        panel.add(myMap, BorderLayout.CENTER);
        panel.add(createLegend(),BorderLayout.SOUTH);
        panel.setBorder(border);
    }

    //----------------- default source code in Demo of JMapViewer librairy
    /**
     * update zoom parameters
     */
    private void updateZoomParameters() {
        if (mperpLabelValue != null)
            mperpLabelValue.setText(String.format("%s", myMap.getMeterPerPixel()));
        if (zoomValue != null)
            zoomValue.setText(String.format("%s", myMap.getZoom()));
    }

    /**
     *
     * @param command JMVCommandEvent
     */
    @Override
    public void processCommand(JMVCommandEvent command) {
        if (command.getCommand().equals(JMVCommandEvent.COMMAND.ZOOM) ||
                command.getCommand().equals(JMVCommandEvent.COMMAND.MOVE)) {
            updateZoomParameters();
        }
    }


    /**
     * setup JPanel
     */
    private void setupPanels() { // this fonction some modification by ourself
        add(panel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        JPanel panelTop = new JPanel(new BorderLayout());
        add(panelTop, BorderLayout.NORTH);
        add(panelTop, BorderLayout.SOUTH);

        // help
        JPanel helpPanel = new JPanel();
        JLabel helpLabel = new JLabel("Use right mouse button to move,\n "
                + "left double click or mouse wheel to zoom.");
        helpPanel.add(helpLabel);

        // echelle de la carte
        mperpLabelName = new JLabel("Meters/Pixels: ");
        mperpLabelValue = new JLabel(String.format("%s", myMap.getMeterPerPixel()));

        // zoom
        panelZoom=new JPanel();
        setupZoomPanel();
        panelTop.add(panelZoom,BorderLayout.SOUTH);

        panelTop.add(setCheckBox(),BorderLayout.NORTH);

        panel.add(helpPanel, BorderLayout.SOUTH);
        panel.add(panelTop, BorderLayout.NORTH);
    }

    private void setupZoomPanel(){
        panelZoom.removeAll();
        JLabel zoomLabel = new JLabel("Zoom: ");
        zoomValue = new JLabel(String.format("%s", myMap.getZoom()));

        panelZoom.add(zoomLabel,BorderLayout.SOUTH);
        panelZoom.add(zoomValue,BorderLayout.SOUTH);
        panelZoom.add(mperpLabelName,BorderLayout.SOUTH);
        panelZoom.add(mperpLabelValue,BorderLayout.SOUTH);
    }

    /**
     * set the CheckBox option for the map
     * @return Jpanel
     */
    private JPanel setCheckBox(){
        JPanel checkBoxPanel = new JPanel();
        final JCheckBox showMapMarker = new JCheckBox("Map markers visible");
        showMapMarker.setSelected(myMap.getMapMarkersVisible());
        showMapMarker.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                myMap.setMapMarkerVisible(showMapMarker.isSelected());
            }
        });
        checkBoxPanel.add(showMapMarker);
        final JCheckBox showTileGrid = new JCheckBox("Tile grid visible");
        showTileGrid.setSelected(myMap.isTileGridVisible());
        showTileGrid.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                myMap.setTileGridVisible(showTileGrid.isSelected());
            }
        });
        checkBoxPanel.add(showTileGrid);
        final JCheckBox showZoomControls = new JCheckBox("Show zoom controls");
        showZoomControls.setSelected(myMap.getZoomControlsVisible());
        showZoomControls.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                myMap.setZoomContolsVisible(showZoomControls.isSelected());
            }
        });
        checkBoxPanel.add(showZoomControls);
        return checkBoxPanel;
    }
    //---------------------

    private JPanel createLegend(){
        JPanel pan=new JPanel(new GridLayout(1,5));

        Border border;
        border = BorderFactory.createEtchedBorder();
        border=BorderFactory.createTitledBorder(border,"Legend :");
        pan.setBorder(border);

        JLabel currShip=new JLabel("Selected Ship");
        currShip.setHorizontalTextPosition(JLabel.LEFT);
        currShip.setForeground(currentSelectedShipColor);

        JLabel currMsg=new JLabel("Selected Message");
        currMsg.setHorizontalTextPosition(JLabel.LEFT);
        currMsg.setForeground(currentSelectedMessageColor);

        JLabel currLastMsg=new JLabel("Selected Last Message ");
        currLastMsg.setHorizontalTextPosition(JLabel.LEFT);
        currLastMsg.setForeground(currentLastMessageRecieveColor);

        JLabel otherMsg=new JLabel("Other Message");
        otherMsg.setHorizontalTextPosition(JLabel.LEFT);
        otherMsg.setForeground(otherMessageColor);

        JLabel otherLastMsg=new JLabel("Other Last Message");
        otherLastMsg.setHorizontalTextPosition(JLabel.LEFT);
        otherLastMsg.setForeground(otherLastMessageReceiveColor);

        pan.add(currMsg);
        pan.add(currLastMsg);
        pan.add(currShip);
        pan.add(otherLastMsg);
        pan.add(otherMsg);

        return pan;
    }

    /**
     * print a ship with his old position
     * @param vessel Ship 
     */
    private void printShip(Ship vessel,boolean isSelected){
        Coordinate lastKnownCoordinate=new Coordinate(vessel.getLastKnownMessage().getDecode().getLatitude(),vessel.getLastKnownMessage().getDecode().getLongitude());
       for (Message message : vessel.getMessages().values()) {
           Coordinate coordinate = new Coordinate(message.getDecode().getLatitude(), message.getDecode().getLongitude());
           MapMarkerDot position = new MapMarkerDot(coordinate.getLat(), coordinate.getLon());
           if(isSelected) {
               if (lastKnownCoordinate.equals(coordinate)) {
                   //position.setColor(currentLastMessageRecieveColor);
                   position.setBackColor(currentLastMessageRecieveColor);
               } else {
                   //position.setColor(currentSelectedShipColor);
                   position.setBackColor(currentSelectedShipColor);
               }
           }else{
               if (lastKnownCoordinate.equals(coordinate)) {
                   //position.setColor(otherLastMessageReceiveColor);
                   position.setBackColor(otherLastMessageReceiveColor);
               } else {
                   //position.setColor(otherMessageColor);
                   position.setBackColor(otherMessageColor);
               }
           }
            myMap.addMapMarker(position);

       }
    }

    /**
     * print all ships
     */
    private void printTrafic(HashMap<String,Ship> trafic,Ship ship){
        if (trafic==null)
            return;
        for (Ship vessel:trafic.values()) {
            if(vessel.equals(ship)){
                printShip(vessel,true);
            }else{
                printShip(vessel,false);
            }
        }
    }

    /**
     * Reload the map to display ship after import
     * @param trafic ArrayList<Message>
     */
    void reloadMap(HashMap<String,Ship> trafic,Ship ship){
        myMap.removeAllMapMarkers();
        printTrafic(trafic,ship);
    }

    /**
     * Reload the map after MMSI select
     * @param trafic HashMap<String,Message>
     * @param MMSI String
     */
    void reloadMap(HashMap<String,Ship> trafic,String MMSI,Message msg){
        this.reloadMap(trafic,trafic.get(MMSI));
        MapMarkerDot ship;
        if (msg==null){
            ship=new MapMarkerDot(trafic.get(MMSI).getLastKnownMessage().getDecode().getLatitude(),trafic.get(MMSI).getLastKnownMessage().getDecode().getLongitude());
        }else{
            ship=new MapMarkerDot(msg.getDecode().getLatitude(),msg.getDecode().getLongitude());
        }
        setSelectedShip(ship);
    }

    /**
     * Reload the map after select ship on map
     * @param trafic ArrayList<Message>
     * @param coordinate Coordinate
     */
    void reloadMap(HashMap<String,Ship> trafic,Coordinate coordinate){
        this.reloadMap(trafic, (Ship) null);
        MapMarkerDot ship=new MapMarkerDot(coordinate);
        setSelectedShip(ship);
    }

    /**
     * set more visible the selected ship
     * @param ship MapMarkerDot
     */
    private void setSelectedShip(MapMarkerDot ship){
        ship.setColor(Color.BLACK);
        ship.setBackColor(Color.RED);
        myMap.addMapMarker(ship);
        myMap.setDisplayPosition(ship.getCoordinate(),3);
        updateZoomParameters();
    }

    /**
     * @return JPanel
     */
    JPanel getPanel() {
        return panel;
    }

    JMapViewer getMyMap() {
        return myMap;
    }
}