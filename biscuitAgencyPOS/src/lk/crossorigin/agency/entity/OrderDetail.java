package lk.crossorigin.agency.entity;

public class OrderDetail {
    private String orderId;
    private String itemCode;
    private double unitPrice_Box;
    private int boxQty;
    private int itemQty;
    private int boxQtyFree;
    private int itemQtyFree;


    public OrderDetail() {
    }

    public OrderDetail(String orderId, String itemCode, double unitPrice_Box, int boxQty, int itemQty, int boxQtyFree, int itemQtyFree) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.unitPrice_Box = unitPrice_Box;
        this.boxQty = boxQty;
        this.itemQty = itemQty;
        this.boxQtyFree = boxQtyFree;
        this.itemQtyFree = itemQtyFree;
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

    public double getUnitPrice_Box() {
        return unitPrice_Box;
    }

    public void setUnitPrice_Box(double unitPrice_Box) {
        this.unitPrice_Box = unitPrice_Box;
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

    public int getBoxQtyFree() {
        return boxQtyFree;
    }

    public void setBoxQtyFree(int boxQtyFree) {
        this.boxQtyFree = boxQtyFree;
    }

    public int getItemQtyFree() {
        return itemQtyFree;
    }

    public void setItemQtyFree(int itemQtyFree) {
        this.itemQtyFree = itemQtyFree;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", unitPrice_Box=" + unitPrice_Box +
                ", boxQty=" + boxQty +
                ", itemQty=" + itemQty +
                ", boxQtyFree=" + boxQtyFree +
                ", itemQtyFree=" + itemQtyFree +
                '}';
    }
}
