package com.artesaniasclient.model;

import java.io.Serializable;

public class Company implements Serializable {

    private String id;
    private String address;
    private String businessname;
    private String city;
    private String dateregistry;
    private boolean isactive;
    private String ruc;
    private String suscriptiontype;

    public Company() {
    }

    public Company(String id) {
        this.id = id;
    }

    public Company(String id, String address, String businessname, String city, String dateregistry, boolean isactive, String ruc, String suscriptiontype) {
        this.id = id;
        this.address = address;
        this.businessname = businessname;
        this.city = city;
        this.dateregistry = dateregistry;
        this.isactive = isactive;
        this.ruc = ruc;
        this.suscriptiontype = suscriptiontype;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessname() {
        return businessname;
    }

    public void setBusinessname(String businessname) {
        this.businessname = businessname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDateregistry() {
        return dateregistry;
    }

    public void setDateregistry(String dateregistry) {
        this.dateregistry = dateregistry;
    }

    public boolean isIsactive() {
        return isactive;
    }

    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getSuscriptiontype() {
        return suscriptiontype;
    }

    public void setSuscriptiontype(String suscriptiontype) {
        this.suscriptiontype = suscriptiontype;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", businessname='" + businessname + '\'' +
                ", city='" + city + '\'' +
                ", dateregistry='" + dateregistry + '\'' +
                ", isactive=" + isactive +
                ", ruc='" + ruc + '\'' +
                ", suscriptiontype='" + suscriptiontype + '\'' +
                '}';
    }
}
