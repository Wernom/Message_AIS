package fr.ufc.l3info.projet;


import java.util.TreeMap;

class Ship {
    private TreeMap<String, Message> messages;
    private TreeMap<String, Message> modifiedMessage;
    private String MMSI;

    Ship(String mmsi,TreeMap<String, Message> messages) {
        this.MMSI=mmsi;
        this.messages = messages;
        this.modifiedMessage = new TreeMap<>();
    }

    Ship(String mmsi) {
        this.MMSI=mmsi;
        this.messages = new TreeMap<>();
        this.modifiedMessage = new TreeMap<>();
    }

    void addMessage(Message message) {
        this.messages.put(message.getDecode().getTimeStamp(), message);
    }

    TreeMap<String, Message> getMessages() {
        return messages;
    }

    public String getMMSI() {
        return MMSI;
    }

    Message getLastKnownMessage() {
        return messages.get(messages.lastKey());
    }
}
