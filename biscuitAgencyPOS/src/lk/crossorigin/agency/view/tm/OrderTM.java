package lk.crossorigin.agency.view.tm;

public class OrderTM {
    private String itemCode;
    private int boxQty;
    private int itemQty;
    private double total;

    public OrderTM() {
    }

    public OrderTM(String itemCode, int boxQty, int itemQty, double total) {
        this.itemCode = itemCode;
        this.boxQty = boxQty;
        this.itemQty = itemQty;
        this.total = total;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderTM{" +
                ", itemCode='" + itemCode + '\'' +
                ", boxQty=" + boxQty +
                ", itemQty=" + itemQty +
                ", total=" + total +
                '}';
    }
}
