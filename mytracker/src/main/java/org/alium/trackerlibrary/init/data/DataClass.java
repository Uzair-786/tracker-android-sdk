package org.alium.trackerlibrary.init.data;

public class DataClass {

   private ClientDetails  clientDetails;
   private Dims dims;

    public DataClass() {
    }

    public DataClass(ClientDetails clientDetails, Dims dims) {
        this.clientDetails = clientDetails;
        this.dims = dims;
    }

    public ClientDetails getClientDetails() {
        return clientDetails;
    }

    public void setClientDetails(ClientDetails clientDetails) {
        this.clientDetails = clientDetails;
    }

    public Dims getDims() {
        return dims;
    }

    public void setDims(Dims dims) {
        this.dims = dims;
    }

    @Override
    public String toString() {
        return "DataClass{" +
                "clientDetails=" + clientDetails +
                ", dims=" + dims +
                '}';
    }
}
