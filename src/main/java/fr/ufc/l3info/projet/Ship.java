package fr.ufc.l3info.projet;

import java.util.HashMap;

class Ship {
    private HashMap<String, Message> messages;

    Ship(HashMap<String, Message> messages) {
        this.messages = messages;
    }

    Ship() {
        this.messages = new HashMap<>();
    }

    void addMessage(Message message) {
        this.messages.put(message.getDecode().getTimeStamp(), message);
    }

    HashMap<String, Message> getMessages() {
        return messages;
    }

    Message getLastKnownMessage() {
        long last=-1;
        long notLast=0;
        for (Message msg: messages.values()) {
            last=Long.decode(msg.getDecode().getTimeStamp());
            if (last<notLast){
                last=notLast;
            }else {
                notLast=last;
            }
        }
        return messages.get(Long.toString(last));
    }
}
