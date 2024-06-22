package lk.crossorigin.agency.view.tm;

import java.util.Date;

public class OrderDetailsTM {
    private String orderId;
    private String itemCode;
    private int boxQty;
    private int itemQty;
    private Date date;
    private double total;


    public OrderDetailsTM() {
    }

    public OrderDetailsTM(String orderId, String itemCode, int boxQty, int itemQty, Date date, double total) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.boxQty = boxQty;
        this.itemQty = itemQty;
        this.date = date;
        this.total = total;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getBoxQty() {
        return boxQty;
    }

    public void setBoxQty(int boxQty) {
        this.boxQty = boxQty;
    }

    public int getItemQty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderDetailsTM{" +
                "orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", boxQty=" + boxQty +
                ", itemQty=" + itemQty +
                ", date=" + date +
                ", total=" + total +
                '}';
    }
}
