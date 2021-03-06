package com.artesaniasclient.model;

import java.util.ArrayList;

public class Order {
    private String id;
    private String deliverydate;
    private String deliverytime;
    private String observations;
    private String orderdate;
    private int ordernumber;
    private String ordertime;
    private String state;
    private String user;
    ArrayList<String> detail;

    public Order(String id, String deliverydate, String deliverytime, String observations, String orderdate, int ordernumber, String ordertime, String state, String user, ArrayList<String> detail) {
        this.id = id;
        this.deliverydate = deliverydate;
        this.deliverytime = deliverytime;
        this.observations = observations;
        this.orderdate = orderdate;
        this.ordernumber = ordernumber;
        this.ordertime = ordertime;
        this.state = state;
        this.user = user;
        this.detail = detail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeliverydate() {
        return deliverydate;
    }

    public void setDeliverydate(String deliverydate) {
        this.deliverydate = deliverydate;
    }

    public String getDeliverytime() {
        return deliverytime;
    }

    public void setDeliverytime(String deliverytime) {
        this.deliverytime = deliverytime;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public int getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(int ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ArrayList<String> getDetail() {
        return detail;
    }

    public void setDetail(ArrayList<String> detail) {
        this.detail = detail;
    }
}
