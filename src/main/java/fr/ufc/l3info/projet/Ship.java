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

            if (timeMessage >= timeFrom && timeMessage <= timeTo) {
                if (repeatIndicator != null)
                    data.getValue().getDecode().setRepeatIndicator(repeatIndicator);
                if (navigationStatus != null)
                    data.getValue().getDecode().setNavigationStatus(navigationStatus);
                if (rateOverTurn != 1000000)
                    data.getValue().getDecode().setRateOverTurn(rateOverTurn);
                if (speedOverGround != 1000000)
                    data.getValue().getDecode().setSpeedOverGround(speedOverGround);
                if (positiontionAccuracy != null)
                    data.getValue().getDecode().setPositiontionAccuracy(positiontionAccuracy);
                if (longitude != 1000000)
                    data.getValue().getDecode().setLongitude(longitude);
                if (latitude != 1000000)
                    data.getValue().getDecode().setLatitude(latitude);
                if (courseOverGroud != 1000000)
                    data.getValue().getDecode().setCourseOverGroud(courseOverGroud);
                if (trueHeading != null)
                    data.getValue().getDecode().setTrueHeading(trueHeading);
                if (timeStamp != null)
                    data.getValue().getDecode().setTimeStamp(timeStamp);
                if (maneuverIndicator != null)
                    data.getValue().getDecode().setManeuverIndicator(maneuverIndicator);
                if (spare != null)
                    data.getValue().getDecode().setSpare(spare);
                if (RAIMflag != null)
                    data.getValue().getDecode().setRAIMflag(RAIMflag);
                if (radioStatus != null)
                    data.getValue().getDecode().setRadioStatus(radioStatus);

                if (hour != 1000000)
                    data.getValue().getDecode().setHour(hour);
                if (minute != 1000000)
                    data.getValue().getDecode().setMinute(minute);

                data.getValue().encode();
                this.modifiedMessage.put(data.getValue().getDecode().getUTCString(), data.getValue());
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
