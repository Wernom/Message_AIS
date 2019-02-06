package fr.ufc.l3info.projet;



import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

//class Menu extends JFrame {
class Menu extends JMenuBar {
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu = new JMenu("Fichier");
    private JMenu menu2 = new JMenu("Exporter");
    private JMenuItem menuItem = new JMenuItem("Importer");
    private JMenuItem menuItem2 = new JMenuItem("ExporterCsv");
    private JMenuItem menuItem3 = new JMenuItem("ExporterAis");
    private HashMap<String,Ship> shipsMessages;

    public static final String FRAME_BOUNDS = "frameBounds";
    public static final String FIELD_TEXT = "fieldText";

    Menu(/*Fenetre fenetre*/) {
        //---------
        shipsMessages = new HashMap<>();
        menuBar.add(menu);
        menu.add(menu2);
        menu.add(menuItem);
        menu2.add(menuItem2);
        menu2.add(menuItem3);
    }

    private Path getApplicationDataFile(boolean create) throws IOException {

        final Path applicationDataDir = Paths.get(System.getProperty("user.home"), "monApplication"); // un dossier pour stocker les données des l'application
        if ( create && !Files.exists(applicationDataDir) ) { // création du dossier s'il n'existe pas
            Files.createDirectories(applicationDataDir);
        }
        return applicationDataDir.resolve("monFichier.dat"); // retourne un nom de fichier pour stocker les info

    }


    //--------------
    HashMap<String,Ship> getShips() {
        return shipsMessages;
    }

    Ship getShip(String MMSI){
        return shipsMessages.get(MMSI);
    }

    JMenuBar getMenuBar() {
        return menuBar;
    }




    JMenuItem getMenuItem() {
        return menuItem;
    }
    JMenuItem getMenuItemExporterCsv() {
        return menuItem2;
    }
    JMenuItem getMenuItemExporterAis() {
        return menuItem3;
    }
    JMenu getMenu() {
        return menu;
    }
}
