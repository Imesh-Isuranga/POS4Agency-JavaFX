package lk.crossorigin.agency.dto;

public class DiscountDTO {
    private int id;
    private int idDup;
    private String orderId;
    private String itemCode;
    private double discountValue;

    public DiscountDTO() {
    }

    public DiscountDTO(int idDup, String orderId, String itemCode, double discountValue) {
        this.idDup = idDup;
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.discountValue = discountValue;
    }

    public DiscountDTO(int id, int idDup, String orderId, String itemCode, double discountValue) {
        this.id = id;
        this.idDup = idDup;
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.discountValue = discountValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDup() {
        return idDup;
    }

    public void setIdDup(int idDup) {
        this.idDup = idDup;
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

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    @Override
    public String toString() {
        return "DiscountDTO{" +
                "id=" + id +
                ", idDup=" + idDup +
                ", orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", discountValue=" + discountValue +
                '}';
    }
}
