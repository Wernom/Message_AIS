package fr.ufc.l3info.projet;



import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

class Fenetre {

    private JFrame fenetre=new JFrame();
    private Carte map;
    private Menu menuBar;
    private MenuDeroulant menuDeroulant;
    private ModificationMessage modificationMessage;

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
                        int ENLEVEMOI=0;
                        while ((ligne = buff.readLine()) != null) {
                            //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                            ++ENLEVEMOI;
                            //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                            Message msg=new Message(ligne);
                            menuBar.getMessages().put(msg.getDecode().getMMSI(),msg);
                            menuDeroulant.getDefaultList().addElement(msg.getDecode().getMMSI());
                            //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                            if(ENLEVEMOI==3)
                                break; // les autres lignes du fichier ne sont peut etre pas encore traité
                            //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
                        }
                        buff.close();
                    } catch (Exception ex) {
                        System.out.println(e.toString());
                    }
                    reloadMap();
                }
            }
        });

        menuBar.getMenuItemExporter().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                if (fc.showOpenDialog(menuBar.getMenu()) == JFileChooser.APPROVE_OPTION) {
                    final String chemin = fc.getSelectedFile().getAbsolutePath();
                    final File fichier = new File(chemin);
                    System.out.println(chemin);
                    try {
                        fichier.createNewFile();
                        final FileWriter writer = new FileWriter(fichier);
                        try {
                            /*for (menuBar.getMessages() :
                                 ) {
                                
                            }*/
                            writer.write("Test\n");
                        } finally {
                            writer.close();
                        }
                    } catch (Exception ex) {
                        System.out.println("Impossible de creer le fichier");
                    }
                }
            }

        });

        BorderLayout layout= new BorderLayout(4,4);
        fenetre.setLayout(layout);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setSize(800, 700);
        fenetre.setMinimumSize(new Dimension(800,700));

        // creation de la carte
        map = new Carte();

        // creation du menu deroulant (liste bateau) a gauche
        menuDeroulant = new MenuDeroulant();
        menuDeroulant.setPreferredSize(new Dimension(100,0));
        menuDeroulant.getListDeroulante().addListSelectionListener(addSelectListener());

        // creation de la partie modification des message AIS en bas
        modificationMessage = new ModificationMessage();
        modificationMessage.getPanel().setPreferredSize(new Dimension(0,150));
        // affichage
        //----------
        fenetre.setJMenuBar(menuBar.getMenuBar());
        //----------
        fenetre.add(menuDeroulant,BorderLayout.WEST);
        fenetre.add(map.getPanel(), BorderLayout.CENTER);
        fenetre.add(modificationMessage.getPanel(),BorderLayout.SOUTH);
        fenetre.setVisible(true);
    }


    //----------------

    /**
     * reload the map after import raw AIS
     */
    private void reloadMap(){
        map.reloadMap(menuBar.getMessages());

    }


    private ListSelectionListener addSelectListener(){
        return new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    modificationMessage.affichage(menuBar.getMessages().get((String)menuDeroulant.getListDeroulante().getSelectedValue()));
                    modificationMessage.getPanel().revalidate();
                    modificationMessage.getPanel().updateUI();
                }

            }
        };
    }

}
