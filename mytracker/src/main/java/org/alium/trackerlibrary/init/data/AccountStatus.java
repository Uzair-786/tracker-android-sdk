package org.alium.trackerlibrary.init.data;

public class AccountStatus {

    private String verification;
    private Boolean isActive;

    public AccountStatus() {
    }

    public AccountStatus(String verification, Boolean isActive) {
        this.verification = verification;
        this.isActive = isActive;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "AccountStatus{" +
                "verification='" + verification + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
