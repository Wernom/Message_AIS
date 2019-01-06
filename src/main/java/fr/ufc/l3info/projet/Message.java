package fr.ufc.l3info.projet;

import java.math.BigInteger;

class Message {
    private MessageAIS ais;
    private MessageDecode decode;

    Message(String message) {
        ais = new MessageAIS(message);
        decode = decode();
    }

    private String sixBitOffset(String binary) {
        int res = Integer.parseInt(binary);
        System.out.println(res);
        if (res <= 31)
            res += 64;

        return Integer.toString(res);
    }

    private String binaryToString(String binary) {
//        return new String(new BigInteger(binary, 2).toByteArray());
        return new BigInteger(binary, 2).toString();
    }

    private MessageDecode decode() {//TODO: si un champ est marqué comme string utiliser sixBitOffset
        String messageType = decodeMessageType();
        String repeatIndicator = decodeRepeatIndicator();
        String MMSI = decodeMMSI();
        String navigationStatus = decodeNavigationStatus();
        String speedOverGround = decodeSpeedOverGround();
        String positiontionAccuracy = decodePositionAccuracy();
        double longitude = decodeLongitude();
        double latitude = decodeLatitude();
        String courseOverGroud = decodeCourseOverGround();
        String trueHeading = decodeTrueHeading();
        String timeStamp = decodeTimeStamp();
        String maneuverIndicator = decodeManeuverIndicator();
        String spare = decodeSpare();
        String RAIMflag = decodeRAIMflag();
        String radioStatus = decodeRadioStatus();

        return new MessageDecode(messageType, repeatIndicator, MMSI, navigationStatus, speedOverGround, positiontionAccuracy, longitude, latitude, courseOverGroud, trueHeading, timeStamp, maneuverIndicator, spare, RAIMflag, radioStatus);
    }

    MessageAIS getAis() {
        return ais;
    }

    MessageDecode getDecode() {
        return decode;
    }

    private String decodeMessageType() {
        return binaryToString(ais.getRawDataPayloadBin().substring(0, 6));
    }

    private String decodeRepeatIndicator() {
        return binaryToString(ais.getRawDataPayloadBin().substring(7, 9));
    }

    private String decodeMMSI() {
        return binaryToString(ais.getRawDataPayloadBin().substring(8, 38));
    }

    private String decodeNavigationStatus() {
        return binaryToString(ais.getRawDataPayloadBin().substring(38, 42));
    }

    private String decodeSpeedOverGround() {
        return binaryToString(ais.getRawDataPayloadBin().substring(50, 60));
    }

    private String decodePositionAccuracy() {
        return binaryToString(ais.getRawDataPayloadBin().substring(60, 61));
    }

    private double decodeLongitude() {//signed

        double longitudeDegre = Integer.parseInt(ais.getRawDataPayloadBin().substring(61, 89), 2);
        longitudeDegre /= 600000;
        if (longitudeDegre > 180) {
            longitudeDegre = (longitudeDegre % 180 -180);
        }
        return longitudeDegre;
    }

    private double decodeLatitude() {//signed
        double latitudeDegre = Integer.parseInt(ais.getRawDataPayloadBin().substring(89, 116), 2);
        latitudeDegre /= 600000;
        if (latitudeDegre > 180) {
            latitudeDegre = (latitudeDegre % 180 - 180);
        }
        return latitudeDegre;
    }

    private String decodeCourseOverGround() {
        return binaryToString(ais.getRawDataPayloadBin().substring(116, 128));
    }

    private String decodeTrueHeading() {
        return binaryToString(ais.getRawDataPayloadBin().substring(128, 137));
    }

    private String decodeTimeStamp() {
        return binaryToString(ais.getRawDataPayloadBin().substring(137, 143));
    }

    private String decodeManeuverIndicator() {
        return binaryToString(ais.getRawDataPayloadBin().substring(143, 145));
    }

    private String decodeSpare() {
        return binaryToString(ais.getRawDataPayloadBin().substring(145, 148));
    }

    private String decodeRAIMflag() {
        return binaryToString(ais.getRawDataPayloadBin().substring(148, 149));
    }

    private String decodeRadioStatus() {
        return binaryToString(ais.getRawDataPayloadBin().substring(149, 168));
    }


}
