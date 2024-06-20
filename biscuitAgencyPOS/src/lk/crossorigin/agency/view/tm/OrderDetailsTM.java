package lk.crossorigin.agency.view.tm;

import java.util.Date;

public class OrderDetailsTM {
    private String orderId;
    private String shopId;
    private String shopName;
    private String itemName;
    private int qty;
    private Date date;
    private double total;


    public OrderDetailsTM() {
    }

    public OrderDetailsTM(String orderId, String shopId, String shopName, String itemName, int qty, Date date, double total) {
        this.orderId = orderId;
        this.shopId = shopId;
        this.shopName = shopName;
        this.itemName = itemName;
        this.qty = qty;
        this.date = date;
        this.total = total;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
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
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", itemName='" + itemName + '\'' +
                ", qty=" + qty +
                ", date=" + date +
                ", total=" + total +
                '}';
    }
}
