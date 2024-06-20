package lk.crossorigin.agency.dto;

public class OrderDetailsDTO {

    private String orderId;
    private String shopId;
    private String itemCode;
    private int qty;
    private double unitPrice;

    public OrderDetailsDTO() {
    }

    public OrderDetailsDTO(String orderId, String shopId, String itemCode, int qty, double unitPrice) {
        this.orderId = orderId;
        this.shopId = shopId;
        this.itemCode = itemCode;
        this.qty = qty;
        this.unitPrice = unitPrice;
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

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
                "orderId='" + orderId + '\'' +
                ", shopId='" + shopId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
