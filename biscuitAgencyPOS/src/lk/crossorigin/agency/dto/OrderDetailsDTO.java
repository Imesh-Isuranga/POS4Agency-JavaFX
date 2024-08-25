package lk.crossorigin.agency.dto;

public class OrderDetailsDTO {

    private String orderId;
    private String itemCode;
    private double unitPrice_Box;
    private double total;
    private double free_total;
    private double dis_tot;
    private double return_tot;
    private int boxQty;
    private int itemQty;
    private int boxQtyFree;
    private int itemQtyFree;

    public OrderDetailsDTO() {
    }

    public OrderDetailsDTO(String orderId, String itemCode, double unitPrice_Box, double total, double free_total, double dis_tot, double return_tot, int boxQty, int itemQty, int boxQtyFree, int itemQtyFree) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.unitPrice_Box = unitPrice_Box;
        this.total = total;
        this.free_total = free_total;
        this.dis_tot = dis_tot;
        this.return_tot = return_tot;
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

    public double getDis_tot() {
        return dis_tot;
    }

    public void setDis_tot(double dis_tot) {
        this.dis_tot = dis_tot;
    }

    public double getReturn_tot() {
        return return_tot;
    }

    public void setReturn_tot(double return_tot) {
        this.return_tot = return_tot;
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
        return "OrderDetailsDTO{" +
                "orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", unitPrice_Box=" + unitPrice_Box +
                ", total=" + total +
                ", free_total=" + free_total +
                ", dis_tot=" + dis_tot +
                ", return_tot=" + return_tot +
                ", boxQty=" + boxQty +
                ", itemQty=" + itemQty +
                ", boxQtyFree=" + boxQtyFree +
                ", itemQtyFree=" + itemQtyFree +
                '}';
    }
}