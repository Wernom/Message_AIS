package fr.ufc.l3info.projet;


import java.util.Map;
import java.util.TreeMap;

class Ship {
    private TreeMap<String, Message> messages;
    private TreeMap<String, Message> modifiedMessage;
    private String MMSI;

    Ship(String mmsi, TreeMap<String, Message> messages) {
        this.MMSI = mmsi;
        this.messages = messages;
        this.modifiedMessage = new TreeMap<>();
    }

    Ship(String mmsi) {
        this.MMSI = mmsi;
        this.messages = new TreeMap<>();
        this.modifiedMessage = new TreeMap<>();
    }

    void addMessage(Message message) {
        this.messages.put(message.getDecode().getUTCString(), message);
    }

    TreeMap<String, Message> getMessages() {
        return messages;
    }

    String getMMSI() {
        return MMSI;
    }

    Message getLastKnownMessage() {
        return messages.get(messages.lastKey());
    }

    /**
     * null value for a string or 1,000,000 for a double mean no modification.
     */
    void staticRangeModification(String repeatIndicator, String navigationStatus, double rateOverTurn,
                                 double speedOverGround, String positiontionAccuracy, double longitude, double latitude,
                                 double courseOverGroud, String trueHeading, String timeStamp, String maneuverIndicator,
                                 String spare, String RAIMflag, String radioStatus, int hour, int minute, int hourFrom,
                                 int minuteFrom, int hourTo, int minuteTo) {

        int timeFrom = hourFrom * 100 + minuteFrom;
        int timeTo = hourTo * 100 + minuteTo;

        for (Map.Entry<String, Message> data : this.messages.entrySet()) {
            int timeMessage = data.getValue().getDecode().getHour() * 100 + data.getValue().getDecode().getHour();
            Message modifiedMessage = new Message(data.getValue().getAis().getRawData());
            if (timeMessage >= timeFrom && timeMessage <= timeTo) {
                if (repeatIndicator != null)
                    modifiedMessage.getDecode().setRepeatIndicator(repeatIndicator);
                if (navigationStatus != null)
                    modifiedMessage.getDecode().setNavigationStatus(navigationStatus);
                if (rateOverTurn != 1000000)
                    modifiedMessage.getDecode().setRateOverTurn(rateOverTurn);
                if (speedOverGround != 1000000)
                    modifiedMessage.getDecode().setSpeedOverGround(speedOverGround);
                if (positiontionAccuracy != null)
                    modifiedMessage.getDecode().setPositiontionAccuracy(positiontionAccuracy);
                if (longitude != 1000000)
                    modifiedMessage.getDecode().setLongitude(longitude);
                if (latitude != 1000000)
                    modifiedMessage.getDecode().setLatitude(latitude);
                if (courseOverGroud != 1000000)
                    modifiedMessage.getDecode().setCourseOverGroud(courseOverGroud);
                if (trueHeading != null)
                    modifiedMessage.getDecode().setTrueHeading(trueHeading);
                if (timeStamp != null)
                    data.getValue().getDecode().setTimeStamp(timeStamp);
                if (maneuverIndicator != null)
                    modifiedMessage.getDecode().setManeuverIndicator(maneuverIndicator);
                if (spare != null)
                    modifiedMessage.getDecode().setSpare(spare);
                if (RAIMflag != null)
                    modifiedMessage.getDecode().setRAIMflag(RAIMflag);
                if (radioStatus != null)
                    modifiedMessage.getDecode().setRadioStatus(radioStatus);

                if (hour != 1000000)
                    modifiedMessage.getDecode().setHour(hour);
                if (minute != 1000000)
                    modifiedMessage.getDecode().setMinute(minute);

                modifiedMessage.encode();
                this.modifiedMessage.put(data.getValue().getDecode().getUTCString(), modifiedMessage);
            }
        }
    }

    String toStringMessage() {
        StringBuilder res = new StringBuilder();
        for (Map.Entry<String, Message> data : this.messages.entrySet()) {
            res.append(data.toString());
        }
        return res.toString();
    }

    String toStringModifiedMessage() {
        StringBuilder res = new StringBuilder();
        for (Map.Entry<String, Message> data : this.modifiedMessage.entrySet()) {
            res.append(data.getValue().toString());
        }
        return res.toString();
    }

}
