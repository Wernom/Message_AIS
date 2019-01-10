package fr.ufc.l3info.projet;

class MessageAIS {
    private String rawData;
    private String rawDataPayloadBin;


    MessageAIS(String rawData) {
        this.rawData = rawData;
        String payload = rawData.split(",")[5];
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

    @Override
    public String toString() {
        return "MessageAIS{" + "\n" +
                "\trawData = '" + rawData + '\'' + ",\n" +
                "\trawDataPayloadBin = '" + rawDataPayloadBin + "\'\n" +
                '}';
    }
}
