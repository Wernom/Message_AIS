package fr.ufc.l3info.projet;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

class Fenetre {

    private JFrame fenetre=new JFrame();
    private Carte map;
    private Menu menuBar;
    private MenuDeroulant menuDeroulant;

    /**
     * constructor of display
     */
    Fenetre(){
        menuBar = new Menu();

        menuBar.getMenuItem().addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fc = new JFileChooser();
                if (fc.showOpenDialog(menuBar.getMenu()) == JFileChooser.APPROVE_OPTION) {
                    try {
                        InputStream flux = new FileInputStream(fc.getSelectedFile());
                        InputStreamReader lecture = new InputStreamReader(flux);
                        BufferedReader buff = new BufferedReader(lecture);
                        String ligne;
                        while ((ligne = buff.readLine()) != null) {
                            System.out.println(ligne);

                            //---------
                            Message msg=new Message(ligne);
                            menuBar.getMessages().add(msg);
                            menuDeroulant.getDefaultList().addElement(msg.getDecode().getMMSI());
                            //---------
                            break; // les autres lignes du fichier ne sont peut etre pas encore traité
                        }
                        buff.close();
                    } catch (Exception ex) {
                        System.out.println(e.toString());
                    }
                    reloadMap();
                }
            }
        });
        //menuBar.addListenerToItemMenu(this);

        fenetre.setSize(400, 400);
        fenetre.setLayout(new BorderLayout());
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // creation de la carte
        map = new Carte();

        // creation du menu deroulant (liste bateau) a gauche
        menuDeroulant = new MenuDeroulant(menuBar.getMessages());

        // creation de la partie modification des message AIS en bas
        ModificationMessage modificationMessage = new ModificationMessage();

        // affichage
        //----------
        fenetre.setJMenuBar(menuBar.getMenuBar());
        //----------
        fenetre.add(menuDeroulant,BorderLayout.WEST);
        fenetre.add(map, BorderLayout.CENTER);
        fenetre.add(modificationMessage,BorderLayout.SOUTH);
        fenetre.setVisible(true);
    }


    //----------------

    /**
     * reload the map after import raw AIS
     */
    void reloadMap(){
        map.reloadMap(menuBar.getMessages());

    }


}
