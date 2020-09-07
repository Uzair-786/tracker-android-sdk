package org.alium.trackerlibrary.init.data.sdk;

public class Sdk {

    private String name;
    private String sdkId;

    public Sdk() {
    }

    public Sdk(String name, String sdkId) {
        this.name = name;
        this.sdkId = sdkId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSdkId() {
        return sdkId;
    }

    public void setSdkId(String sdkId) {
        this.sdkId = sdkId;
    }

    @Override
    public String toString() {
        return "Sdk{" +
                "name='" + name + '\'' +
                ", sdkId='" + sdkId + '\'' +
                '}';
    }
}
