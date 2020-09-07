package org.alium.trackerlibrary.init.data;

public class Ios {

    private String subscription;
    private Boolean isActive;
    private String sdkID;

    public Ios() {
    }

    public Ios(String subscription, Boolean isActive, String sdkID) {
        this.subscription = subscription;
        this.isActive = isActive;
        this.sdkID = sdkID;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getSdkID() {
        return sdkID;
    }

    public void setSdkID(String sdkID) {
        this.sdkID = sdkID;
    }

    @Override
    public String toString() {
        return "Ios{" +
                "subscription='" + subscription + '\'' +
                ", isActive=" + isActive +
                ", sdkID='" + sdkID + '\'' +
                '}';
    }
}
