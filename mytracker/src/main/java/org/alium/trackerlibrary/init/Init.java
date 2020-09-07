package org.alium.trackerlibrary.init;

import org.alium.trackerlibrary.init.data.Dims;
import org.json.JSONException;
import org.json.JSONObject;

public class Init {

    private String clientId = "";
    private String sdkId = "";
    private Dims dims ;

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

    public Dims getDims() {
        return dims;
    }

    public void setDims(Dims dims) {
        this.dims = dims;
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


