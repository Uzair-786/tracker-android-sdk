package org.alium.trackerlibrary.init.data;

public class DimsArray {

    private String dimName;
    private String dimAction = "onclick";

    public DimsArray() {
    }

    public DimsArray(String dimName, String dimAction) {
        this.dimName = dimName;
        this.dimAction = dimAction;
    }

    public String getDimName() {
        return dimName;
    }

    public void setDimName(String dimName) {
        this.dimName = dimName;
    }

    public String getDimAction() {
        return dimAction;
    }

    public void setDimAction(String dimAction) {
        this.dimAction = dimAction;
    }

    @Override
    public String toString() {
        return "DimsArray{" +
                "dimName='" + dimName + '\'' +
                ", dimAction='" + dimAction + '\'' +
                '}';
    }
}
