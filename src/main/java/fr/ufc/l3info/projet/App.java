package fr.ufc.l3info.projet;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );


        //------- demo carte -------


        JFrame fenetre=new JFrame();
        fenetre.setSize(400, 400);
        fenetre.setLayout(new BorderLayout());
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);

            //creation du trafic
            ArrayList<Ship> trafic=new ArrayList<>();

            // creation de la carte
        Carte map = new Carte(trafic);
            // affichage de la map
        fenetre.add(map, BorderLayout.CENTER);
        fenetre.setVisible(true);

    }
}
