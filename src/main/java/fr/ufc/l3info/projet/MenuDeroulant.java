package fr.ufc.l3info.projet;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

class MenuDeroulant extends JPanel {
    /*
        List of ship import from source file
     */
    private JPanel panel;
    private JComboBox<String> possibilitiesModifications;
    private JComboBox<String> choiceDisplayedMessage;
    private JList listDeroulante;
    private DefaultListModel<String> defaultList;

    /**
     * constructor
     */
    MenuDeroulant() {
        panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(125,0));
        initList();
        initChoicesList();
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
        Border border ;
        border = BorderFactory.createEtchedBorder();
        border = BorderFactory.createTitledBorder(border,"Ship List");
        scrollPane.setBorder(border);
        panel.add(scrollPane,BorderLayout.CENTER);
    }

    private void initChoicesList(){
        initPossibilities();
        initchoiceDisplayedMessage();
        JPanel choicePanel=new JPanel(new GridLayout(2,0));
        choicePanel.add(possibilitiesModifications);
        choicePanel.add(choiceDisplayedMessage);
        panel.add(choicePanel,BorderLayout.SOUTH);
    }

    /**
     * initialize list of possibilities
     */
    private void initPossibilities(){
        String defaultValue= "Hard";
        String[] possibilities={defaultValue,"Propagation"};
        possibilitiesModifications=new JComboBox<>(possibilities);
        Border border ;
        border = BorderFactory.createEmptyBorder();
        border = BorderFactory.createTitledBorder(border,"Modification type :");
        possibilitiesModifications.setSelectedItem(defaultValue);
        possibilitiesModifications.setBorder(border);
        possibilitiesModifications.setSelectedIndex(0);
    }

    /**
     * initialize type of displaying message
     */
    private void initchoiceDisplayedMessage(){
        String defaultValue= "Unmodified";
        String[] possibilities={"Unmodified","Modified"};
        choiceDisplayedMessage=new JComboBox<>(possibilities);
        Border border ;
        border = BorderFactory.createEmptyBorder();
        border = BorderFactory.createTitledBorder(border,"Message Displayed :");
        possibilitiesModifications.setSelectedItem(defaultValue);
        choiceDisplayedMessage.setBorder(border);
        choiceDisplayedMessage.setSelectedIndex(0);
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

    /**
     * @return JComboBox<String>
     */
    JComboBox getPossibilitiesModifications() {
        return possibilitiesModifications;
    }

    /**
     * @return JComboBox<String>
     */
    JComboBox<String> getChoiceDisplayedMessage() {
        return choiceDisplayedMessage;
    }
}

