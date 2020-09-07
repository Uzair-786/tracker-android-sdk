package org.alium.trackerlibrary.init.data;

import java.sql.Timestamp;
import java.util.Arrays;

public class ClientDetails {

    private SpocDetails spocDetails;
    private CompanyAddress companyAddress;
    private AccountStatus accountStatus;
    private Tokens tokens;
    private String companyName;
    private String [] SDK = {"Web","Ios","Android"};
    private String _id;
    private String companyEmail;
    private Timestamp createdOn;
    private Timestamp lastModified;
    private int _v = 0;

    public ClientDetails() {
    }

    public ClientDetails(SpocDetails spocDetails, CompanyAddress companyAddress, AccountStatus accountStatus, Tokens tokens, String companyName, String[] SDK, String _id, String companyEmail, Timestamp createdOn, Timestamp lastModified, int _v) {
        this.spocDetails = spocDetails;
        this.companyAddress = companyAddress;
        this.accountStatus = accountStatus;
        this.tokens = tokens;
        this.companyName = companyName;
        this.SDK = SDK;
        this._id = _id;
        this.companyEmail = companyEmail;
        this.createdOn = createdOn;
        this.lastModified = lastModified;
        this._v = _v;
    }

    public SpocDetails getSpocDetails() {
        return spocDetails;
    }

    public void setSpocDetails(SpocDetails spocDetails) {
        this.spocDetails = spocDetails;
    }

    public CompanyAddress getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(CompanyAddress companyAddress) {
        this.companyAddress = companyAddress;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Tokens getTokens() {
        return tokens;
    }

    public void setTokens(Tokens tokens) {
        this.tokens = tokens;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String[] getSDK() {
        return SDK;
    }

    public void setSDK(String[] SDK) {
        this.SDK = SDK;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    public int get_v() {
        return _v;
    }

    public void set_v(int _v) {
        this._v = _v;
    }

    @Override
    public String toString() {
        return "ClientDetails{" +
                "spocDetails=" + spocDetails +
                ", companyAddress=" + companyAddress +
                ", accountStatus=" + accountStatus +
                ", tokens=" + tokens +
                ", companyName='" + companyName + '\'' +
                ", SDK=" + Arrays.toString(SDK) +
                ", _id='" + _id + '\'' +
                ", companyEmail='" + companyEmail + '\'' +
                ", createdOn=" + createdOn +
                ", lastModified=" + lastModified +
                ", _v=" + _v +
                '}';
    }
}
