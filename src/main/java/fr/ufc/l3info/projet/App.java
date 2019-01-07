package fr.ufc.l3info.projet;

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
            //creation du trafic
            ArrayList<Ship> trafic=new ArrayList<>();
            // creation des navires
            Ship Titanic = new Ship(0,0);
            Ship QueensAnneRevenge = new Ship(-10,-10);
            //deplacement d'un navire
            Titanic.setNewCurrentPosition(new Coordinate(1,1));
            Titanic.setNewCurrentPosition(new Coordinate(1.5,1.5));
            Titanic.setNewCurrentPosition(new Coordinate(3,3));
            //ajout des navires
            trafic.add(Titanic);
            trafic.add(QueensAnneRevenge);
            // creation de la carte
            Carte map = new Carte(trafic);
            // affichage de la map
            map.setVisible(true);
    }
}
