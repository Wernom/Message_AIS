package fr.ufc.l3info.projet;

import java.math.BigInteger;

class Message {
    private MessageAIS ais;
    private MessageDecode decode;

    Message(String message) {
        ais = new MessageAIS(message);
        decode = decode();
    }

    Message(String messageCSV[]) {
        decode = new MessageDecode(messageCSV);
        ais = encode();
    }

    private String sixBitOffset(String binary) {
        int res = Integer.parseInt(binary);
        System.out.println(res);
        if (res <= 31)
            res += 64;

        return Integer.toString(res);
    }

    private String binaryToString(String binary) {
        return new BigInteger(binary, 2).toString();
    }

    private String decimalToBinary(String hex) {
        int i = Integer.parseInt(hex, 10);
        return Integer.toBinaryString(i);
    }

    private MessageDecode decode() {//TODO: si un champ est marqué comme string utiliser sixBitOffset
        String messageType = decodeMessageType();
        String repeatIndicator = decodeRepeatIndicator();
        String MMSI = decodeMMSI();
        String navigationStatus = decodeNavigationStatus();
        double speedOverGround = decodeSpeedOverGround();
        String positiontionAccuracy = decodePositionAccuracy();
        double longitude = decodeLongitude();
        double latitude = decodeLatitude();
        double courseOverGroud = decodeCourseOverGround();
        String trueHeading = decodeTrueHeading();
        String timeStamp = decodeTimeStamp();
        String maneuverIndicator = decodeManeuverIndicator();
        String spare = decodeSpare();
        String RAIMflag = decodeRAIMflag();
        String radioStatus = decodeRadioStatus();

        return new MessageDecode(messageType, repeatIndicator, MMSI, navigationStatus, speedOverGround, positiontionAccuracy, longitude, latitude, courseOverGroud, trueHeading, timeStamp, maneuverIndicator, spare, RAIMflag, radioStatus);
    }

    private MessageAIS encode() {
        String aisRaw = "";
        String aisBin;
        switch (this.decode.getMessageType()) {
            case "1":
            case "2":
            case "3":
                aisBin = encodeMessageType() + encodeRepeatInicator() + encodeMMSI() + encodeNavigateStatus() + "11111000"
                        + encodeSpeedOverGround() + encodePositionAccuracy() + encodeLongitude() + encodeLatitude() + encodeCourseOverGround()
                        + encodeTrueHeading() + encodeTimeStamp() + encodeManeuverIndicator() + encodeSpare() + encodeRAIMflag() + encodeRadioStatus();

                aisRaw = "!AIVDM,1,1,,A,";
                String aisChar;
                StringBuilder aisRawBuilderBin = new StringBuilder(aisRaw);
                for (int i = 0; i < 28; ++i) {
                    aisChar = String.valueOf(aisBin.charAt(6 * i)) +
                            String.valueOf(aisBin.charAt(6 * i + 1)) +
                            String.valueOf(aisBin.charAt(6 * i + 2)) +
                            String.valueOf(aisBin.charAt(6 * i + 3)) +
                            String.valueOf(aisBin.charAt(6 * i + 4)) +
                            String.valueOf(aisBin.charAt(6 * i + 5));
                    int transformToAscii = Integer.parseInt(aisChar, 2);

                    if (transformToAscii > 40) {
                        transformToAscii += 8;
                    }

                    transformToAscii += 48;

                    aisRawBuilderBin.append((char)Integer.parseInt(addZeroToReachNbit(Integer.toBinaryString(transformToAscii), 7), 2));
                }
                aisRawBuilderBin.append(",???");//TODO: remplacer ??? par le bon message.
                aisRaw = aisRawBuilderBin.toString();

                break;
        }
        return new MessageAIS(aisRaw);
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

    private String encodeMessageType() {
        return addZeroToReachNbit(decimalToBinary(decode.getMessageType()), 6);
    }

    private String decodeRepeatIndicator() {
        return binaryToString(ais.getRawDataPayloadBin().substring(7, 9));
    }

    private String encodeRepeatInicator() {
        return addZeroToReachNbit(decimalToBinary(decode.getRepeatIndicator()), 2);
    }

    private String decodeMMSI() {
        return binaryToString(ais.getRawDataPayloadBin().substring(8, 38));
    }

    private String encodeMMSI() {
        return addZeroToReachNbit(decimalToBinary(decode.getMMSI()), 30);
    }

    private String decodeNavigationStatus() {
        return binaryToString(ais.getRawDataPayloadBin().substring(38, 42));
    }

    private String encodeNavigateStatus() {
        return addZeroToReachNbit(decimalToBinary(decode.getNavigationStatus()), 4);
    }

    private double decodeSpeedOverGround() {
        double SOG = Integer.parseInt(ais.getRawDataPayloadBin().substring(50, 60), 2);
        SOG /= 10;
        return SOG;
    }

    private String encodeSpeedOverGround() {
        return addZeroToReachNbit(Integer.toBinaryString((int) Math.round(decode.getSpeedOverGround() * 10)), 10);
    }

    private String decodePositionAccuracy() {
        return binaryToString(ais.getRawDataPayloadBin().substring(60, 61));
    }

    private String encodePositionAccuracy() {
        return addZeroToReachNbit(decimalToBinary(decode.getPositiontionAccuracy()), 1);
    }

    private double decodeLongitude() {//signed
        double longitudeDegre = Integer.parseInt(ais.getRawDataPayloadBin().substring(61, 89), 2);
        longitudeDegre /= 600000;
        if (longitudeDegre > 180) {
            longitudeDegre = (longitudeDegre % 180) - 180;
        }
        return longitudeDegre;
    }

    private String encodeLongitude() {
        double longitudeBin = decode.getLongitude();
        longitudeBin *= 600000;
        if (longitudeBin < 0) {
            longitudeBin *= -1;
            longitudeBin += 180;
        }
        int lon = (int) Math.round(longitudeBin);
        return addZeroToReachNbit(Integer.toBinaryString(lon), 28);
    }


    private double decodeLatitude() {//signed
        double latitudeDegre = Integer.parseInt(ais.getRawDataPayloadBin().substring(89, 116), 2);
        latitudeDegre /= 600000;
        if (latitudeDegre > 180) {
            latitudeDegre = (latitudeDegre % 180 - 180);
        }
        return latitudeDegre;
    }

    private String encodeLatitude() {
        double latitudeBin = decode.getLatitude();
        latitudeBin *= 600000;
        if (latitudeBin < 0) {
            latitudeBin *= -1;
            latitudeBin += 180;
        }
        int lon = (int) Math.round(latitudeBin);
        return addZeroToReachNbit(Integer.toBinaryString(lon), 27);
    }

    private double decodeCourseOverGround() {
        double COG = Integer.parseInt(ais.getRawDataPayloadBin().substring(116, 128), 2);
        COG /= 10;
        return COG;
    }

    private String encodeCourseOverGround() {
        return addZeroToReachNbit(Integer.toBinaryString((int) Math.round(decode.getSpeedOverGround() * 10)), 12);
    }

    private String decodeTrueHeading() {
        return binaryToString(ais.getRawDataPayloadBin().substring(128, 137));
    }

    private String encodeTrueHeading() {
        return addZeroToReachNbit(decimalToBinary(decode.getTrueHeading()), 9);
    }

    private String decodeTimeStamp() {
        return binaryToString(ais.getRawDataPayloadBin().substring(137, 143));
    }

    private String encodeTimeStamp() {
        return addZeroToReachNbit(decimalToBinary(decode.getTimeStamp()), 2);
    }

    private String decodeManeuverIndicator() {
        return binaryToString(ais.getRawDataPayloadBin().substring(143, 145));
    }

    private String encodeManeuverIndicator() {
        return addZeroToReachNbit(decimalToBinary(decode.getManeuverIndicator()), 2);
    }

    private String decodeSpare() {
        return binaryToString(ais.getRawDataPayloadBin().substring(145, 148));
    }

    private String encodeSpare() {
        return addZeroToReachNbit(decimalToBinary(decode.getSpare()), 3);
    }

    private String decodeRAIMflag() {
        return binaryToString(ais.getRawDataPayloadBin().substring(148, 149));
    }

    private String encodeRAIMflag() {
        return addZeroToReachNbit(decimalToBinary(decode.getRAIMflag()), 1);
    }

    private String decodeRadioStatus() {
        return binaryToString(ais.getRawDataPayloadBin().substring(149, 168));
    }

    private String encodeRadioStatus() {
        return addZeroToReachNbit(decimalToBinary(decode.getRadioStatus()), 19);
    }

    private String addZeroToReach6bit(String ascii) {
        int offcet = 6 - ascii.length();
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < offcet; ++i) {
            res.append("0");
        }

        res.append(ascii);

        return res.toString();
    }

    private String addZeroToReachNbit(String ascii, int n) {
        int offcet = n - ascii.length();
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < offcet; ++i) {
            res.append("0");
        }

        res.append(ascii);

        return res.toString();
    }
}
