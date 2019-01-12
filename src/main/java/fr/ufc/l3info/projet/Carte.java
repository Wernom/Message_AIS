package fr.ufc.l3info.projet;

import org.openstreetmap.gui.jmapviewer.*;
import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Carte extends JPanel implements JMapViewerEventListener{

    private static final long serialVersionUID = 1L;

    private JMapViewer myMap;
    private JPanel panel = new JPanel(new BorderLayout());
    private JLabel zoomLabel;
    private JLabel zoomValue;
    private JLabel mperpLabelName;
    private JLabel mperpLabelValue;


    /**
     * Setups the JFrame layout, sets some default options for the JMapViewerTree and displays a map in the window.
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

        panel.add(myMap, BorderLayout.CENTER);
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
        JPanel panelZoom=new JPanel();
        zoomLabel = new JLabel("Zoom: ");
        zoomValue = new JLabel(String.format("%s", myMap.getZoom()));

        panel.add(helpPanel, BorderLayout.SOUTH);
        panel.add(panelTop, BorderLayout.NORTH);

        panelZoom.add(zoomLabel,BorderLayout.SOUTH);
        panelZoom.add(zoomValue,BorderLayout.SOUTH);
        panelZoom.add(mperpLabelName,BorderLayout.SOUTH);
        panelZoom.add(mperpLabelValue,BorderLayout.SOUTH);

        panelTop.add(panelZoom,BorderLayout.SOUTH);
        panelTop.add(setCheckBox(),BorderLayout.NORTH);
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

    /**
     * print a ship with his old position
     * @param vessel Ship 
     */
    private void printShip(MessageDecode vessel){
        myMap.addMapMarker(new MapMarkerDot(vessel.getLatitude(),vessel.getLongitude()));
       /*
       for (Coordinate oldCoordinate: vessel.getOldPositionsList()) {
            MapMarkerCircle oldPosition = new  MapMarkerCircle(oldCoordinate.getLatitude(),oldCoordinate.getLongitude(),0.03);
            oldPosition.setColor(Color.black);
            oldPosition.setBackColor(Color.black);
            map().addMapMarker(oldPosition);
        }
        */
    }

    /**
     * print all ships
     */
    private void printTrafic(ArrayList<Message> trafic){
        if (trafic==null)
            return;
        for (Message vessel:trafic) {
            printShip(vessel.getDecode());
        }
    }

    /**
     * Reload the map to display ship
     * @param trafic ArrayList<Message>
     */
    void reloadMap(ArrayList<Message> trafic){
        myMap.removeAllMapMarkers();
        printTrafic(trafic);
    }
}


/*

    public String printVessel() {
        if(myInfoShip==null)
            return "GhostShip\n";
        return "Ship:" +
                " \n\tMMSI=" + myInfoShip.getMMSI() +
                ", \n\tnavigationStatus=" + myInfoShip.getNavigationStatus() +
                ", \n\tspeedOverGround=" + myInfoShip.getSpeedOverGround() +
                ", \n\tpositiontionAccuracy=" + myInfoShip.getPositiontionAccuracy() +
                ", \n\tlongitude=" + myInfoShip.getLongitude() +
                ", \n\tlatitude=" + myInfoShip.getLatitude() +
                ", \n\tcourseOverGround=" + myInfoShip.getCourseOverGroud() +
                ", \n\ttrueHeading=" + myInfoShip.getTrueHeading() +
                ", \n\ttimeStamp=" + myInfoShip.getTimeStamp() +
                ", \n\tmaneuverIndicator=" + myInfoShip.getManeuverIndicator() +
                //", \n\tspare=" + myInfoShip.getSpare() +
                //", \n\tRAIMflag'" + myInfoShip.getRAIMflag() +
                ", \n\tradioStatus=" + myInfoShip.getRadioStatus() +
                 "\n";
    }
*/