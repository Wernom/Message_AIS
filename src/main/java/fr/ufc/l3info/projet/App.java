package fr.ufc.l3info.projet;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class App 
{

    public static void main( String[] args )
    {

        //creation du trafic
        Message msg = new Message("!AIVDM,1,1,,A,13u?etPv2;0n:dDPwUM1U1Cb069D,0*23");
        ArrayList<Ship> trafic=new ArrayList<>();
        ArrayList<Message> listMessage = new ArrayList<>();
        trafic.add(new Ship(msg.getDecode()));

        new Fenetre(trafic);

    }
}
