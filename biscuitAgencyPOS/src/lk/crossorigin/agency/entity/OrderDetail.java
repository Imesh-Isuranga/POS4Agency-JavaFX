package lk.crossorigin.agency.entity;

public class OrderDetail {
    private String orderId;
    private String itemCode;
    private double unitPrice_Box;
    private double total;
    private double free_total;
    private double dis_total;
    private double return_total;
    private int boxQty;
    private int itemQty;
    private int boxQtyFree;
    private int itemQtyFree;


    public OrderDetail() {
    }

    public OrderDetail(String orderId, String itemCode, double unitPrice_Box, double total, double free_total, double dis_total, double return_total, int boxQty, int itemQty, int boxQtyFree, int itemQtyFree) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.unitPrice_Box = unitPrice_Box;
        this.total = total;
        this.free_total = free_total;
        this.dis_total = dis_total;
        this.return_total = return_total;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getFree_total() {
        return free_total;
    }

    public void setFree_total(double free_total) {
        this.free_total = free_total;
    }

    public double getDis_total() {
        return dis_total;
    }

    public void setDis_total(double dis_total) {
        this.dis_total = dis_total;
    }

    public double getReturn_total() {
        return return_total;
    }

    public void setReturn_total(double return_total) {
        this.return_total = return_total;
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
                ", total=" + total +
                ", free_total=" + free_total +
                ", dis_total=" + dis_total +
                ", return_total=" + return_total +
                ", boxQty=" + boxQty +
                ", itemQty=" + itemQty +
                ", boxQtyFree=" + boxQtyFree +
                ", itemQtyFree=" + itemQtyFree +
                '}';
    }
}