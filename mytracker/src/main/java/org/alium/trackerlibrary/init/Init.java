package org.alium.trackerlibrary.init;


import org.json.JSONException;
import org.json.JSONObject;

public class Init {

    private String clientId = "";
    private String sdkId = "";

    public Init() {
    }

    public Init(String clientId, String sdkId) {
        this.clientId = clientId;
        this.sdkId = sdkId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSdkId() {
        return sdkId;
    }

    public void setSdkId(String sdkId) {
        this.sdkId = sdkId;
    }

    @Override
    public String toString() {
        return "Init{" +
                "clientId='" + clientId + '\'' +
                ", sdkId='" + sdkId + '\'' +
                '}';
    }

    public JSONObject toJSON() throws JSONException {

            JSONObject obj = new JSONObject();
            obj.put("clientId", clientId);
            obj.put("sdkId", sdkId);
            return obj;
        }
}


