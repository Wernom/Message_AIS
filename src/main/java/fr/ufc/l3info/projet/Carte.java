package fr.ufc.l3info.projet;

import org.openstreetmap.gui.jmapviewer.*;
import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedList;


public class Carte extends JPanel implements JMapViewerEventListener{
    //public class Carte extends JFrame implements JMapViewerEventListener {

    private static final long serialVersionUID = 1L;

    private JMapViewer myMap;
    private JPanel panel = new JPanel(new BorderLayout());
    private JLabel zoomLabel;
    private JLabel zoomValue;
    private JLabel mperpLabelName;
    private JLabel mperpLabelValue;


    // list of all ships
    private ArrayList<Ship> myTrafic;

    /**
     * Setups the JFrame layout, sets some default options for the JMapViewerTree and displays a map in the window.
     */
    Carte(ArrayList<Ship> trafic) {


        myMap = new JMapViewer();
        setupPanels();

        // initialisation du trafic
        this.myTrafic=trafic;

        // Listen to the map viewer for user operations so components will
        // receive events and updates
        myMap.addJMVListener(this);

        // Set some options, e.g. tile source and that markers are visible
        myMap.setTileSource(new OsmTileSource.Mapnik());
        myMap.setTileLoader(new OsmTileLoader(myMap));
        myMap.setMapMarkerVisible(true);
        myMap.setZoomContolsVisible(true);

        printTrafic();

        // activate map in window
        myMap.setVisible(true);

        panel.add(myMap, BorderLayout.CENTER);

    }

    /*/**
     * setup JFrame
     */
  /*  private void setupJFrame() {
        setSize(400, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }*/

    /**
     * setup JPanel
     */
    private void setupPanels() {
        add(panel, BorderLayout.CENTER);

        JPanel panelTop = new JPanel();
        JPanel helpPanel = new JPanel();

        // echelle de la carte
        mperpLabelName = new JLabel("Meters/Pixels: ");
        mperpLabelValue = new JLabel(String.format("%s", myMap.getMeterPerPixel()));

        // zoom
        zoomLabel = new JLabel("Zoom: ");
        zoomValue = new JLabel(String.format("%s", myMap.getZoom()));


        panel.add(helpPanel, BorderLayout.SOUTH);
        panel.add(panelTop, BorderLayout.NORTH);
        JLabel helpLabel = new JLabel("Use right mouse button to move,\n "
                + "left double click or mouse wheel to zoom.");
        helpPanel.add(helpLabel);

        panelTop.add(zoomLabel);
        panelTop.add(zoomValue);
        panelTop.add(mperpLabelName);
        panelTop.add(mperpLabelValue);
        panelTop.add(setCheckBox(),BorderLayout.NORTH);
    }

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


   /* /**
     * @return JMapViewerTree a tree map
     *//*
    private JMapViewer map() {
        return myMap.getViewer();
    }*/

    /**
     * print a ship with his old position
     * @param vessel Ship 
     */
    private void printShip(Ship vessel){
        myMap.addMapMarker(new MapMarkerDot(vessel.getMyCurrentPosition().getLatitude(),vessel.getMyCurrentPosition().getLongitude()));
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
    private void printTrafic(){
        for (Ship vessel:myTrafic) {
            System.out.println(vessel);
            printShip(vessel);
        }
    }

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
}




class Ship{
    private MessageDecode myInfoShip;
    //private Coordinate myCurrentPosition;
   // private ArrayList<Coordinate> oldPositionsList; // faire une list de Ship pour garder en mémoire les anciennes info de navigations ?

    //------
    // constructor
    //------

    Ship(double latitude,double longitude){ // for testing
       // this.oldPositionsList = new ArrayList<>();
       // setMyCurrentPosition(new Coordinate(latitude,longitude));
    }

    Ship(MessageDecode infoShip){
        this.myInfoShip = infoShip;
        //this.oldPositionsList = new ArrayList<>();
        //setMyCurrentPosition((myInfoShip==null) ? new Coordinate(0,0) : new Coordinate(myInfoShip.getLatitude(),myInfoShip.getLongitude()));

    }

    //------
    // coordinate
    //------
   /* private void setMyCurrentPosition(Coordinate coordinate) {
        this.myCurrentPosition = coordinate;
    }*/

    Coordinate getMyCurrentPosition() {
        return new Coordinate(myInfoShip.getLatitude(),myInfoShip.getLongitude());
    }
/*
    private void addToOldPosition(Coordinate coordinate){
        oldPositionsList.add(coordinate);
    }

    void setNewCurrentPosition(Coordinate coordinate){
        addToOldPosition(this.myCurrentPosition);
        setMyCurrentPosition(coordinate);
    }

    ArrayList<Coordinate> getOldPositionsList() {
        return oldPositionsList;
    }
*/


    @Override
    public String toString() {
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
}



class Coordinate{
    private double latitude;
    private double longitude;

    Coordinate(double latitude,double longitude){
        setLatitude(latitude);
        setlongitude(longitude);
    }

    private void setLatitude(double lat) {
        this.latitude = lat;
    }

    double getLatitude() {
        return latitude;
    }

    private void setlongitude(double lon) {
        this.longitude = lon;
    }

    double getLongitude() {
        return longitude;
    }

}