package fr.ufc.l3info.projet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class MessageAIS {
    private String rawData;
    private String beforePayload;
    private String afterPayload;
    private String rawDataPayloadBin;


    MessageAIS(String rawData) {
        this.rawData = rawData;
        StringBuilder beforePayload = new StringBuilder();
        StringBuilder afterPayload = new StringBuilder();
        String[] arrayRawData = rawData.split(",");
        String payload = "";
        for (int i = 0; i < arrayRawData.length; i++){
            if (i == 5)
                payload = arrayRawData[i];
            else if (i < 5){
                beforePayload.append(arrayRawData[i]).append(',');
            }else{
                afterPayload.append(',').append(arrayRawData[i]);
            }

        }
        this.beforePayload = beforePayload.toString();
        this.afterPayload = afterPayload.toString();

        rawDataPayloadBin = "";
        StringBuilder payloadBinEcoded = new StringBuilder();
        for (int i = 0; i < payload.length(); ++i) {
            int ascii = (int) payload.charAt(i);
            int asciiOffset = ascii - 48;
            int nbOfZero;
            if (asciiOffset == 0)
                nbOfZero = 5;
            else
                nbOfZero = 5 - ((int) (Math.log(asciiOffset) / Math.log(2)));


            if (asciiOffset > 40)
                asciiOffset -= 8;

            for (int j = 0; j < nbOfZero; ++j)
                payloadBinEcoded.append(0);

            payloadBinEcoded.append(Integer.toBinaryString(asciiOffset));
        }

        rawDataPayloadBin = payloadBinEcoded.toString();
    }

    String getRawDataPayloadBin() {
        return rawDataPayloadBin;
    }

    String getRawData() {
        return rawData;
    }

    void printMessage(String fileName) {

        String content = this.rawData;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
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
        return "MessageAIS{" + "\n" +
                "\trawData = '" + rawData + '\'' + ",\n" +
                "\trawDataPayloadBin = '" + rawDataPayloadBin + "\'\n" +
                '}';
    }

    public String getBeforePayload() {
        return beforePayload;
    }

    public String getAfterPayload() {
        return afterPayload;
    }
}
