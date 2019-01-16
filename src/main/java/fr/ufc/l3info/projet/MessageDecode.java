package fr.ufc.l3info.projet;

import java.io.*;

class MessageDecode {
    private String messageType;
    private String repeatIndicator;
    private String MMSI;
    private String navigationStatus;
    private double rateOverTurn;
    private double speedOverGround;
    private String positiontionAccuracy;
    private double longitude;
    private double latitude;
    private double courseOverGroud;
    private String trueHeading;
    private String timeStamp;
    private String maneuverIndicator;
    private String spare;

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public void setRepeatIndicator(String repeatIndicator) {
        this.repeatIndicator = repeatIndicator;
    }

    public void setMMSI(String MMSI) {
        this.MMSI = MMSI;
    }

    public void setNavigationStatus(String navigationStatus) {
        this.navigationStatus = navigationStatus;
    }

    public void setSpeedOverGround(double speedOverGround) {
        this.speedOverGround = speedOverGround;
    }

    public void setPositiontionAccuracy(String positiontionAccuracy) {
        this.positiontionAccuracy = positiontionAccuracy;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setCourseOverGroud(double courseOverGroud) {
        this.courseOverGroud = courseOverGroud;
    }

    public void setTrueHeading(String trueHeading) {
        this.trueHeading = trueHeading;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setManeuverIndicator(String maneuverIndicator) {
        this.maneuverIndicator = maneuverIndicator;
    }

    public void setSpare(String spare) {
        this.spare = spare;
    }

    public void setRAIMflag(String RAIMflag) {
        this.RAIMflag = RAIMflag;
    }

    public void setRadioStatus(String radioStatus) {
        this.radioStatus = radioStatus;
    }

    private String RAIMflag;
    private String radioStatus;

    MessageDecode(String messageType, String repeatIndicator, String MMSI, String navigationStatus,double rateOverTurn, double speedOverGround, String positiontionAccuracy, double longitude, double latitude, double courseOverGroud, String trueHeading, String timeStamp, String maneuverIndicator, String spare, String RAIMflag, String radioStatus) {
        this.messageType = messageType;
        this.repeatIndicator = repeatIndicator;
        this.MMSI = MMSI;
        this.navigationStatus = navigationStatus;
        this.rateOverTurn = rateOverTurn;
        this.speedOverGround = speedOverGround;
        this.positiontionAccuracy = positiontionAccuracy;
        this.longitude = longitude;
        this.latitude = latitude;
        this.courseOverGroud = courseOverGroud;
        this.trueHeading = trueHeading;
        this.timeStamp = timeStamp;
        this.maneuverIndicator = maneuverIndicator;
        this.spare = spare;
        this.RAIMflag = RAIMflag;
        this.radioStatus = radioStatus;
    }

    MessageDecode(String message[]){
        this.messageType = message[0];
        this.repeatIndicator = message[1];
        this.MMSI = message[2];
        this.navigationStatus = message[3];
        this.speedOverGround = Double.parseDouble(message[5]);
        this.positiontionAccuracy = message[6];
        this.longitude = Double.parseDouble(message[7]);
        this.latitude = Double.parseDouble(message[8]);
        this.courseOverGroud = Double.parseDouble(message[9]);
        this.trueHeading = message[10];
        this.timeStamp = message[11];
        this.maneuverIndicator = message[12];
        this.spare = message[13];
        this.RAIMflag = message[14];
        this.radioStatus = message[15];
    }


    String getMessageType() {
        return messageType;
    }

    String getRepeatIndicator() {
        return repeatIndicator;
    }

    String getMMSI() {
        return MMSI;
    }

    String getNavigationStatus() {
        return navigationStatus;
    }

    double getSpeedOverGround() {
        return speedOverGround;
    }

    String getPositiontionAccuracy() {
        return positiontionAccuracy;
    }

    double getLongitude() {
        return longitude;
    }

    double getLatitude() {
        return latitude;
    }

    public double getCourseOverGroud() {
        return courseOverGroud;
    }

    String getTrueHeading() {
        return trueHeading;
    }

    String getTimeStamp() {
        return timeStamp;
    }

    String getManeuverIndicator() {
        return maneuverIndicator;
    }

    String getSpare() {
        return spare;
    }

    String getRAIMflag() {
        return RAIMflag;
    }

    String getRadioStatus() {
        return radioStatus;
    }

    void printMessage(String fileName) {

        String content = this.messageType +',' + this.repeatIndicator +',' + this.MMSI + ',' + this.navigationStatus +',' + ',' + this.speedOverGround  + ',' + this.positiontionAccuracy +',' + this.longitude +',' + this.latitude +',' + this.courseOverGroud +',' + this.trueHeading +',' + this.timeStamp +',' + this.maneuverIndicator +',' + this.spare +',' + this.RAIMflag +',' + this.radioStatus;
        try {
            File file = new File(fileName);

            if (!file.exists()) {
                try {
                    file.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "MessageDecode{" +
                "\n\tmessageType='" + messageType + '\'' +
                ", \n\trepeatIndicator='" + repeatIndicator + '\'' +
                ", \n\tMMSI='" + MMSI + '\'' +
                ", \n\tnavigationStatus='" + navigationStatus + '\'' +
                ", \n\trateOverTurn='" + rateOverTurn + '\'' +
                ", \n\tspeedOverGround='" + speedOverGround + '\'' +
                ", \n\tpositiontionAccuracy='" + positiontionAccuracy + '\'' +
                ", \n\tlongitude='" + longitude + '\'' +
                ", \n\tlatitude='" + latitude + '\'' +
                ", \n\tcourseOverGroud='" + courseOverGroud + '\'' +
                ", \n\ttrueHeading='" + trueHeading + '\'' +
                ", \n\ttimeStamp='" + timeStamp + '\'' +
                ", \n\tmaneuverIndicator='" + maneuverIndicator + '\'' +
                ", \n\tspare='" + spare + '\'' +
                ", \n\tRAIMflag='" + RAIMflag + '\'' +
                ", \n\tradioStatus='" + radioStatus + '\'' +
                "\n\t}";
    }
}
