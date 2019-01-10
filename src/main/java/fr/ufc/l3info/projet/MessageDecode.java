package fr.ufc.l3info.projet;

import java.io.*;

class MessageDecode {
    private String messageType;
    private String repeatIndicator;
    private String MMSI;
    private String navigationStatus;
    private double speedOverGround;
    private String positiontionAccuracy;
    private double longitude;
    private double latitude;
    private double courseOverGroud;
    private String trueHeading;
    private String timeStamp;
    private String maneuverIndicator;
    private String spare;
    private String RAIMflag;
    private String radioStatus;

    MessageDecode(String messageType, String repeatIndicator, String MMSI, String navigationStatus, double speedOverGround, String positiontionAccuracy, double longitude, double latitude, double courseOverGroud, String trueHeading, String timeStamp, String maneuverIndicator, String spare, String RAIMflag, String radioStatus) {
        this.messageType = messageType;
        this.repeatIndicator = repeatIndicator;
        this.MMSI = MMSI;
        this.navigationStatus = navigationStatus;
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


    public String getMessageType() {
        return messageType;
    }

    public String getRepeatIndicator() {
        return repeatIndicator;
    }

    public String getMMSI() {
        return MMSI;
    }

    public String getNavigationStatus() {
        return navigationStatus;
    }

    public double getSpeedOverGround() {
        return speedOverGround;
    }

    public String getPositiontionAccuracy() {
        return positiontionAccuracy;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getCourseOverGroud() {
        return courseOverGroud;
    }

    public String getTrueHeading() {
        return trueHeading;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getManeuverIndicator() {
        return maneuverIndicator;
    }

    public String getSpare() {
        return spare;
    }

    public String getRAIMflag() {
        return RAIMflag;
    }

    public String getRadioStatus() {
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
