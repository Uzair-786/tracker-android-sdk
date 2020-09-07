package org.alium.trackerlibrary.init.data;

public class CompanyAddress {

     private String address;
     private String state;
     private String city;
     private String country;
     private String pincode;

    public CompanyAddress() {
    }

    public CompanyAddress(String address, String state, String city, String country, String pincode) {
        this.address = address;
        this.state = state;
        this.city = city;
        this.country = country;
        this.pincode = pincode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    @Override
    public String toString() {
        return "CompanyAddress{" +
                "address='" + address + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", pincode='" + pincode + '\'' +
                '}';
    }
}
