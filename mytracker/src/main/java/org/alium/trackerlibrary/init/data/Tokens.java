package org.alium.trackerlibrary.init.data;

public class Tokens {

    private Sdk sdk;
    private String clientID;

    public Tokens(Sdk sdk, String clientID) {
        this.sdk = sdk;
        this.clientID = clientID;
    }

    public Tokens() {
    }

    public Sdk getSdk() {
        return sdk;
    }

    public void setSdk(Sdk sdk) {
        this.sdk = sdk;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    @Override
    public String toString() {
        return "Tokens{" +
                "sdk=" + sdk +
                ", clientID='" + clientID + '\'' +
                '}';
    }
}
