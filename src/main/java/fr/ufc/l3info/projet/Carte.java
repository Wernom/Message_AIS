package fr.ufc.l3info.projet;

import org.openstreetmap.gui.jmapviewer.*;
import org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;
import org.openstreetmap.gui.jmapviewer.interfaces.JMapViewerEventListener;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedList;


public class Carte extends JFrame implements JMapViewerEventListener {

    private static final long serialVersionUID = 1L;

    private JMapViewerTree treeMap;
    private JLabel zoomLabel;
    private JLabel zoomValue;
    private JLabel mperpLabelName;
    private JLabel mperpLabelValue;

// list of all ships
    public ArrayList<Ship> myTrafic;

    /**
     * Setups the JFrame layout, sets some default options for the JMapViewerTree and displays a map in the window.
     */
    public Carte(ArrayList<Ship> trafic) {


        super("JMapViewer Test");
        treeMap = new JMapViewerTree("Zones");
        setupJFrame();
        setupPanels();

        // initialisation du trafic
        this.myTrafic=trafic;

        // Listen to the map viewer for user operations so components will
        // receive events and updates
        map().addJMVListener(this);

        // Set some options, e.g. tile source and that markers are visible
        map().setTileSource(new OsmTileSource.Mapnik());
        map().setTileLoader(new OsmTileLoader(map()));
        map().setMapMarkerVisible(true);
        map().setZoomContolsVisible(true);

        printTrafic();

        // activate map in window
        treeMap.setTreeVisible(true);
        add(treeMap, BorderLayout.CENTER);
    }

    /**
     * setup JFrame
     */
    private void setupJFrame() {
        setSize(400, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    /**
     * setup JPanel
     */
    private void setupPanels() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelTop = new JPanel();
        JPanel panelBottom = new JPanel();
        JPanel helpPanel = new JPanel();

        mperpLabelName = new JLabel("Meters/Pixels: ");
        mperpLabelValue = new JLabel(String.format("%s", map().getMeterPerPixel()));
        zoomLabel = new JLabel("Zoom: ");
        zoomValue = new JLabel(String.format("%s", map().getZoom()));

        add(panel, BorderLayout.NORTH);
        add(helpPanel, BorderLayout.SOUTH);
        panel.add(panelTop, BorderLayout.NORTH);
        panel.add(panelBottom, BorderLayout.SOUTH);
        JLabel helpLabel = new JLabel("Use right mouse button to move,\n "
                + "left double click or mouse wheel to zoom.");
        helpPanel.add(helpLabel);

        panelTop.add(zoomLabel);
        panelTop.add(zoomValue);
        panelTop.add(mperpLabelName);
        panelTop.add(mperpLabelValue);
    }

    /**
     * @return JMapViewerTree a tree map
     */
    private JMapViewer map() {
        return treeMap.getViewer();
    }

    /**
     * print a ship with his old position
     * @param vessel Ship 
     */
    public void printShip(Ship vessel){
        map().addMapMarker(new MapMarkerDot(vessel.getMyCurrentPosition().getD_latitude(),vessel.getMyCurrentPosition().getD_longitude()));
        for (Coordinate oldCoordinate: vessel.getOldPositionsList()) {
            MapMarkerCircle oldPosition = new  MapMarkerCircle(oldCoordinate.getD_latitude(),oldCoordinate.getD_longitude(),0.03);
            oldPosition.setColor(Color.black);
            oldPosition.setBackColor(Color.black);
            map().addMapMarker(oldPosition);
        }
    }

    /**
     * print all ships
     */
    public void printTrafic(){
        for (Ship vessel:myTrafic) {
            printShip(vessel);
        }
    }

    /**
     * update zoom parameters
     */
    private void updateZoomParameters() {
        if (mperpLabelValue != null)
            mperpLabelValue.setText(String.format("%s", map().getMeterPerPixel()));
        if (zoomValue != null)
            zoomValue.setText(String.format("%s", map().getZoom()));
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

    private Coordinate myCurrentPosition;
    private ArrayList<Coordinate> oldPositionsList; // faire une list de Ship pour garder en mémoire les anciennes info de navigations ?
    private String myName;
    private String myMMSI;
    private String myNavigationStatus;
    private String myRateOfTurn;
    private String mySpeedOverGround;
    private String myPositiontionAccuracy;
    private String myCourseOverGround;
    private String myTrueHeading;
    private String myTimeStamp;
    private String myManeuverIndicator;
    //other information

    //------
    // constructor
    //------
    Ship(){
        this("0","0");
    }

    Ship(String latitude,String longitude){
        this(new Coordinate(latitude,longitude));
    }

    Ship(String latitude,String longitude,String name){
        this(new Coordinate(latitude,longitude),name);
    }

    Ship(Coordinate coordinate){
        this(coordinate,"GhostShip");
    }

    Ship(Coordinate coordinate,String name){
        this(coordinate,name,"000000000");
    }

    Ship(Coordinate coordinate,String name,String MMSI){
        this(coordinate,name,MMSI,"","","","","","","","");
    }

    Ship(Coordinate coordinate,String name,String MMSI, String navigationStatus, String rateOfTurn, String speedOverGround, String positiontionAccuracy, String courseOverGround, String trueHeading, String timeStamp, String maneuverIndicator){
        setCurrentCoordinate(coordinate);
        setMyName(name);
        setMyMMSI(MMSI);
        setMyNavigationStatus(navigationStatus);
        setMyRateOfTurn(rateOfTurn);
        setMySpeedOverGround(speedOverGround);
        setMyPositiontionAccuracy(positiontionAccuracy);
        setMyCourseOverGround(courseOverGround);
        setMyTrueHeading(trueHeading);
        setMyTimeStamp(timeStamp);
        setMyManeuverIndicator(maneuverIndicator);
        oldPositionsList = new ArrayList<>();
    }

    //------
    // coordinate
    //------
    public void setCurrentCoordinate(Coordinate coordinate) {
        this.myCurrentPosition = coordinate;
    }

    public void setCurrentCoordinate(String latitude,String longitude) {
        this.myCurrentPosition = new Coordinate(latitude,longitude);
    }

    public Coordinate getCurrentCoordinate() {
        return myCurrentPosition;
    }

    public void addToOldPosition(Coordinate coordinate){
        oldPositionsList.add(coordinate);
    }

    public void setNewCurrentPosition(Coordinate coordinate){
        addToOldPosition(this.myCurrentPosition);
        setCurrentCoordinate(coordinate);
    }

    public ArrayList<Coordinate> getOldPositionsList() {
        return oldPositionsList;
    }

    public Coordinate getMyCurrentPosition() {
        return myCurrentPosition;
    }
    //------
    // name
    //------
    public void setMyName(String name) {
        this.myName = name;
    }

    public String getMyName() {
        return myName;
    }
    //------
    // MMSI
    //------

    public void setMyMMSI(String myMMSI) {
        this.myMMSI = myMMSI;
    }

    public String getMyMMSI() {
        return myMMSI;
    }

    //------
    // navigationStatus
    //------

    public void setMyNavigationStatus(String myNavigationStatus) {
        this.myNavigationStatus = myNavigationStatus;
    }

    public String getMyNavigationStatus() {
        return myNavigationStatus;
    }

    //------
    // rateOfTurn
    //------

    public void setMyRateOfTurn(String myRateOfTurn) {
        this.myRateOfTurn = myRateOfTurn;
    }

    public String getMyRateOfTurn() {
        return myRateOfTurn;
    }

    //------
    // speedOverGround
    //------

    public void setMySpeedOverGround(String mySpeedOverGround) {
        this.mySpeedOverGround = mySpeedOverGround;
    }

    public String getMySpeedOverGround() {
        return mySpeedOverGround;
    }

    //------
    // positiontionAccuracy
    //------

    public void setMyPositiontionAccuracy(String myPositiontionAccuracy) {
        this.myPositiontionAccuracy = myPositiontionAccuracy;
    }

    public String getMyPositiontionAccuracy() {
        return myPositiontionAccuracy;
    }

    //------
    //  courseOverGroud
    //------

    public void setMyCourseOverGround(String myCourseOverGround) {
        this.myCourseOverGround = myCourseOverGround;
    }

    public String getMyCourseOverGround() {
        return myCourseOverGround;
    }

    //------
    // trueHeading
    //------

    public void setMyTrueHeading(String myTrueHeading) {
        this.myTrueHeading = myTrueHeading;
    }

    public String getMyTrueHeading() {
        return myTrueHeading;
    }

    //------
    // timeStamp
    //------

    public void setMyTimeStamp(String myTimeStamp) {
        this.myTimeStamp = myTimeStamp;
    }

    public String getMyTimeStamp() {
        return myTimeStamp;
    }

    //------
    // maneuverIndicator
    //------

    public void setMyManeuverIndicator(String myManeuverIndicator) {
        this.myManeuverIndicator = myManeuverIndicator;
    }

    public String getMyManeuverIndicator() {
        return myManeuverIndicator;
    }

    @Override
    public String toString() {
        return "Vessel:" +
                ", \n\tMMSI='" + myMMSI +
                ", \n\tnavigationStatus='" + myNavigationStatus +
                ", \n\tspeedOverGround='" + mySpeedOverGround +
                ", \n\tpositiontionAccuracy='" + myPositiontionAccuracy +
                ", \n\tlongitude='" + myCurrentPosition.getStr_longitude() +
                ", \n\tlatitude='" + myCurrentPosition.getStr_latitude() +
                ", \n\tcourseOverGround='" + myCourseOverGround +
                ", \n\ttrueHeading='" + myTrueHeading +
                ", \n\ttimeStamp='" + myTimeStamp +
                ", \n\tmaneuverIndicator='" + myManeuverIndicator +
              /*  ", \n\tspare='" + mySpare +
                ", \n\tRAIMflag='" + RAIMflag +
                ", \n\tradioStatus='" + myRadioStatus +
              */ "\n\t}";
    }
}



class Coordinate{
    private String str_latitude;
    private String str_longitude;

    Coordinate(String latitude,String longitude){
        setStr_latitude(latitude);
        setStr_longitude(longitude);
    }

    public void setStr_latitude(String lat) {
        this.str_latitude = lat;
    }

    public String getStr_latitude() {
        return str_latitude;
    }

    public void setStr_longitude(String lon) {
        this.str_longitude = lon;
    }

    public String getStr_longitude() {
        return str_longitude;
    }

    public double getD_latitude() {
        return Double.parseDouble(str_latitude);
    }

    public double getD_longitude() {
        return Double.parseDouble(str_longitude);
    }
}