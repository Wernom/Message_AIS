package fr.ufc.l3info.projet;



import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

//class Menu extends JFrame {
class Menu extends JMenuBar {
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu = new JMenu("Fichier");
    private JMenuItem menuItem = new JMenuItem("Importer");
    private JMenuItem menuItem2 = new JMenuItem("Exporter");
    private ArrayList<Message> messages;

    public static final String FRAME_BOUNDS = "frameBounds";
    public static final String FIELD_TEXT = "fieldText";

    Menu(/*Fenetre fenetre*/) {
        //---------
        messages = new ArrayList<>();

        menuBar.add(menu);

      /*----------------
      addListenerToItemMenu(fenetre);
       */

        menuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*try(BufferedWriter writer = Files.newBufferedWriter(applicationDataFile) ) {
                    writer.write("test");
                    writer.write('=');
                    writer.write("lol");
                    writer.newLine();
                }*/
            }
        });
        menu.add(menuItem);
        menu.add(menuItem2);
        //---------
        //fenetre.setJMenuBar(menuBar);
    }

    private Path getApplicationDataFile(boolean create) throws IOException {

        final Path applicationDataDir = Paths.get(System.getProperty("user.home"), "monApplication"); // un dossier pour stocker les données des l'application
        if ( create && !Files.exists(applicationDataDir) ) { // création du dossier s'il n'existe pas
            Files.createDirectories(applicationDataDir);
        }
        return applicationDataDir.resolve("monFichier.dat"); // retourne un nom de fichier pour stocker les info

    }


    //--------------
    ArrayList<Message> getMessages() {
        return messages;
    }

    JMenuBar getMenuBar() {
        return menuBar;
    }

    void addListenerToItemMenu(final Fenetre fenetre){
        menuItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fc = new JFileChooser();
                if (fc.showOpenDialog(menu) == JFileChooser.APPROVE_OPTION) {
                    try {
                        InputStream flux = new FileInputStream(fc.getSelectedFile());
                        InputStreamReader lecture = new InputStreamReader(flux);
                        BufferedReader buff = new BufferedReader(lecture);
                        String ligne;
                        while ((ligne = buff.readLine()) != null) {
                            System.out.println(ligne);

                            //---------
                            messages.add(new Message(ligne));
                            //---------
                            break; // les autres lignes du fichier ne sont peut etre pas encore traité
                        }
                        buff.close();
                    } catch (Exception ex) {
                        System.out.println(e.toString());
                    }
                    fenetre.reloadMap();
                }
            }
        });
    }

    JMenuItem getMenuItem() {
        return menuItem;
    }

    JMenu getMenu() {
        return menu;
    }
}
