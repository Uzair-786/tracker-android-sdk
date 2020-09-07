package org.alium.trackerlibrary.init.data;

public class Sdk {

    private Web web;
    private Ios ios;
    private Android android;

    public Sdk() {
    }

    public Sdk(Web web) {
        this.web = web;
    }

    public Sdk(Ios ios) {
        this.ios = ios;
    }

    public Sdk(Android android) {
        this.android = android;
    }

    public Web getWeb() {
        return web;
    }

    public void setWeb(Web web) {
        this.web = web;
    }

    public Ios getIos() {
        return ios;
    }

    public void setIos(Ios ios) {
        this.ios = ios;
    }

    public Android getAndroid() {
        return android;
    }

    public void setAndroid(Android android) {
        this.android = android;
    }

    @Override
    public String toString() {
        return "Sdk{" +
                "web=" + web +
                ", ios=" + ios +
                ", android=" + android +
                '}';
    }
}
