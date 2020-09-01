package org.alium.trackerlibrary.init;


import org.json.JSONException;
import org.json.JSONObject;

public class Init {

    private String sdkId = "";
    private String clientId = "";

    public Init() {
    }

    public Init(String sdkId, String clientId) {
        sdkId = sdkId;
        clientId = clientId;
    }

    public String getSdkId() {
        return sdkId;
    }

    public void setSdkId(String sdkId) {
        sdkId = sdkId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        clientId = clientId;
    }


    @Override
    public String toString() {
        return "Init{" +
                "sdkId='" + sdkId + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
    }

    public JSONObject toJSON() throws JSONException {

            JSONObject obj = new JSONObject();
            obj.put("sdkId", sdkId);
            obj.put("clientId", clientId);
            return obj;
        }
}


