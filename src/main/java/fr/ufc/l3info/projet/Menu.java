package fr.ufc.l3info.projet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

class Menu extends JFrame {
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu = new JMenu("Fichier");
    private JMenuItem menuItem = new JMenuItem("Importer");
    private JMenuItem menuItem2 = new JMenuItem("Exporter");


    Menu(JFrame fenetre) {
        menuBar.add(menu);
        menuItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e){
                //objet pour différencier les 2 boutons
                Object source = e.getSource();

                JFileChooser fc = new JFileChooser();
                if (fc.showOpenDialog(menu) == JFileChooser.APPROVE_OPTION) {
                    try {

                        Desktop.getDesktop().open(fc.getSelectedFile());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        menu.add(menuItem);
        menu.add(menuItem2);
        fenetre.setJMenuBar(menuBar);
    }
}
