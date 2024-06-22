package lk.crossorigin.agency.view.tm;

public class ItemTM {
    private String code;
    private String name;
    private double unitPrice_Box_Agency;
    private double unitPrice_Box;
    private int boxQty;
    private int itemQty;
    private double total;

    public ItemTM() {
    }

    public ItemTM(String code, String name, double unitPrice_Box_Agency, double unitPrice_Box, int boxQty, int itemQty, double total) {
        this.code = code;
        this.name = name;
        this.unitPrice_Box_Agency = unitPrice_Box_Agency;
        this.unitPrice_Box = unitPrice_Box;
        this.boxQty = boxQty;
        this.itemQty = itemQty;
        this.total = total;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice_Box_Agency() {
        return unitPrice_Box_Agency;
    }

    public void setUnitPrice_Box_Agency(double unitPrice_Box_Agency) {
        this.unitPrice_Box_Agency = unitPrice_Box_Agency;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ItemTM{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", unitPrice_Box_Agency=" + unitPrice_Box_Agency +
                ", unitPrice_Box=" + unitPrice_Box +
                ", boxQty=" + boxQty +
                ", itemQty=" + itemQty +
                ", total=" + total +
                '}';
    }
}
