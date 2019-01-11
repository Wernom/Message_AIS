package fr.ufc.l3info.projet;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Fenetre {

    private JFrame fenetre=new JFrame();
    private ArrayList<Message> messages;
    private ArrayList<Ship> trafic;


    Fenetre(ArrayList<Ship> trafic){
        initArray();
        new Menu(fenetre);
        fenetre.setSize(400, 400);
        fenetre.setLayout(new BorderLayout());
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // creation de la carte
        Carte map = new Carte(trafic);

        // creation du menu deroulant (liste bateau) a gauche
        MenuDeroulant menuDeroulant = new MenuDeroulant();

        // creation de la partie modification des message AIS en bas
        ModificationMessage modificationMessage = new ModificationMessage();

        // affichage
        fenetre.add(menuDeroulant,BorderLayout.WEST);
        fenetre.add(map, BorderLayout.CENTER);
        fenetre.add(modificationMessage,BorderLayout.SOUTH);
        fenetre.setVisible(true);
    }

    private void initArray() {
        messages=new ArrayList<>();
        trafic = new ArrayList<>();
    }


}
