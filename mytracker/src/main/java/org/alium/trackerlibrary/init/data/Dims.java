package org.alium.trackerlibrary.init.data;

import org.alium.trackerlibrary.init.data.sdk.Sdk;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

public class Dims {

    private String _id;
    private Sdk sdk;
    private String clientId;
    private String platform;
    private ArrayList<HashMap<String,String>> dims;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int __v = 0;

    public Dims() {
    }

    public Dims(String _id, Sdk sdk, String clientId, String platform, ArrayList<HashMap<String,String>> dims, Timestamp createdAt, Timestamp updatedAt, int __v) {
        this._id = _id;
        this.sdk = sdk;
        this.clientId = clientId;
        this.platform = platform;
        this.dims = dims;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.__v = __v;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Sdk getSdk() {
        return sdk;
    }

    public void setSdk(Sdk sdk) {
        this.sdk = sdk;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public ArrayList<HashMap<String,String>> getDims() {
        return dims;
    }

    public void setDims(ArrayList<HashMap<String,String>> dims) {
        this.dims = dims;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    @Override
    public String toString() {
        return "Dims{" +
                "_id='" + _id + '\'' +
                ", sdk=" + sdk +
                ", clientId='" + clientId + '\'' +
                ", platform='" + platform + '\'' +
                ", dims=" + dims +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", __v=" + __v +
                '}';
    }
}
