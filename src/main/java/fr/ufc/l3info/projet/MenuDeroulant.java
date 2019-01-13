package fr.ufc.l3info.projet;

import javax.swing.*;
import java.awt.*;

class MenuDeroulant extends JPanel {
    /*
        affichage des bateau et des informations de ceux ci
     */
    private JPanel panel;
    private JList listDeroulante;
    private DefaultListModel<String> defaultList;

    MenuDeroulant() {
        panel = new JPanel(new BorderLayout());
        add(panel,BorderLayout.NORTH);
        add(panel,BorderLayout.CENTER);

        JLabel testList = new JLabel("Ship List :");
        panel.add(testList,BorderLayout.NORTH);
        initList();
    }

    private void initList() {
        defaultList=new DefaultListModel<>();
        listDeroulante = new JList<>(defaultList);
        listDeroulante.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listDeroulante.setLayoutOrientation(JList.VERTICAL);
        panel.add(listDeroulante,BorderLayout.CENTER);
    }

    JList getListDeroulante() {
        return listDeroulante;
    }

    DefaultListModel getDefaultList() {
        return defaultList;
    }

}

