package com.pc.ks.List;

public class TimeLineModel {
    private String message;
    private String date;
    private OrderStatus status;
    public TimeLineModel(String message, String date,OrderStatus status) {
        this.message = message;
        this.date = date;
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public String getDate(){return date;}
    public OrderStatus getStatus() {
        return status;
    }
}
