package lk.crossorigin.agency.entity;

public class ReturnStock {
    private int id;
    private String orderId;
    private String itemCode;
    private int boxQty;
    private int itemQty;

    public ReturnStock() {
    }

    public ReturnStock(int id, String orderId, String itemCode, int boxQty, int itemQty) {
        this.id = id;
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.boxQty = boxQty;
        this.itemQty = itemQty;
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

    @Override
    public String toString() {
        return "ReturnStock{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", boxQty=" + boxQty +
                ", itemQty=" + itemQty +
                '}';
    }
}
