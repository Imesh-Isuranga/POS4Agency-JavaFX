package lk.crossorigin.agency.dto;

public class ReturnStockDTO {
    private int id;
    private String orderId;
    private String itemCode;
    private int boxQty;
    private int itemQty;
    private double perQty;


    public ReturnStockDTO() {
    }

    public ReturnStockDTO(String orderId, String itemCode, int boxQty, int itemQty, double perQty) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.boxQty = boxQty;
        this.itemQty = itemQty;
        this.perQty = perQty;
    }
    public ReturnStockDTO(int id, String orderId, String itemCode, int boxQty, int itemQty, double perQty) {
        this.id = id;
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.boxQty = boxQty;
        this.itemQty = itemQty;
        this.perQty = perQty;
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

    public double getPerQty() {
        return perQty;
    }

    public void setPerQty(double perQty) {
        this.perQty = perQty;
    }

    @Override
    public String toString() {
        return "ReturnStockDTO{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", boxQty=" + boxQty +
                ", itemQty=" + itemQty +
                ", perQty=" + perQty +
                '}';
    }
}
