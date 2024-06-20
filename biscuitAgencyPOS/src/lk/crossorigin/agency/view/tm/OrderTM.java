package lk.crossorigin.agency.view.tm;

public class OrderTM {
    private String name;
    private String code;
    private int qty;
    private double unitPrice;
    private double total;

    public OrderTM() {
    }

    public OrderTM(String name, String code, int qty, double unitPrice, double total) {
        this.name = name;
        this.code = code;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderTM{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", total=" + total +
                '}';
    }
}
