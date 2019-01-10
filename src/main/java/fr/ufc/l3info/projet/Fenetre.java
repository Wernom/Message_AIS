package fr.ufc.l3info.projet;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Fenetre {

    private JFrame fenetre=new JFrame();


    Fenetre(ArrayList<Ship> trafic){
        new Menu(fenetre);
        fenetre.setSize(400, 400);
        fenetre.setLayout(new BorderLayout());
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // creation de la carte
        Carte map = new Carte(trafic);
        // affichage de la map

        fenetre.add(map, BorderLayout.CENTER);
        fenetre.setVisible(true);
    }

}
