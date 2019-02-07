package fr.ufc.l3info.projet;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

class MenuDeroulant extends JPanel {
    /*
        List of ship import from source file
     */
    private JPanel panel;
    private JList listDeroulante;
    private DefaultListModel<String> defaultList;

    /**
     * constructor
     */
    MenuDeroulant() {
        panel = new JPanel(new BorderLayout());
        add(panel,BorderLayout.CENTER);
        Border border ;
        border = BorderFactory.createEtchedBorder();
        border = BorderFactory.createTitledBorder(border,"Ship List");
        panel.setBorder(border);
        panel.setPreferredSize(new Dimension(100,0));
        initList();
    }

    /**
     * initialize lis of ship
     */
    private void initList() {
        defaultList=new DefaultListModel<>();
        listDeroulante = new JList<>(defaultList);
        listDeroulante.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listDeroulante.setVisibleRowCount(-1);
        listDeroulante.setLayoutOrientation(JList.VERTICAL);
        defaultList.addElement("<none>");
        JScrollPane scrollPane=new JScrollPane(listDeroulante);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(scrollPane,BorderLayout.CENTER);
    }

    /**
     * @return Jpanel
     */
    JPanel getPanel() {
        return panel;
    }

    /**
     * @return JList
     */
    JList getListDeroulante() {
        return listDeroulante;
    }

    /**
     * @return DefaultListModel
     */
    DefaultListModel getDefaultList() {
        return defaultList;
    }

}

