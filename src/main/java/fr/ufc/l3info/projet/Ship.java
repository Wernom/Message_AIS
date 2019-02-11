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

    TreeMap<String, Message> getModifiedMessage() {
        return modifiedMessage;
    }

    String getMMSI() {
        return MMSI;
    }

    Message getLastKnownMessage() {
        return messages.get(messages.lastKey());
    }

    Message getLastKnownModifiedMessage() {
        if(modifiedMessage.size()==0){
            return null;
        }
        return modifiedMessage.get(modifiedMessage.lastKey());
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
            int timeMessage = data.getValue().getDecode().getHour() * 100 + data.getValue().getDecode().getMinute();
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
                    modifiedMessage.getDecode().setTimeStamp(timeStamp);
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
                this.modifiedMessage.put(modifiedMessage.getDecode().getUTCString(), modifiedMessage);
            }
        }
    }

    private double knotToMPH(double speedOverGround) {
        double conversionFactor = 1.15078;
        return speedOverGround * conversionFactor;
    }

    void speedModificationAffectPosition(double newSpeedOverGround, int hourFrom, int minuteFrom, int hourTo, int minuteTo) {
        int timeFrom = hourFrom * 100 + minuteFrom;
        int timeTo = hourTo * 100 + minuteTo;
        double earthRadiusM = 6371000;
        double offcetX = 0;
        double offcetY = 0;
        double offcetZ = 0;
        for (Map.Entry<String, Message> data : this.messages.entrySet()) {
            int timeMessage = data.getValue().getDecode().getHour() * 100 + data.getValue().getDecode().getMinute();
            Message modifiedMessage = new Message(data.getValue().getAis().getRawData());
            if (timeMessage >= timeFrom && timeMessage <= timeTo) {
                //Calcultion of coordinate x, y, z from the current message;
                double x1, y1, z1;
                x1 = earthRadiusM * Math.sin(Math.PI - data.getValue().getDecode().getLatitude()) * Math.cos(data.getValue().getDecode().getLongitude());
                y1 = earthRadiusM * Math.sin(Math.PI - data.getValue().getDecode().getLatitude()) * Math.sin(data.getValue().getDecode().getLongitude());
                z1 = earthRadiusM * Math.cos(Math.PI - data.getValue().getDecode().getLatitude());

                //Calcultion of coordinate x, y, z from the next message;
                Message nextMessage = this.messages.higherEntry(data.getKey()).getValue();
                double x2, y2, z2;
                x2 = earthRadiusM * Math.sin(Math.PI - nextMessage.getDecode().getLatitude()) * Math.cos(nextMessage.getDecode().getLongitude());
                y2 = earthRadiusM * Math.sin(Math.PI - nextMessage.getDecode().getLatitude()) * Math.sin(nextMessage.getDecode().getLongitude());
                z2 = earthRadiusM * Math.cos(Math.PI - nextMessage.getDecode().getLatitude());


                //Calculation of speed vector.
                double normeOM = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2));
                double unitVectorX = (x2 - x1) / normeOM;
                double unitVectorY = (y2 - y1) / normeOM;
                double unitVectorZ = (z2 - z1) / normeOM;
                //Calculation of dt(hour) ->  difference of the emission time of the previous message and current
                double dt = (data.getValue().getDecode().getHour() + data.getValue().getDecode().getMinute() / 60.0) -
                        (nextMessage.getDecode().getHour() + nextMessage.getDecode().getMinute() / 60.0);


                //Calculation of the new next message position.
                double newX, newY, newZ;
                offcetX += this.knotToMPH(newSpeedOverGround) * unitVectorX * dt;
                offcetY += this.knotToMPH(newSpeedOverGround) * unitVectorY * dt;
                offcetZ += this.knotToMPH(newSpeedOverGround) * unitVectorZ * dt;
                newX = x1 + offcetX;
                newY = y1 + offcetY;
                newZ = z1 + offcetZ;

                //Calculation of the new longitude and latitude.
                addModifiedMessage(earthRadiusM, modifiedMessage, newX, newY, newZ);
                
            } else if (timeMessage > timeTo) {

                double x1 = earthRadiusM * Math.sin(Math.PI - data.getValue().getDecode().getLatitude()) * Math.cos(data.getValue().getDecode().getLongitude());
                double y1 = earthRadiusM * Math.sin(Math.PI - data.getValue().getDecode().getLatitude()) * Math.sin(data.getValue().getDecode().getLongitude());
                double z1 = earthRadiusM * Math.cos(Math.PI - data.getValue().getDecode().getLatitude());

                double newX = x1 + offcetX;
                double newY = y1 + offcetY;
                double newZ = z1 + offcetZ;

                addModifiedMessage(earthRadiusM, modifiedMessage, newX, newY, newZ);
            }
        }
    }

    private void addModifiedMessage(double earthRadiusM, Message modifiedMessage, double newX, double newY, double newZ) {
        double newLatitude = Math.PI / 2 - Math.acos(newZ / earthRadiusM);
        double newLongitude;
        if (newY >= 0) {
            newLongitude = Math.acos(newX / Math.sqrt(newX * newX + newY * newY));
        } else {
            newLongitude = 2 * Math.PI - Math.acos(newX / Math.sqrt(newX * newX + newY * newY));
        }

        modifiedMessage.getDecode().setLatitude(newLatitude);
        modifiedMessage.getDecode().setLongitude(newLongitude);

        modifiedMessage.encode();
        this.modifiedMessage.put(modifiedMessage.getDecode().getUTCString(), modifiedMessage);
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
