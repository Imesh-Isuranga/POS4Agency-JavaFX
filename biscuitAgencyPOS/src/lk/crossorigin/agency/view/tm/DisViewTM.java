package lk.crossorigin.agency.view.tm;

import javafx.scene.control.Button;

public class DisViewTM {
    private int id;
    private String orderId;
    private String item;
    private double disPercentage;


    public DisViewTM() {
    }

    public DisViewTM(String orderId, String item, double disPercentage) {
        this.orderId = orderId;
        this.item = item;
        this.disPercentage = disPercentage;
    }

    public DisViewTM(int id, String orderId, String item, double disPercentage) {
        this.id = id;
        this.orderId = orderId;
        this.item = item;
        this.disPercentage = disPercentage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getDisPercentage() {
        return disPercentage;
    }

    public void setDisPercentage(double disPercentage) {
        this.disPercentage = disPercentage;
    }



    @Override
    public String toString() {
        return "DisViewTM{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", item='" + item + '\'' +
                ", disPercentage=" + disPercentage +
                '}';
    }
}
