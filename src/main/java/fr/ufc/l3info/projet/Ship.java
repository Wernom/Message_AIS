package fr.ufc.l3info.projet;

import java.util.HashMap;

public class Ship {
    private HashMap<String, Message> messages;

    Ship(HashMap<String, Message> messages) {
        this.messages = messages;
    }

    Ship() {
        this.messages = new HashMap<>();
    }

    public void addMessage(Message message) {
        this.messages.put(message.getDecode().getMMSI(), message);
    }


}
