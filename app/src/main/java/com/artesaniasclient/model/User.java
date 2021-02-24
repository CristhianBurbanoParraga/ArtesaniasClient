package com.artesaniasclient.model;

import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;

public class User implements Serializable {

    private String id;
    private String address;
    private String dateregistry;
    private String email;
    private boolean isactive;
    private boolean issuperuser;
    private String lastname;
    private String firstname;
    private String username;
    private String password;
    private String phone;
    private String usertype;

    public User() {
    }

    public User(String id) {
        this.id = id;
    }

    public User(String id, String address, String dateregistry, String email, boolean isactive, boolean issuperuser, String lastname, String firstname, String username, String password, String phone, String usertype) {
        this.id = id;
        this.address = address;
        this.dateregistry = dateregistry;
        this.email = email;
        this.isactive = isactive;
        this.issuperuser = issuperuser;
        this.lastname = lastname;
        this.firstname = firstname;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.usertype = usertype;
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

    public String getDateregistry() {
        return dateregistry;
    }

    public void setDateregistry(String dateregistry) {
        this.dateregistry = dateregistry;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isIsactive() {
        return isactive;
    }

    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
    }

    public boolean isIssuperuser() {
        return issuperuser;
    }

    public void setIssuperuser(boolean issuperuser) {
        this.issuperuser = issuperuser;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", dateregistry='" + dateregistry + '\'' +
                ", email='" + email + '\'' +
                ", isactive=" + isactive +
                ", issuperuser=" + issuperuser +
                ", lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", usertype='" + usertype + '\'' +
                '}';
    }
}
